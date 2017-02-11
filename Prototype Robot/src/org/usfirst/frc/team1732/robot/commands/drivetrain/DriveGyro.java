package org.usfirst.frc.team1732.robot.commands.drivetrain;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveGyro extends Command {

	private final double	left;
	private final double	right;
	private final double	setpoint;

	public DriveGyro(double angleSetpoint, double leftSpeed, double rightSpeed) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(driveTrain);
		setpoint = angleSetpoint;
		left = leftSpeed;
		right = rightSpeed;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		driveTrain.gyro.reset();
		double p = driveTrain.gyroP;
		double i = driveTrain.gyroI;
		double d = driveTrain.gyroD;
		driveTrain.gyroPID.setPID(p, i, d);
		driveTrain.gyroPID.setSetpoint(setpoint);
		driveTrain.driveRaw(left, right);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return driveTrain.gyroPID.onTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		driveTrain.driveRaw(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {}
}
