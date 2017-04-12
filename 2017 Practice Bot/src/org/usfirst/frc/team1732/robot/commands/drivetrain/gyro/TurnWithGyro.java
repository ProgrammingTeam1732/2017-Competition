package org.usfirst.frc.team1732.robot.commands.drivetrain.gyro;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnWithGyro extends Command {

	private final double setpoint;

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
		if (Math.abs(driveTrain.getGyroError()) < DriveTrain.GYRO_IZONE) {
			driveTrain.setGyroPIDS(DriveTrain.gyroP, DriveTrain.GYRO_IZONE_I, DriveTrain.gyroD);
		} else {
			driveTrain.resetGyroPIDValues();
		}

		double output = driveTrain.getGyroPIDOutput();

		double leftOutput = output;
		double rightOutput = -output;

		// double leftError = driveTrain.getLeftPIDError();
		// double rightError = driveTrain.getRightPIDError();
		// double leftRightAdjustment = (leftError + rightError) *
		// driveTrain.errorDifferenceScalar;
		// leftOutput = output - leftRightAdjustment;
		// rightOutput = output + leftRightAdjustment;

		driveTrain.driveRaw(leftOutput, rightOutput);
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
		Robot.driveTrain.resetGyroPIDValues();
	}
}
