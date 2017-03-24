package org.usfirst.frc.team1732.robot.commands.vision;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;
import static org.usfirst.frc.team1732.robot.Robot.visionMain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveWithVisionStraight extends Command {

	// stuff to add
	// also only update encoders when vision update is ready

	public DriveWithVisionStraight(double aTargetDistanceInches, double maxSetpoint) {
		requires(driveTrain);
		targetDistanceInches = aTargetDistanceInches;
		MAX_SETPOINT = maxSetpoint;
		leftMeasurements = MAX_SETPOINT;
		rightMeasurements = MAX_SETPOINT;
		setTimeout(4);
	}

	public static void setSmartDashboardDistance(double distance) {
		smartDashboardDistance = distance;
	}

	// public static DriveWithVisionOld newCommandUseSmartDashboardDistance() {
	// return new DriveWithVisionOld(smartDashboardDistance);
	// }

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		driveTrain.resetGyro();
		driveTrain.resetEncoders();

		driveTrain.setEncoderSetpoint(MAX_SETPOINT);
		driveTrain.setGyroSetpoint(0);
		visionMain.setGearSetpoint(0);
		driveTrain.setEncoderPIDS(0.02, 0, 0);
	}

	public static final double	DEFAULT_TARGET_INCHES	= 10;
	private static double		smartDashboardDistance	= DEFAULT_TARGET_INCHES;

	private final double	MAX_SETPOINT;
	private double			targetDistanceInches;
	private double			leftMeasurements	= 0;
	private double			rightMeasurements	= 0;
	private int				measurements		= 1;

	private static double	middle	= 70;
	private static double	lower	= 0.001;	// 0.001
	private static double	upper	= 0.025;
	private static double	slope	= 1.2E-4;	// Wed. 2-15-17 changed to 1.2//
	// 0.03 / 75;// 0.0001;

	// Add a safeguard to make sure we don't get stuck
	// public static double slope = 0.03/75;
	@Override
	protected void execute() {
		visionMain.run();
		if (visionMain.canSeeGearPeg() && visionMain.isNewGearDataAvailable()) {
			double distance = visionMain.getInchesToGearPeg();

			double dDistance = distance - targetDistanceInches;
			double leftSetpoint = dDistance + driveTrain.getLeftDistance();
			double rightSetpoint = dDistance + driveTrain.getRightDistance();
			if (leftSetpoint < MAX_SETPOINT && rightSetpoint < MAX_SETPOINT) {
				leftMeasurements += leftSetpoint;
				rightMeasurements += rightSetpoint;
				measurements++;
			}

			if (leftMeasurements != 0 && rightMeasurements != 0 && measurements != 0) {
				leftSetpoint = leftMeasurements / measurements;
				rightSetpoint = rightMeasurements / measurements;
			}

			if (leftSetpoint > MAX_SETPOINT)
				leftSetpoint = MAX_SETPOINT - driveTrain.getLeftDistance();
			if (rightSetpoint > MAX_SETPOINT)
				rightSetpoint = MAX_SETPOINT - driveTrain.getRightDistance();
			driveTrain.setLeftEncoderSetpoint(leftSetpoint);
			driveTrain.setRightEncoderSetpoint(rightSetpoint);
		}

		double leftOutput = driveTrain.getLeftPIDOutput();
		double rightOutput = driveTrain.getRightPIDOutput();
		leftOutput = leftOutput + driveTrain.getLeftRightAdjustment();
		rightOutput = rightOutput - driveTrain.getLeftRightAdjustment();
		double max = Math.abs(Math.max(leftOutput, rightOutput));
		if (max >= 1) {
			leftOutput = leftOutput / max;
			rightOutput = rightOutput / max;
		}
		driveTrain.driveRaw(leftOutput, rightOutput);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return (driveTrain.isErrorNegative() || driveTrain.encodersOnTarget()) || isTimedOut();
	}

	public static void setSlope(double slope) {
		DriveWithVisionStraight.slope = slope;
	}

	public static void setMiddle(double middle) {
		DriveWithVisionStraight.middle = middle;
	}

	public static void setLower(double lower) {
		DriveWithVisionStraight.lower = lower;
	}

	public static void setUpper(double upper) {
		DriveWithVisionStraight.upper = upper;
	}

	@Override
	protected void end() {
		driveTrain.driveRaw(0, 0);
		visionMain.resetGearPIDValues();
		driveTrain.resetEncoderPIDValues();
	}

	public static double getMiddle() {
		return middle;
	}

	public static double getLower() {
		return lower;
	}

	public static double getUpper() {
		return upper;
	}

	public static double getSlope() {
		return slope;
	}

}
