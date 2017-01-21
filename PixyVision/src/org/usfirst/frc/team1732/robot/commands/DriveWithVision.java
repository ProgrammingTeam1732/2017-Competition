package org.usfirst.frc.team1732.robot.commands;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;
import static org.usfirst.frc.team1732.robot.Robot.visionMain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveWithVision extends Command {

	public final double targetDistanceInches;

	public DriveWithVision(double aTargetDistanceInches) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(driveTrain);
		targetDistanceInches = aTargetDistanceInches;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		driveTrain.setDriveWithEncoders();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		visionMain.run();
		double distance = visionMain.getInchesToGearPeg();
		driveTrain.zeroEncoders();
		driveTrain.setEncoderSetpoint(distance - targetDistanceInches);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return driveTrain.isAtEncoderSetpoint();
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
