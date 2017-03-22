package org.usfirst.frc.team1732.robot.commands.gearIntake.base;

import static org.usfirst.frc.team1732.robot.Robot.gearIntake;

import edu.wpi.first.wpilibj.command.Command;

public class GearIntakeInCurrentTime extends Command {

	public GearIntakeInCurrentTime(double timeout, boolean useTimeOut) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(gearIntake);
		if (useTimeOut)
			setTimeout(timeout);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		gearIntake.setIn();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return gearIntake.gearIsIn() || isTimedOut();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {}

}
