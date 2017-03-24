package org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetStopperIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShuffleBallsWithWait extends CommandGroup {

	public ShuffleBallsWithWait() {
		// initially put stopper down
		addSequential(new GearIntakeSetDown());
		addSequential(new Wait(0.3));
		addSequential(new GearIntakeSetStopperIn());
		addSequential(new Wait(0.1));
		addSequential(new GearIntakeSetUp());

		// wait 3 sec
		addSequential(new Wait(3));
		// shuffle balls
		addSequential(new ShuffleBalls());
		// wait 3 sec
		addSequential(new Wait(3));
		// shuffle balls
		addSequential(new ShuffleBalls());
		// wait 3 sec
		addSequential(new Wait(3));
		// shuffle balls
		addSequential(new ShuffleBalls());
	}
}
