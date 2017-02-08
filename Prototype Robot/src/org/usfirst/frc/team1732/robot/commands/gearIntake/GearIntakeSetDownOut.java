package org.usfirst.frc.team1732.robot.commands.gearIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearIntakeSetDownOut extends CommandGroup {

	public GearIntakeSetDownOut() {
		addSequential(new GearIntakeSetDown());
		addSequential(new GearIntakeSetOut());
	}
}
