package org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor;

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

}
