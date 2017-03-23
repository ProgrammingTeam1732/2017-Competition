package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class TurnWithEncoders extends Command {

	private final DoubleSupplier angle;

	public TurnWithEncoders(DoubleSupplier angle) {
		this.angle = angle;
	}

	public TurnWithEncoders(double angle) {
		this(() -> angle);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		// Robot.driveTrain.setEncoderPIDS(0.125, 0, 0);
		// Robot.driveTrain.setEncoderDeadband(3);
		Robot.driveTrain.resetEncoders();
		double setpoint = angle.getAsDouble() / 360.0 * DriveTrain.TURNING_CIRCUMFERENCE;
		Robot.driveTrain.setLeftEncoderSetpoint(setpoint);
		Robot.driveTrain.setRightEncoderSetpoint(-setpoint);
		Robot.driveTrain.setEncoderToTurningPID();
		Robot.driveTrain.setEncoderDeadband(DriveTrain.ENCODER_TURNING_DEADBAND_INCHES);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (Math.abs(driveTrain.getLeftPIDError()) < DriveTrain.ENCODER_IZONE_TURNING
				|| Math.abs(driveTrain.getRightPIDError()) < DriveTrain.ENCODER_IZONE_TURNING) {
			driveTrain.setEncoderPIDS(DriveTrain.encoderTurningP, DriveTrain.ENCODER_IZONE_TURNING_I,
					DriveTrain.encoderTurningD);
		} else {
			driveTrain.setEncoderToTurningPID();
		}

		double leftError = Robot.driveTrain.getLeftPIDError();
		double rightError = Robot.driveTrain.getRightPIDError();
		double leftRightAdjustment = (leftError + rightError) * DriveTrain.errorDifferenceScalar;

		double left = Robot.driveTrain.getLeftPIDOutput();
		double right = Robot.driveTrain.getRightPIDOutput();

		left = left + leftRightAdjustment;
		right = right + leftRightAdjustment;
		Robot.driveTrain.driveRaw(left, right);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.driveTrain.encodersOnTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveTrain.driveRaw(0, 0);
		Robot.driveTrain.resetEncoderPIDValues();
		Robot.driveTrain.resetEncoderDeadband();
	}
}
