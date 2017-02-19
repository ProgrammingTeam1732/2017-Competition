package org.usfirst.frc.team1732.robot.commands.BallIntake;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class BallIntakeSetUp extends InstantCommand {

	public BallIntakeSetUp() {
		requires(Robot.ballIntake);
	}

	@Override
	protected void initialize() {
		if (Robot.gearIntake.isDown()) {
			Robot.ballIntake.setPosistionUp();
		}
	}

}
