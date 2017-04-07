package org.usfirst.frc.team1732.robot.commands.test;

import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.ShootTime;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.ShuffleBallsWithWait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestShootingWithBallAgitator extends CommandGroup {

	public TestShootingWithBallAgitator() {
		addParallel(new ShuffleBallsWithWait());
		addSequential(new ShootTime(5));
	}
}
