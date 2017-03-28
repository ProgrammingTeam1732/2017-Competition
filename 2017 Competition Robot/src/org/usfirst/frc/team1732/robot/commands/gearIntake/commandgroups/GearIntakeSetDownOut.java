package org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeSetOut;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetDown;

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
