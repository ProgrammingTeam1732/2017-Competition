package org.usfirst.frc.team1732.robot.commands.flywheel;

import org.usfirst.frc.team1732.robot.commands.ballintake.BallIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.ballintake.BallIntakeSetIn;
import org.usfirst.frc.team1732.robot.commands.feeder.FeederSetOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Shoot extends CommandGroup {

	public Shoot() {
		addSequential(new EnableFlywheel());
		addSequential(new BallIntakeSetDown()); // sets ball intake down
		addSequential(new FeederSetOut());
		addSequential(new BallIntakeSetIn());
	}
}
