package org.usfirst.frc.team1732.robot.commands.vision;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;
import static org.usfirst.frc.team1732.robot.Robot.visionMain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithVision extends Command {

	public DriveWithVision(double aTargetDistanceInches) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(driveTrain);
		targetDistanceInches = aTargetDistanceInches;
		// visionMain.visionPID.setPID(0.01, 0, 0);
	}

	public static void setSmartDashboardDistance(double distance) {
		smartDashboardDistance = distance;
	}

	public static DriveWithVision newCommandUseSmartDashboardDistance() {
		return new DriveWithVision(smartDashboardDistance);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		driveTrain.resetGyro();
		driveTrain.resetEncoders();

		driveTrain.setEncoderSetpoint(0);
		driveTrain.setGyroSetpoint(0);
		visionMain.setVisionSetpoint(0);
	}

	public double targetDistanceInches;

	public static final double	DEFAULT_TARGET_INCHES	= 10;
	private static double		smartDashboardDistance	= DEFAULT_TARGET_INCHES;

	private boolean	foundOnce	= false;
//	private boolean	lostOnce	= false;

	private double previousAngleOutput = 0;

	public static final double stopInputDistance = 20;

	public static double	middle	= 70;
	public static double	lower	= 0.001;	// 0.001
	public static double	upper	= 0.025;
	public static double	slope	= 1.2E-4;	// Wed. 2-15-17 changed to 1.2//
												// 0.03 / 75;// 0.0001;

	// Add a safeguard to make sure we don't get stuck
	// public static double slope = 0.03/75;
	@Override
	protected void execute() {
		// double angle = visionMain.getAngleToGearPeg();
		double distance = visionMain.getInchesToGearPeg();

		double dDistance = distance - targetDistanceInches;
		double leftSetpoint = dDistance + driveTrain.getLeftDistance();
		double rightSetpoint = dDistance + driveTrain.getRightDistance();

		// double angleSetpoint = angle + driveTrain.gyro.getAngle();
		// if it still sees it calculate the new output, otherwise keep doing
		// what it was doing
		if (visionMain.canSeeGearPeg()) {
			// double P = lower + slope * distance;
			// double P = lower + (upper - lower) / (1 + Math.exp(-slope *
			// (distance - middle)));
			// double distance = visionMain.getInchesToGearPeg();

			double P = lower + slope * distance;
			// driveTrain.leftEncoderPID.setPID(P, 0, 0);
			// driveTrain.rightEncoderPID.setPID(P, 0, 0);
			visionMain.setPIDValues(P, 0, 0);
			foundOnce = true;
			previousAngleOutput = visionMain.getVisionPIDOutput();
			// driveTrain.gyroPID.setSetpoint(angleSetpoint);
			driveTrain.setLeftEncoderSetpoint(leftSetpoint);
			driveTrain.setRightEncoderSetpoint(rightSetpoint);
		}
//		else if (foundOnce) {
//			lostOnce = true;
//		}
		// double angleOutput = driveTrain.gyroPID.get();
		double leftOutput = driveTrain.getLeftPIDOutput() - previousAngleOutput;
		double rightOutput = driveTrain.getRightPIDOutput() + previousAngleOutput;
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
		return foundOnce && (driveTrain.isErrorNegative() || driveTrain.encodersOnTarget());
		// && driveTrain.gyroPID.onTarget() &&
	}

	public static void setSlope(double slope) {
		DriveWithVision.slope = slope;
	}

	public static void setMiddle(double middle) {
		DriveWithVision.middle = middle;
	}

	public static void setLower(double lower) {
		DriveWithVision.lower = lower;
	}

	public static void setUpper(double upper) {
		DriveWithVision.upper = upper;
	}

	@Override
	protected void end() {
		driveTrain.driveRaw(0, 0);
		driveTrain.resetEncoderPIDValues();
		driveTrain.resetGyroPIDValues();
		visionMain.resetPIDValues();
	}

}
