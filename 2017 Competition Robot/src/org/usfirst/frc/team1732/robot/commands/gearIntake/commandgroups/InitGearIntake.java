package org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeSetIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeSetStop;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.stopper.GearIntakeSetStopperOut;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class InitGearIntake extends CommandGroup {

	public InitGearIntake() {
		addSequential(new GearIntakeSetIn());

		addSequential(new GearIntakeSetDown());
		addSequential(new Wait(0.2));
		addSequential(new GearIntakeSetStopperOut());
		addSequential(new Wait(0.1));
		addSequential(new GearIntakeSetUp());

		addSequential(new GearIntakeSetStop());
	}
}
