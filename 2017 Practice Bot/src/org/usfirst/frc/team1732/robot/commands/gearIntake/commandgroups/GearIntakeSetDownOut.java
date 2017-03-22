package org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetOut;

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
