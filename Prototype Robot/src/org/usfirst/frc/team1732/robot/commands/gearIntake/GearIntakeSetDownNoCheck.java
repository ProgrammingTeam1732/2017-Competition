package org.usfirst.frc.team1732.robot.commands.gearIntake;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

class GearIntakeSetDownNoCheck extends InstantCommand {

	public GearIntakeSetDownNoCheck() {
		super();
		requires(Robot.gearIntake);
	}

	@Override
	protected void initialize() {
		Robot.gearIntake.setDown();
	}
}
