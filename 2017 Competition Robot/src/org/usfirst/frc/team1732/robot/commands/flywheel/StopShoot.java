package org.usfirst.frc.team1732.robot.commands.flywheel;

import org.usfirst.frc.team1732.robot.commands.BallIntake.BallIntakeSetStop;
import org.usfirst.frc.team1732.robot.commands.feeder.FeederSetStop;

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
