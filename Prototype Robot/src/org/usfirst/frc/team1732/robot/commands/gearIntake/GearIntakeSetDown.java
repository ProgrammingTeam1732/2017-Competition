package org.usfirst.frc.team1732.robot.commands.gearIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearIntakeSetDown extends CommandGroup {

	public GearIntakeSetDown() {
		addSequential(new GearIntakeSetDownNoCheck());
	}
}
