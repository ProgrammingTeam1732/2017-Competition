package org.usfirst.frc.team1732.robot.commands.ballsystem.ballandfeeder;

import org.usfirst.frc.team1732.robot.commands.ballsystem.ballintake.motor.BallIntakeSetTimedIn;
import org.usfirst.frc.team1732.robot.commands.ballsystem.ballintake.position.BallIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.ballsystem.feeder.FeederSetStop;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StopIntakeAndFeeder extends CommandGroup {

	public StopIntakeAndFeeder() {
		addSequential(new BallIntakeSetDown());
		addSequential(new BallIntakeSetTimedIn(1));
		addSequential(new FeederSetStop());
	}
}
