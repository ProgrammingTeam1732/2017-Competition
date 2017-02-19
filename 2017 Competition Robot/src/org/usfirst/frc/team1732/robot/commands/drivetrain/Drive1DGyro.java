package org.usfirst.frc.team1732.robot.commands.drivetrain;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive1DGyro extends Command {
	private final double setpoint;

	private final double left;
	private final double right;
	public Drive1DGyro(double degreesSetpoint, double leftSide, double rightSide) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(driveTrain);
		setpoint = degreesSetpoint;
		left = leftSide;
		right = rightSide;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.driveTrain.gyro.reset();
		Robot.driveTrain.gyroPID.setSetpoint(setpoint);
	//	Robot.driveTrain.gyroPID.setAbsoluteTolerance(5);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		driveTrain.driveRaw(left,  right);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.driveTrain.gyroPID.onTarget();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.driveRaw(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
