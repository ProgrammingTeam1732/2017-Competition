package org.usfirst.frc.team1732.robot.commands.gearIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearIntakeSetUpTimedIn extends CommandGroup {

	public GearIntakeSetUpTimedIn(double time) {
		addSequential(new GearIntakeSetUp());
		addSequential(new GearIntakeInTime(time));
	}
}
