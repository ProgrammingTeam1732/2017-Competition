package org.usfirst.frc.team1732.robot.commands.ballsystem.shooting;

import org.usfirst.frc.team1732.robot.commands.ballsystem.ballintake.motor.BallIntakeSetIn;
import org.usfirst.frc.team1732.robot.commands.ballsystem.ballintake.position.BallIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.ballsystem.feeder.FeederSetOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Shoot extends CommandGroup {

	public Shoot() {
		//addSequential(new EnableFlywheel());
		addSequential(new BallIntakeSetDown()); // sets ball intake down
		addSequential(new FeederSetOut());
		addSequential(new BallIntakeSetIn());
	}
}
