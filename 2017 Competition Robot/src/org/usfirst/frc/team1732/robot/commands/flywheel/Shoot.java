package org.usfirst.frc.team1732.robot.commands.flywheel;

import org.usfirst.frc.team1732.robot.commands.BallIntake.BallIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.BallIntake.BallIntakeSetIn;
import org.usfirst.frc.team1732.robot.commands.feeder.FeederSetOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Shoot extends CommandGroup {

	public Shoot() {
		addSequential(new EnableFlywheel());
		addSequential(new BallIntakeSetDown());
		addSequential(new FeederSetOut());
		addSequential(new BallIntakeSetIn());
	}
}
