package org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetStop;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetUp;

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
