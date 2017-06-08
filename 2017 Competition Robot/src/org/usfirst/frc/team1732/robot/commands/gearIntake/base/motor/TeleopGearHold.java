package org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor;

import static org.usfirst.frc.team1732.robot.Robot.gearIntake;

import edu.wpi.first.wpilibj.command.Command;

public class TeleopGearHold extends Command {

	public TeleopGearHold() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(gearIntake);
	}

	@Override
	public void execute() {

	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if (gearIntake.isUp() && gearIntake.gearIsHeld())
			gearIntake.setHold();
		else
			gearIntake.setStop();
	}

	@Override
	protected boolean isFinished() {
		return !gearIntake.gearIsHeld();
	}

	@Override
	protected void end() {
		gearIntake.setStop();
	}

	protected void inturrupted() {
		end();
	}

}
