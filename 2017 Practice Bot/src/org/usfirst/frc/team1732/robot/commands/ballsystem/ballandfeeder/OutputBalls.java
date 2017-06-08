package org.usfirst.frc.team1732.robot.commands.ballsystem.ballandfeeder;

import org.usfirst.frc.team1732.robot.commands.ballsystem.ballintake.motor.BallIntakeSetOut;
import org.usfirst.frc.team1732.robot.commands.ballsystem.ballintake.position.BallIntakeSetUp;

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
