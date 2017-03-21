package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveEncodersGetSetpointAtRuntime extends Command {

	private final DoubleSupplier leftDistance;
	private final DoubleSupplier rightDistance;

	private final double offset;

	public DriveEncodersGetSetpointAtRuntime(DoubleSupplier distanceInches, double offset) {
		this(distanceInches, distanceInches, offset);
	}

	public DriveEncodersGetSetpointAtRuntime(DoubleSupplier leftInches, DoubleSupplier rightInches, double offset) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(driveTrain);
		leftDistance = leftInches;
		rightDistance = rightInches;
		this.offset = offset;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		driveTrain.resetEncoders();
		driveTrain.setLeftEncoderSetpoint(leftDistance.getAsDouble());
		driveTrain.setRightEncoderSetpoint(rightDistance.getAsDouble());
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double leftOutput = driveTrain.getLeftPIDOutput();
		double rightOutput = driveTrain.getRightPIDOutput();

		if (leftDistance.getAsDouble() == rightDistance.getAsDouble()) {
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
		return driveTrain.encodersOnTarget(offset);
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		driveTrain.driveRaw(0, 0);
	}
}
