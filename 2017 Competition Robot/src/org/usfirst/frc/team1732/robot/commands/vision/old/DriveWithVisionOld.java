package org.usfirst.frc.team1732.robot.commands.vision.old;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;
import static org.usfirst.frc.team1732.robot.Robot.visionMain;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveWithVisionOld extends Command {

	public DriveWithVisionOld(double aTargetDistanceInches) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(driveTrain);
		requires(Robot.pixyCamera);
		targetDistanceInches = aTargetDistanceInches;
		// visionMain.visionPID.setPID(0.01, 0, 0);
		setTimeout(5);
	}

	public static void setSmartDashboardDistance(double distance) {
		smartDashboardDistance = distance;
	}

	public static DriveWithVisionOld newCommandUseSmartDashboardDistance() {
		return new DriveWithVisionOld(smartDashboardDistance);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.pixyCamera.turnOnLights();
		driveTrain.resetGyro();
		driveTrain.resetEncoders();

		driveTrain.setEncoderSetpoint(0);
		driveTrain.setGyroSetpoint(0);
		visionMain.setGearSetpoint(0);

		visionMain.run();
		distance = visionMain.getInchesToGearPeg();
		if (distance > 110)
			distance = 110;
		driveTrain.setEncoderSetpoint(distance);
	}

	public double	targetDistanceInches;
	public double	distance;

	public static final double	DEFAULT_TARGET_INCHES	= 10;
	private static double		smartDashboardDistance	= DEFAULT_TARGET_INCHES;

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
		double leftOutput = Robot.driveTrain.getLeftPIDOutput();
		double rightOutput = Robot.driveTrain.getRightPIDOutput();
		driveTrain.driveRaw(leftOutput, rightOutput);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return (driveTrain.isErrorNegative() || driveTrain.encodersOnTarget()) || isTimedOut()
				|| (driveTrain.getRightDistance() < 100 && driveTrain.getLeftDistance() < 100);
	}

	public static void setSlope(double slope) {
		DriveWithVisionOld.slope = slope;
	}

	public static void setMiddle(double middle) {
		DriveWithVisionOld.middle = middle;
	}

	public static void setLower(double lower) {
		DriveWithVisionOld.lower = lower;
	}

	public static void setUpper(double upper) {
		DriveWithVisionOld.upper = upper;
	}

	@Override
	protected void end() {
		Robot.pixyCamera.turnOffLights();
		driveTrain.driveRaw(0, 0);
		// driveTrain.resetEncoderPIDValues();
		// driveTrain.resetGyroPIDValues();
		visionMain.resetGearPIDValues();
	}

}
