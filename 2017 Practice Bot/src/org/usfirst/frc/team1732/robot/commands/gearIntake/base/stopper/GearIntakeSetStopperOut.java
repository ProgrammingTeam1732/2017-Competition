package org.usfirst.frc.team1732.robot.commands.gearIntake.base.stopper;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class GearIntakeSetStopperOut extends InstantCommand {
	public GearIntakeSetStopperOut() {
		super();
		requires(Robot.gearIntake);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.gearIntake.setStopperOut();
	}
}
