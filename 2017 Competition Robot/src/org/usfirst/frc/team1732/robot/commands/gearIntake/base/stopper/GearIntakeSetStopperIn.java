package org.usfirst.frc.team1732.robot.commands.gearIntake.base.stopper;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class GearIntakeSetStopperIn extends InstantCommand {
	public GearIntakeSetStopperIn() {
		super();
		requires(Robot.gearIntake);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.gearIntake.setStopperIn();
	}
}
