package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class TurnWithEncodersWithRamping extends Command {

	private final DoubleSupplier angle;

	public TurnWithEncodersWithRamping(DoubleSupplier angle) {
		this.angle = angle;
	}

	public TurnWithEncodersWithRamping(double angle) {
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
