package org.usfirst.frc.team1732.robot.commands.climber;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.stopper.GearIntakeSetStopperIn;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ArmSetOutGroup extends CommandGroup {

	public ArmSetOutGroup() {
		addSequential(new GearIntakeSetDown());
		addSequential(new Wait(0.2));
		addSequential(new GearIntakeSetUp());
		addSequential(new GearIntakeSetStopperIn());
		addSequential(new Wait(.5));
		addSequential(new ArmSetOut());
	}

	@Override
	public void interrupted() {
		super.interrupted();
		System.out.println("interrupted");
	}
}
