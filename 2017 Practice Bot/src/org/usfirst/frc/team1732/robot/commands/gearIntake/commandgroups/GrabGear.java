package org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeInCurrentDistance;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeInCurrentTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetDown;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GrabGear extends CommandGroup {

	public GrabGear(boolean useTimeout, double stopPoint) {
		addSequential(new GearIntakeSetDown());
		if (useTimeout) {
			addSequential(new GearIntakeInCurrentTime(stopPoint, useTimeout));
		} else {
			addSequential(new GearIntakeInCurrentDistance(stopPoint));

		}
		addParallel(new GearIntakeSetUpTimedIn(.3));
		// addSequential(new GearIntakeSetUp());
		// addSequential(new Wait(2));
		// addSequential(new GearIntakeSetStop());
	}
}
