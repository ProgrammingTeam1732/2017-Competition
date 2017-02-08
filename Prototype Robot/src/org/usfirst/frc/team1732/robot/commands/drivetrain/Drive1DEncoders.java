package org.usfirst.frc.team1732.robot.commands.drivetrain;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Drive1DEncoders extends Command {

	private final double	leftDistance;
	private final double	rightDistance;

	public Drive1DEncoders(double distanceInches) {
		this(distanceInches, distanceInches);
	}

	public Drive1DEncoders(double leftInches, double rightInches) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(driveTrain);
		leftDistance = leftInches;
		rightDistance = rightInches;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		driveTrain.leftEncoder.reset();
		driveTrain.rightEncoder.reset();
		driveTrain.leftEncoderPID.setSetpoint(leftDistance);
		driveTrain.rightEncoderPID.setSetpoint(rightDistance);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double leftOutput = driveTrain.leftEncoderPID.get();
		double rightOutput = driveTrain.rightEncoderPID.get();
		driveTrain.driveRaw(leftOutput, rightOutput);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return driveTrain.leftEncoderPID.onTarget() && driveTrain.rightEncoderPID.onTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		driveTrain.driveRaw(0, 0);
	}

}
