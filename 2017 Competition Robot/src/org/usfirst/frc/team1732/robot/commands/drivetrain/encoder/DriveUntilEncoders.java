package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveUntilEncoders extends Command {

	private final double	leftSetpoint;
	private final double	rightSetpoint;
	private final double	left;
	private final double	right;
	private boolean			stop;

	public DriveUntilEncoders(double leftSetpoint, double rightSetpoint, double leftSpeed, double rightSpeed,
			boolean stop) {
		requires(Robot.driveTrain);
		this.leftSetpoint = leftSetpoint;
		this.rightSetpoint = rightSetpoint;
		left = leftSpeed;
		right = rightSpeed;
		this.stop = stop;
	}

	public DriveUntilEncoders(double distanceInches, double leftSpeed, double rightSpeed, boolean stop) {
		this(distanceInches, distanceInches, leftSpeed, rightSpeed, stop);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("running forward");
		Robot.driveTrain.resetEncoders();
		Robot.driveTrain.setLeftEncoderSetpoint(leftSetpoint);
		Robot.driveTrain.setRightEncoderSetpoint(rightSetpoint);
		Robot.driveTrain.driveRaw(left, right);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		boolean leftOvershoot = Math.abs(Robot.driveTrain.getLeftDistance()) > Math.abs(leftSetpoint);
		boolean rightOvershoot = Math.abs(Robot.driveTrain.getRightDistance()) > Math.abs(rightSetpoint);
		return Robot.driveTrain.encodersOnTarget() || (leftOvershoot && rightOvershoot);
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		if (stop)
			Robot.driveTrain.driveRaw(0, 0);
	}
}
