package org.usfirst.frc.team1732.robot.commands;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FlywheelBangBang extends Command {

	public FlywheelBangBang() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.flywheel);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {}

	private long	startTime	= System.currentTimeMillis();
	private int		iterations	= 0;

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		System.out.printf("Time: %s, Iter: %d%n", "" + (System.currentTimeMillis() - startTime), iterations++);
		startTime = System.currentTimeMillis();
		if (Robot.flywheel.getSpeed() < Robot.flywheel.getSetpoint()) {
			Robot.flywheel.setMotorSpeed(Robot.flywheel.BANG_BANG_UPPER);
		} else {
			Robot.flywheel.setMotorSpeed(Robot.flywheel.BANG_BANG_FF);
		}
		System.out.printf("Speed: %f, Output: %f%n%n", Robot.flywheel.getSpeed(), Robot.flywheel.getMotorOutput());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.flywheel.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
