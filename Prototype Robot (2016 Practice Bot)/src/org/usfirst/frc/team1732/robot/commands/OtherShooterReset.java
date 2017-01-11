package org.usfirst.frc.team1732.robot.commands;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OtherShooterReset extends Command {

	public OtherShooterReset() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.otherShooter);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.otherShooter.reset();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {}
}
