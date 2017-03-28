package org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class GearIntakeSetIn extends InstantCommand {

	public GearIntakeSetIn() {
		super();
		requires(Robot.gearIntake);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.gearIntake.setIn();
	}

}
