package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class TurnWithEncodersWithRamping extends Command {

    public TurnWithEncodersWithRamping(DoubleSupplier angle) {
	this.angleSupplier = angle;
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

	angle = angleSupplier.getAsDouble();
	setpoint = angle / 360.0 * DriveTrain.TURNING_CIRCUMFERENCE;
	targetMotorOutput = Math.abs(setpoint) * STOP_RAMP_PERCENTAGE * DriveTrain.encoderTurningP;

	Robot.driveTrain.setLeftEncoderSetpoint(setpoint);
	Robot.driveTrain.setRightEncoderSetpoint(-setpoint);
	Robot.driveTrain.setEncoderToTurningPID();
	Robot.driveTrain.setEncoderDeadband(DriveTrain.ENCODER_TURNING_DEADBAND_INCHES);
    }

    private final DoubleSupplier angleSupplier;
    private double angle;
    private double setpoint;
    private double targetMotorOutput;
    private static final double STOP_RAMP_PERCENTAGE = 0.33;
    // stop ramping after completed 33% of the turn
    private static final double MINIMUM_OUTPUT = 0.2;

    // Called repeatedly when this Command is scheduled to run

    @Override
    protected void execute() {
	// the error is how far away the robot is from the setpoint
	double averageError = (Math.abs(Robot.driveTrain.getLeftPIDError())
		+ Math.abs(Robot.driveTrain.getRightPIDError())) / 2.0;
	double percentCompleted = 1.0 - (averageError / Math.abs(setpoint));

	if (percentCompleted < STOP_RAMP_PERCENTAGE) {
	    // if we have completed 20%, and will stop at 30%, then do 2/3 power
	    double percentOfTargetOutput = percentCompleted / STOP_RAMP_PERCENTAGE;
	    double output = targetMotorOutput * percentOfTargetOutput;
	    if (output < MINIMUM_OUTPUT) {
		output = MINIMUM_OUTPUT; // minimum output needed to move
		// hopefully this doesn't really ever happen because then the
		// robot wouldn't be able to complete the turn anyways
		// using just the pid turning
	    }
	    output = Math.copySign(output, Robot.visionMain.getGearPIDOutput());
	    Robot.driveTrain.driveRaw(output, -output);
	} else { // finish turn normally
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
