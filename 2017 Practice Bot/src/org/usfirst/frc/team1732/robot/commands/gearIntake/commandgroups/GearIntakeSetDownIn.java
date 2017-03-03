package org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetIn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearIntakeSetDownIn extends CommandGroup {

	public GearIntakeSetDownIn() {
		addSequential(new GearIntakeSetDown());
		addSequential(new GearIntakeSetIn());
	}
}
