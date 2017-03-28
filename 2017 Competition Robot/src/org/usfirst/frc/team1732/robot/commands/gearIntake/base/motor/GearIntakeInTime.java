package org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor;

import static org.usfirst.frc.team1732.robot.Robot.gearIntake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearIntakeInTime extends Command {

	public GearIntakeInTime(double timeSeconds) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(gearIntake);
		setTimeout(timeSeconds);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		gearIntake.setIn();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		gearIntake.setStop();
	}

}
