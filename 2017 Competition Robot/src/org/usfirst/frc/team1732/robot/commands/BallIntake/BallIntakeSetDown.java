package org.usfirst.frc.team1732.robot.commands.BallIntake;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class BallIntakeSetDown extends InstantCommand {

	public BallIntakeSetDown() {
		requires(Robot.gearIntake);
		requires(Robot.ballIntake);
	}

	@Override
	protected void initialize() {
		if (Robot.gearIntake.isDown()) {
			Robot.gearIntake.setUp();
		}
		Robot.ballIntake.setDown();
	}

}
