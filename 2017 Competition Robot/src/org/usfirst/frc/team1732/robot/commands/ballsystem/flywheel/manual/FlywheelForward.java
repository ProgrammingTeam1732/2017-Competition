package org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.manual;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class FlywheelForward extends Command {

	public FlywheelForward() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.flywheel);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.flywheel.setSpeed(.3);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// Robot.flywheel.setSpeed(.3);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.flywheel.setSpeed(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}