package org.usfirst.frc.team1732.robot.commands.gearIntake;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearIntakeOutTime extends Command {

	public GearIntakeOutTime(double timeSeconds) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.gearIntake);
		setTimeout(timeSeconds);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.gearIntake.setOut();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.gearIntake.setStop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
