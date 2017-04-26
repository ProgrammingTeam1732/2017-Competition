package org.usfirst.frc.team1732.robot.commands.ballsystem.ballintake.motor;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class BallIntakeSetIn extends InstantCommand {

    public BallIntakeSetIn() {
	super();
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(Robot.ballIntake);
    }

    // Called once when the command executes
    @Override
    protected void initialize() {
	Robot.ballIntake.setSpeedIn();
    }

}