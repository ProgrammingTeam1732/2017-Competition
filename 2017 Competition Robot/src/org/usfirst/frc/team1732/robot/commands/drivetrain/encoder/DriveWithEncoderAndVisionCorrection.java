package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveWithEncoderAndVisionCorrection extends Command {

	private DoubleSupplier distance;

	public static final double ANGLE_P = 0.04;

	public DriveWithEncoderAndVisionCorrection(DoubleSupplier distance) {
		this.distance = distance;
	}

	public DriveWithEncoderAndVisionCorrection(double distance) {
		this(() -> distance);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		driveTrain.resetEncoders();
		Robot.driveTrain.setEncoderSetpoint(distance.getAsDouble());
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double left = Robot.driveTrain.getLeftPIDOutput();
		double right = Robot.driveTrain.getRightPIDOutput();
		double output = (left + right) / 2.0;
		Robot.visionMain.run();
		double angle = Robot.visionMain.getAngleToGearPeg();
		double angleAdjust = angle * ANGLE_P;
		double leftOutput = output + angleAdjust;
		double rightOutput = output - angleAdjust;
		Robot.driveTrain.driveRaw(leftOutput, rightOutput);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		boolean rightGood = Math.abs(Robot.driveTrain.getRightPIDError()) < DriveTrain.ENCODER_DEADBAND_INCHES;
		boolean leftGood = Math.abs(Robot.driveTrain.getLeftPIDError()) < DriveTrain.ENCODER_DEADBAND_INCHES;
		return leftGood || rightGood;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveTrain.driveRaw(0, 0);
	}

}
