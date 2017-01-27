package org.usfirst.frc.team1732.robot.commands;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;
import static org.usfirst.frc.team1732.robot.Robot.visionMain;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithVision extends Command {

	public double targetDistanceInches;

	public static final double DEFAULT_TARGET_INCHES = 100;

	public DriveWithVision(double aTargetDistanceInches) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(driveTrain);
		targetDistanceInches = aTargetDistanceInches;
	}

	public DriveWithVision() {
		this(DEFAULT_TARGET_INCHES);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		driveTrain.setDriveWithEncoders();
		driveTrain.zeroEncoders();
		driveTrain.setEncoderSetpointInches(0, 0);
	}

	private double lastGoodDistance;

	public static final double MAXIMUM_DISTANCE_CHANGE = 15;

	private boolean goodDistanceInitialized = false;

	// private int framesSinceGoodFrame = 0;
	// public static final int RESET_FRAME_NUMBER = 4;

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// framesSinceGoodFrame++;
		Robot.visionMain.run();
		double distance = visionMain.getInchesToGearPeg();
		System.out.println("Running auto vision: " + distance);
		if ((!goodDistanceInitialized || Math.abs(distance - lastGoodDistance) < MAXIMUM_DISTANCE_CHANGE)
				&& distance != -1) {
			goodDistanceInitialized = true;
			// framesSinceGoodFrame = 0;
			lastGoodDistance = distance;
			double dDistance = distance - targetDistanceInches;
			// FIXME (both use left)
			double leftEncoderDistance = driveTrain.getLeftEncoderDistance();
			driveTrain.setEncoderSetpointInches(dDistance + leftEncoderDistance, dDistance + leftEncoderDistance);
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
		// return driveTrain.isAtEncoderSetpoint();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		driveTrain.setDriveManual();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
