package org.usfirst.frc.team1732.robot.commands.drivetrain;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveUntilEncoders extends Command {

	private final double	setpointInches;
	private final double	left;
	private final double	right;
	private boolean			stop;

	public DriveUntilEncoders(double distanceInches, double leftSpeed, double rightSpeed, boolean stop) {
		requires(Robot.driveTrain);
		setpointInches = distanceInches;
		left = leftSpeed;
		right = rightSpeed;
		this.stop = stop;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("running forward");
		Robot.driveTrain.leftEncoder.reset();
		Robot.driveTrain.rightEncoder.reset();
		Robot.driveTrain.leftEncoderPID.setSetpoint(setpointInches);
		Robot.driveTrain.rightEncoderPID.setSetpoint(setpointInches);
		Robot.driveTrain.driveRawNoLimit(left, right);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.driveTrain.encodersOnTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		if (stop)
			Robot.driveTrain.driveRaw(0, 0);
	}
}
