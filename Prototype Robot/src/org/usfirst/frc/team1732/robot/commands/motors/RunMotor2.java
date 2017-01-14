package org.usfirst.frc.team1732.robot.commands.motors;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunMotor2 extends Command {

	public RunMotor2() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		super(Robot.motor2.name);
		requires(Robot.motor2);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.motor2.run();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.motor2.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
