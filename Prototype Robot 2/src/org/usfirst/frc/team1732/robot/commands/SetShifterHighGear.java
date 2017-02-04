package org.usfirst.frc.team1732.robot.commands;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class SetShifterHighGear extends InstantCommand {

	public SetShifterHighGear() {
		super();
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.driveTrain.setHighGear();
	}

}
