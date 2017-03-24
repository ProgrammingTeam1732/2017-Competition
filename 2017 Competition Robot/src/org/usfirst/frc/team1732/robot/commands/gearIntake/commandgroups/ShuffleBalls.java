package org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShuffleBalls extends CommandGroup {

	public ShuffleBalls() {
		addSequential(new GearIntakeSetDown());
		addSequential(new Wait(0.3));
		addSequential(new GearIntakeSetUp());
	}
}
