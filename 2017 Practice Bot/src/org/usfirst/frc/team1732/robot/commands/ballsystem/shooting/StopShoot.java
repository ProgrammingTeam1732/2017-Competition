package org.usfirst.frc.team1732.robot.commands.ballsystem.shooting;

import org.usfirst.frc.team1732.robot.commands.ballsystem.ballintake.motor.BallIntakeSetStop;
import org.usfirst.frc.team1732.robot.commands.ballsystem.feeder.FeederSetStop;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StopShoot extends CommandGroup {

	public StopShoot() {
		addSequential(new FeederSetStop());
		addSequential(new BallIntakeSetStop());
	}
}
