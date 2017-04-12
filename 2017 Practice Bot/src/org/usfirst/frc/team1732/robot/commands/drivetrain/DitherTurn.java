package org.usfirst.frc.team1732.robot.commands.drivetrain;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DitherTurn extends Command {

	private final double	angleSetpoint;
	private final double	ditherInterval;
	private final double	ditherLength;

	private long startTime;

	public DitherTurn(double angle, double ditherInterval, double ditherLength) {
		requires(Robot.driveTrain);
		angleSetpoint = angle;
		this.ditherInterval = ditherInterval;
		this.ditherLength = ditherLength;
		setTimeout(10);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		startTime = System.currentTimeMillis();
		Robot.driveTrain.resetEncoders();
		double setpoint = angleSetpoint / 360.0 * DriveTrain.TURNING_CIRCUMFERENCE;
		Robot.driveTrain.setLeftEncoderSetpoint(setpoint);
		Robot.driveTrain.setRightEncoderSetpoint(-setpoint);
		Robot.driveTrain.setEncoderToTurningPID();
		Robot.driveTrain.setEncoderDeadband(DriveTrain.ENCODER_TURNING_DEADBAND_INCHES);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double leftError = Robot.driveTrain.getLeftPIDError();
		double rightError = Robot.driveTrain.getRightPIDError();
		double leftRightAdjustment = (leftError + rightError) * DriveTrain.errorDifferenceScalar;

		double left = Robot.driveTrain.getLeftPIDOutput();
		double right = Robot.driveTrain.getRightPIDOutput();

		if (System.currentTimeMillis() - startTime > ditherInterval) {
			if (System.currentTimeMillis() - startTime - ditherInterval < ditherLength) {// && Math.abs(output) <.35) {
				left = Math.copySign(.35, left);
				right = Math.copySign(.35, right);
			} else {
				startTime = System.currentTimeMillis();
			}
		} else {
			left = 0;
			right = 0;
		}

		left = left + leftRightAdjustment;
		right = right + leftRightAdjustment;
		driveTrain.driveRawAbsoluteLimit(left, right, .178, 1);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.gearIntake.isUp();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.pixyCamera.turnOffLights();
	}

}
