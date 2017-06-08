package org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeSetIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetDown;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GearIntakeSetDownIn extends CommandGroup {

	public GearIntakeSetDownIn() {
		// addSequential(new GearIntakeSetStopperIn());
		addSequential(new GearIntakeSetDown());
		addSequential(new GearIntakeSetIn());
	}
}
