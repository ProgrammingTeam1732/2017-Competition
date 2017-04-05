package org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeInTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeSetIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetDownNoCheck;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetUpNoCheck;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.stopper.GearIntakeSetStopperIn;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShuffleBallsWithWait extends CommandGroup {

	public ShuffleBallsWithWait() {
		// initially put stopper down
		System.out.println("Shuffle Balls With Wait");
		addSequential(new GearIntakeSetIn());
		System.out.println("GEAR INTAKE SET IN");
		addSequential(new GearIntakeSetDownNoCheck());
		addSequential(new Wait(0.3));
		addSequential(new GearIntakeSetStopperIn());
		addSequential(new Wait(0.1));
		addSequential(new GearIntakeSetUpNoCheck());
		System.out.println("GEAR INTAKE SET UP");

		// wait 3 sec
		addSequential(new GearIntakeInTime(1));
		addSequential(new Wait(2));

		// shuffle balls
		addSequential(new ShuffleBalls());
		System.out.println("Shuffle Balls 1");
		// wait 3 sec
		addSequential(new GearIntakeInTime(1));
		addSequential(new Wait(2));
		// shuffle balls
		addSequential(new ShuffleBalls());
		System.out.println("Shuffle Balls 2");
		// wait 3 sec
		addSequential(new GearIntakeInTime(1));
		addSequential(new Wait(2));
		// shuffle balls
		addSequential(new ShuffleBalls());

		System.out.println("Shuffle Balls 3");
	}

}
