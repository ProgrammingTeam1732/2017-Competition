package org.usfirst.frc.team1732.robot.commands.gearIntake;

import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearIntakeSetDown extends CommandGroup {

	public GearIntakeSetDown() {
		addSequential(new Wait(0));
		addSequential(new GearIntakeSetDownNoCheck());
	}
}
