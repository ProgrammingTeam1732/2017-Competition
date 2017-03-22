package org.usfirst.frc.team1732.robot.commands.ballandfeeder;

import org.usfirst.frc.team1732.robot.commands.ballintake.BallIntakeSetOut;
import org.usfirst.frc.team1732.robot.commands.ballintake.BallIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.feeder.FeederSetOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class OutputBalls extends CommandGroup {

	public OutputBalls() {
		addSequential(new BallIntakeSetUp());
		addSequential(new BallIntakeSetOut());
		//addSequential(new FeederSetOut());
	}
}
