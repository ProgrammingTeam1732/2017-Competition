package org.usfirst.frc.team1732.robot.commands.motors;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunMotor1 extends Command {

	public RunMotor1() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		super(Robot.motor1.name);
		requires(Robot.motor1);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.motor1.run();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.motor1.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
