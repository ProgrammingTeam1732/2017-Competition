package org.usfirst.frc.team1732.robot.commands.gearIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearIntakeSetUpStop extends CommandGroup {

	public GearIntakeSetUpStop() {
		addSequential(new GearIntakeSetUp());
		addSequential(new GearIntakeSetStop());
	}
}
