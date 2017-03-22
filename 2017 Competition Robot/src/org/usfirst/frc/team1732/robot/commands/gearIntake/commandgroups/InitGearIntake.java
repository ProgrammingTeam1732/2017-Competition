package org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetStopperOut;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class InitGearIntake extends CommandGroup {

	public InitGearIntake() {
		addSequential(new GearIntakeSetDown());
		addSequential(new Wait(0.2));
		addSequential(new GearIntakeSetStopperOut());
		addSequential(new Wait(0.1));
		addSequential(new GearIntakeSetUp());
	}
}
