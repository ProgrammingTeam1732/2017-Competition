package org.usfirst.frc.team1732.robot.commands.ballandfeeder;

import org.usfirst.frc.team1732.robot.commands.BallIntake.BallIntakeSetOut;
import org.usfirst.frc.team1732.robot.commands.BallIntake.BallIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.feeder.FeederSetOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class OutputBalls extends CommandGroup {

	public OutputBalls() {
		addSequential(new BallIntakeSetUp());
		addSequential(new BallIntakeSetOut());
		addSequential(new FeederSetOut());
	}
}
