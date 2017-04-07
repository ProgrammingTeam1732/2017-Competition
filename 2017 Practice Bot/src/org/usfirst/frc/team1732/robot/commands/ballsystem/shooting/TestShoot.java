package org.usfirst.frc.team1732.robot.commands.ballsystem.shooting;

import org.usfirst.frc.team1732.robot.commands.ballsystem.feeder.FeederSetIn;
import org.usfirst.frc.team1732.robot.commands.ballsystem.feeder.FeederSetStop;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestShoot extends CommandGroup {

	public TestShoot() {
		addSequential(new FeederSetIn());
		addSequential(new Wait(2));
		addSequential(new FeederSetStop());
		addSequential(new ShootTime(5));
	}
}
