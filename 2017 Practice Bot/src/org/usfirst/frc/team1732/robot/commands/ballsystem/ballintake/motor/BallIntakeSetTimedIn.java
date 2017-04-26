package org.usfirst.frc.team1732.robot.commands.ballsystem.ballintake.motor;

import static org.usfirst.frc.team1732.robot.Robot.ballIntake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BallIntakeSetTimedIn extends Command {

    public BallIntakeSetTimedIn(double timeSeconds) {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(ballIntake);
	setTimeout(timeSeconds);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
	ballIntake.setSpeedIn();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	return isTimedOut();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	ballIntake.setSpeedStop();
    }

}