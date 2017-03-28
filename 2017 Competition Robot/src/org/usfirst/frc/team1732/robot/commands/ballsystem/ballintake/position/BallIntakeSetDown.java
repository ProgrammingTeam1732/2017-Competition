package org.usfirst.frc.team1732.robot.commands.ballsystem.ballintake.position;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class BallIntakeSetDown extends InstantCommand {

	public BallIntakeSetDown() {
		requires(Robot.ballIntake);
	}

	@Override
	protected void initialize() {
		Robot.ballIntake.setPositionDown();
	}

}
