package org.usfirst.frc.team1732.robot.commands.gearIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GrabGear extends CommandGroup {

	public GrabGear(double timeout, boolean useTimeOut) {
		addSequential(new GearIntakeSetDown());
		addSequential(new GearIntakeInCurrent(timeout, useTimeOut));
		addParallel(new GearIntakeSetUpTimedIn(.3));
		// addSequential(new GearIntakeSetUp());
		// addSequential(new Wait(2));
		// addSequential(new GearIntakeSetStop());
	}
}
