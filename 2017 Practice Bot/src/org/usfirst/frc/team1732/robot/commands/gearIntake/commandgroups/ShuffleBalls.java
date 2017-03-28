package org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetDownNoCheck;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetUpNoCheck;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShuffleBalls extends CommandGroup {

	public ShuffleBalls() {
		addSequential(new GearIntakeSetDownNoCheck());
		addSequential(new Wait(0.3));
		addSequential(new GearIntakeSetUpNoCheck());
	}
}
