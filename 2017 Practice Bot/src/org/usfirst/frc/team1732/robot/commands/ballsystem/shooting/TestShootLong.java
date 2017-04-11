package org.usfirst.frc.team1732.robot.commands.ballsystem.shooting;

import org.usfirst.frc.team1732.robot.commands.ballsystem.feeder.FeederSetIn;
import org.usfirst.frc.team1732.robot.commands.ballsystem.feeder.FeederSetStop;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestShootLong extends CommandGroup {

	public TestShootLong() {
		addSequential(new EnableFlywheel());
		addSequential(new FeederSetIn());
		addSequential(new Wait(2));
		addSequential(new FeederSetStop());
		addParallel(new ShuffleBallsWithWait(3, 1.5));
		addSequential(new ShootTime(5));
	}
}