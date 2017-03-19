package org.usfirst.frc.team1732.robot.commands.ballintake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class BallIntakeSetDoneGroup extends CommandGroup {

	public BallIntakeSetDoneGroup() {
		addParallel(new BallIntakeSetDown());
		addParallel(new BallIntakeSetDoneGroup());
	}
}
