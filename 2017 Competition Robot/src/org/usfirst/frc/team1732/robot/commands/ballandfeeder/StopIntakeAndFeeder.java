package org.usfirst.frc.team1732.robot.commands.ballandfeeder;

import org.usfirst.frc.team1732.robot.commands.ballintake.BallIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.ballintake.BallIntakeSetStop;
import org.usfirst.frc.team1732.robot.commands.feeder.FeederSetStop;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team1732.robot.commands.ballintake.BallIntakeSetTimedIn;
/**
 *
 */
public class StopIntakeAndFeeder extends CommandGroup {

	public StopIntakeAndFeeder() {
		addSequential(new BallIntakeSetDown());
		addSequential(new BallIntakeSetTimedIn(1));
		addSequential(new FeederSetStop());
	}
}
