package org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor;

import static org.usfirst.frc.team1732.robot.Robot.gearIntake;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class TeleopGearHold extends InstantCommand {

	public TeleopGearHold() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(gearIntake);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if (gearIntake.isUp())//&& gearIntake.gearIsInHold())
			gearIntake.setHold();
		else
			gearIntake.setStop();
	}

}
