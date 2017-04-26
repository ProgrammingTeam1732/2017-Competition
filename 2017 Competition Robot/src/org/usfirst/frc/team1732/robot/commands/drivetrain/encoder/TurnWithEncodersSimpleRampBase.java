package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class TurnWithEncodersSimpleRampBase extends Command {

    public TurnWithEncodersSimpleRampBase(DoubleSupplier angle) {
	this(angle, DEFAULT_STOP_RAMP_PERCENTAGE, DEFAULT_STOP_FLAT_PERCENTAGE);
    }

    public TurnWithEncodersSimpleRampBase(double angle) {
	this(angle, DEFAULT_STOP_RAMP_PERCENTAGE, DEFAULT_STOP_FLAT_PERCENTAGE);
    }

    public TurnWithEncodersSimpleRampBase(DoubleSupplier angle, double stopRampPercent, double stopFlatPercent) {
	this.angleSupplier = angle;
	if (stopFlatPercent < stopRampPercent)
	    stopFlatPercent = stopRampPercent;
	this.stopRampPercentage = stopRampPercent;
	this.stopFlatPercentage = stopFlatPercent;
    }

    public TurnWithEncodersSimpleRampBase(double angle, double stopRampPercent, double stopFlatPercent) {
	this(() -> angle, stopRampPercent, stopFlatPercent);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
	System.out.println("starting ramp turn");
	// TODO: might need to use a different set of PID for ramping puposes
	// Robot.driveTrain.setEncoderPIDS(0.125, 0, 0);
	// Robot.driveTrain.setEncoderDeadband(3);
	Robot.driveTrain.resetEncoders();

	angle = angleSupplier.getAsDouble();
	setpoint = angle / 360.0 * DriveTrain.EFFECTIVE_TURNING_CIRCUMFERENCE;

	Robot.driveTrain.setLeftEncoderSetpoint(setpoint);
	Robot.driveTrain.setRightEncoderSetpoint(-setpoint);
	Robot.driveTrain.setEncoderToTurningPID();
	Robot.driveTrain.setEncoderDeadband(DriveTrain.ENCODER_TURNING_DEADBAND_INCHES);
    }

    private final DoubleSupplier angleSupplier;
    private double angle;
    private double setpoint;
    private double prevLeft = 0;
    private double prevRight = 0;
    private static final double RAMP_RATE = 0.035; // max delta % Volt
						   // per 20 ms
    private final double stopRampPercentage;
    private final double stopFlatPercentage;
    private static final double DEFAULT_STOP_RAMP_PERCENTAGE = 0.33;
    private static final double DEFAULT_STOP_FLAT_PERCENTAGE = 0.66;
    // stop ramping after completed 33% of the turn

    // Called repeatedly when this Command is scheduled to run

    @Override
    protected void execute() {
	// the error is how far away the robot is from the setpoint
	double averageError = (Math.abs(Robot.driveTrain.getLeftPIDError())
		+ Math.abs(Robot.driveTrain.getRightPIDError())) / 2.0;
	double percentCompleted = 1.0 - (averageError / Math.abs(setpoint));
	// System.out.println("% = " + percentCompleted);
	if (percentCompleted < stopRampPercentage) {

	    double left = Robot.driveTrain.getLeftPIDOutput();
	    double right = Robot.driveTrain.getRightPIDOutput();

	    if (Math.abs(prevLeft - left) > RAMP_RATE) {
		left = RAMP_RATE * Math.signum(left) + prevLeft;
	    }
	    if (Math.abs(prevRight - right) > RAMP_RATE) {
		right = RAMP_RATE * Math.signum(right) + prevRight;
	    }
	    // System.out.println("left: " + left);
	    // System.out.println("right: " + right);
	    double leftError = Robot.driveTrain.getLeftPIDError();
	    double rightError = Robot.driveTrain.getRightPIDError();
	    double leftRightAdjustment = (leftError + rightError) * DriveTrain.errorDifferenceScalar;
	    left = left + leftRightAdjustment;
	    right = right + leftRightAdjustment;
	    double max = Math.max(Math.abs(left), Math.abs(right));
	    if (max > 1) {
		left = left / max;
		right = right / max;
	    }
	    prevLeft = left;
	    prevRight = right;
	    Robot.driveTrain.driveRaw(left, right);
	} else if (percentCompleted >= stopRampPercentage && percentCompleted <= stopFlatPercentage) {
	    // move at previous set speed until STOP_FLAT_PERCENTAGE is reached
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

	    double max = Math.max(Math.abs(left), Math.abs(right));
	    if (max > 1) {
		left = left / max;
		right = right / max;
	    }

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
	System.out.println("ending ramp turn");
	Robot.driveTrain.driveRaw(0, 0);
	Robot.driveTrain.resetEncoderPIDValues();
	Robot.driveTrain.resetEncoderDeadband();
    }
}
