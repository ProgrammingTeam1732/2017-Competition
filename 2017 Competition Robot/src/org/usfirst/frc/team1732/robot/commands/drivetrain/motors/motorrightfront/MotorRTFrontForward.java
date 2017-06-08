package org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorrightfront;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class MotorRTFrontForward extends Command {

	public MotorRTFrontForward() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.driveTrain.runMotorRtFront(1);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// Robot.driveTrain.runMotorRtFront(1);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveTrain.runMotorRtFront(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}