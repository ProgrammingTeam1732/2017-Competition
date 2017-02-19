package org.usfirst.frc.team1732.robot.commands.ballandfeeder;

import org.usfirst.frc.team1732.robot.commands.BallIntake.BallIntakeSetIn;
import org.usfirst.frc.team1732.robot.commands.BallIntake.BallIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.feeder.FeederSetIn;

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
