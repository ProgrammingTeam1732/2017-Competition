package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveEncodersGetSetpointAtRuntime extends Command {

	private final DoubleSupplier	leftDistance;
	private final DoubleSupplier	rightDistance;

	private double	left	= 0;
	private double	right	= 0;

	public DriveEncodersGetSetpointAtRuntime(DoubleSupplier distanceInches) {
		this(distanceInches, distanceInches);
	}

	public DriveEncodersGetSetpointAtRuntime(DoubleSupplier leftInches, DoubleSupplier rightInches) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(driveTrain);
		leftDistance = leftInches;
		rightDistance = rightInches;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		driveTrain.resetEncoders();
		left = ((int) (leftDistance.getAsDouble() * 1000)) / 1000.0;
		right = ((int) (rightDistance.getAsDouble() * 1000)) / 1000.0;
		driveTrain.setLeftEncoderSetpoint(left);
		driveTrain.setRightEncoderSetpoint(right);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double leftOutput = driveTrain.getLeftPIDOutput();
		double rightOutput = driveTrain.getRightPIDOutput();

		if (left == right) {
			leftOutput = leftOutput + driveTrain.getLeftRightAdjustment();
			rightOutput = rightOutput - driveTrain.getLeftRightAdjustment();
		}

		double max = Math.max(Math.abs(leftOutput), Math.abs(rightOutput));

		if (max > 1) {
			leftOutput = leftOutput / max;
			rightOutput = rightOutput / max;
		}

		driveTrain.driveRaw(leftOutput, rightOutput);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return driveTrain.encodersOnTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		driveTrain.driveRaw(0, 0);
	}
}
