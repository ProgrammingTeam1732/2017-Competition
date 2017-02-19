package org.usfirst.frc.team1732.robot.commands.ballandfeeder;

import org.usfirst.frc.team1732.robot.commands.BallIntake.BallIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.BallIntake.BallIntakeSetStop;
import org.usfirst.frc.team1732.robot.commands.feeder.FeederSetStop;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StopIntakeAndFeeder extends CommandGroup {

	public StopIntakeAndFeeder() {
		addSequential(new BallIntakeSetDown());
		addSequential(new BallIntakeSetStop());
		addSequential(new FeederSetStop());
	}
}
