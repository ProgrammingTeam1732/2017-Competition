package org.usfirst.frc.team1732.robot.commands.vision;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;
import static org.usfirst.frc.team1732.robot.Robot.visionMain;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithVision extends Command {

	public double targetDistanceInches;

	public static final double	DEFAULT_TARGET_INCHES	= 10;
	private static double		smartDashboardDistance	= DEFAULT_TARGET_INCHES;

	private boolean foundOnce = false;

	public DriveWithVision(double aTargetDistanceInches) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(driveTrain);
		targetDistanceInches = aTargetDistanceInches;
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
		driveTrain.gyro.reset();
		driveTrain.leftEncoder.reset();
		driveTrain.rightEncoder.reset();

		driveTrain.leftEncoderPID.setSetpoint(0);
		driveTrain.rightEncoderPID.setSetpoint(0);
		driveTrain.gyroPID.setSetpoint(0);
		visionMain.visionPID.setSetpoint(0);
	}

	private double previousAngleOutput = 0;
	public static double slope = 0.03/75;
	@Override
	protected void execute() {

//		double angle = visionMain.getAngleToGearPeg();
		double distance = visionMain.getInchesToGearPeg();
		
		double P = 0.01 + slope*distance;
		driveTrain.leftEncoderPID.setPID(P, 0, 0);
		driveTrain.rightEncoderPID.setPID(P, 0, 0);

		double dDistance = distance - targetDistanceInches;
		double leftSetpoint = dDistance + driveTrain.leftEncoder.getDistance();
		double rightSetpoint = dDistance + driveTrain.rightEncoder.getDistance();

//		double angleSetpoint = angle + driveTrain.gyro.getAngle();
		// if it still sees it calculate the new output, otherwise keep doing
		// what it was doing
		if (visionMain.canSeeGearPeg()) {
			foundOnce = true;
			previousAngleOutput = Robot.visionMain.visionPID.get();
//			driveTrain.gyroPID.setSetpoint(angleSetpoint);
			driveTrain.leftEncoderPID.setSetpoint(leftSetpoint);
			driveTrain.rightEncoderPID.setSetpoint(rightSetpoint);
		}
//		double angleOutput = driveTrain.gyroPID.get();
		double leftOutput = driveTrain.leftEncoderPID.get() - previousAngleOutput;
		double rightOutput = driveTrain.rightEncoderPID.get() + previousAngleOutput;
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
		return foundOnce && driveTrain.rightEncoderPID.onTarget() && driveTrain.leftEncoderPID.onTarget();
//				&& driveTrain.gyroPID.onTarget() && 
	}
	
	public static void setSlope(double s) {
		slope = s;
	}

	@Override
	protected void end() {
		driveTrain.driveRaw(0, 0);
	}

}
