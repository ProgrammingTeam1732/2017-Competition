package org.usfirst.frc.team1732.robot.commands.ballsystem.ballintake.motor;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class BallIntakeSetOut extends InstantCommand {

	public BallIntakeSetOut() {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.ballIntake);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.ballIntake.setSpeedOut();
	}

}
