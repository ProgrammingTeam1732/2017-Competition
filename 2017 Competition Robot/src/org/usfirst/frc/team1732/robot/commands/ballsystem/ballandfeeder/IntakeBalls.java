package org.usfirst.frc.team1732.robot.commands.ballsystem.ballandfeeder;

import org.usfirst.frc.team1732.robot.commands.ballsystem.ballintake.motor.BallIntakeSetIn;
import org.usfirst.frc.team1732.robot.commands.ballsystem.ballintake.position.BallIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.ballsystem.feeder.FeederSetIn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeBalls extends CommandGroup {

	public IntakeBalls() {
		addSequential(new BallIntakeSetUp());
		addSequential(new BallIntakeSetIn());
		addSequential(new FeederSetIn());
	}
}
