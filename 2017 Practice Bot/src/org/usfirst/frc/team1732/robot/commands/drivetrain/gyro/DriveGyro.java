package org.usfirst.frc.team1732.robot.commands.drivetrain.gyro;

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
		driveTrain.resetGyro();
		driveTrain.setGyroSetpoint(setpoint);
		driveTrain.driveRaw(left, right);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return driveTrain.gyroOnTarget() || Math.abs(driveTrain.getAngle()) > Math.abs(setpoint);
		// the second condition is so that if this overshoots the setpoint it
		// will stop
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
