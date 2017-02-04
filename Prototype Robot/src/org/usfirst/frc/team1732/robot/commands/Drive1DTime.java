package org.usfirst.frc.team1732.robot.commands;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive1DTime extends Command {
	private boolean	direction;	// Forward (true or backwards(false)
	private double	SPEED;

	public Drive1DTime(double sec, boolean direction) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(driveTrain);
		setTimeout(sec);
		if (direction)
			SPEED = -.5;
		else
			SPEED = .5;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		driveTrain.tankDrive(SPEED, SPEED);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return isTimedOut();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		driveTrain.tankDrive(0, 0);
	}

	@Override
	protected void interrupted() {
		end();
	}
}
