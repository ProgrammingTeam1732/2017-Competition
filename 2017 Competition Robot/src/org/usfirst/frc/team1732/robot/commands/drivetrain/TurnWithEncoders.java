package org.usfirst.frc.team1732.robot.commands.drivetrain;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TurnWithEncoders extends Command {

	private final double angle;

	public TurnWithEncoders(double angle) {
		requires(Robot.driveTrain);
		this.angle = angle + Math.signum(angle) * 40;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		// Robot.driveTrain.setEncoderPIDS(0.125, 0, 0);
		// Robot.driveTrain.setEncoderDeadband(3);
		Robot.driveTrain.resetEncoders();
		double setpoint = angle / 360 * Robot.driveTrain.TURNING_CIRCUMFERENCE;
		Robot.driveTrain.setLeftEncoderSetpoint(setpoint);
		Robot.driveTrain.setRightEncoderSetpoint(-setpoint);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double left = Robot.driveTrain.getLeftPIDOutput();
		double right = Robot.driveTrain.getRightPIDOutput();
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
