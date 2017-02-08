package org.usfirst.frc.team1732.robot.commands.gearIntake;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class GearIntakeSetDown extends InstantCommand {

	public GearIntakeSetDown() {
		super();
		requires(Robot.gearIntake);
	}

	@Override
	protected void initialize() {
		Robot.gearIntake.setDown();
	}
}
