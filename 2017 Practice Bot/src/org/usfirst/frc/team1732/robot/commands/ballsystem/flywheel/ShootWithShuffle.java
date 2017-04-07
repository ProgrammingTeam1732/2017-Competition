package org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShootWithShuffle extends CommandGroup {

	public ShootWithShuffle() {
		addSequential(new Shoot());
		addSequential(new ShuffleBallsWithWait());
	}
}
