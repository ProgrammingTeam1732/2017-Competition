package org.usfirst.frc.team1732.robot.autocommands.test;

import org.usfirst.frc.team1732.robot.commands.flywheel.ShootTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.ShuffleBallsWithWait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestShootingWithBallAgitator extends CommandGroup {

	public TestShootingWithBallAgitator() {
		// addParallel(new CommandGroup() {
		// {
		// addParallel(new GearIntakeSetDown());
		addSequential(new ShuffleBallsWithWait());
		// }
		// });

		addSequential(new ShootTime(5));
	}
}
