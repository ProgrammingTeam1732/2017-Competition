package org.usfirst.frc.team1732.robot.commands.unused;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class BallIntakeSetForward extends InstantCommand {

	public BallIntakeSetForward() {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.ballIntake);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.ballIntake.setForward();
	}

}
