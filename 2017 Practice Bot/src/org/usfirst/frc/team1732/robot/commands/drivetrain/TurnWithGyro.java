package org.usfirst.frc.team1732.robot.commands.drivetrain;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnWithGyro extends Command {

	private final double setpoint;

	private final double IZONE = 10;

	public TurnWithGyro(double degreesSetpoint) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
		setpoint = degreesSetpoint;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		// System.out.println("Running turn : " + Robot.isRedAlliance());
		driveTrain.resetGyro();
		// Robot.driveTrain.resetGyroPID();
		// Robot.driveTrain.clearGyroIntgral();
		driveTrain.setGyroSetpoint(setpoint);
		// System.out.println(setpoint);
		driveTrain.setEncoderSetpoint(0);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (Math.abs(driveTrain.getGyroError()) > IZONE) {
			driveTrain.setGyroI(0);
			System.out.println("Outisde gyro izone");
		}
		double output = driveTrain.getGyroPIDOutput();
		double leftError = driveTrain.getLeftPIDError();
		double rightError = driveTrain.getRightPIDError();
		double leftRightAdjustment = (leftError + rightError) * driveTrain.errorDifferenceScalar;
		driveTrain.driveRaw(output - leftRightAdjustment, -(output + leftRightAdjustment));
		System.out.println(output);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.driveTrain.gyroOnTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveTrain.driveRaw(0, 0);
	}
}
