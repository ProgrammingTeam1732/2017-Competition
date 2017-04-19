package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveEncodersWithRamping extends Command {

    public DriveEncodersWithRamping(DoubleSupplier distance) {
	this(distance, DEFAULT_STOP_RAMP_PERCENTAGE, DEFAULT_STOP_FLAT_PERCENTAGE);
    }

    public DriveEncodersWithRamping(double distance) {
	this(distance, DEFAULT_STOP_RAMP_PERCENTAGE, DEFAULT_STOP_FLAT_PERCENTAGE);
    }

    public DriveEncodersWithRamping(DoubleSupplier distance, double stopRampPercent, double stopFlatPercent) {
	this.distanceSupplier = distance;
	if (stopFlatPercent < stopRampPercent)
	    stopFlatPercent = stopRampPercent;
	this.stopRampPercentage = stopRampPercent;
	this.stopFlatPercentage = stopFlatPercent;
    }

    public DriveEncodersWithRamping(double distance, double stopRampPercent, double stopFlatPercent) {
	this(() -> distance, stopRampPercent, stopFlatPercent);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
	// TODO: might need to use a different set of PID for ramping puposes
	// Robot.driveTrain.setEncoderPIDS(0.125, 0, 0);
	// Robot.driveTrain.setEncoderDeadband(3);
	Robot.driveTrain.resetEncoders();

	driveTrain.resetEncoders();
	driveTrain.resetEncoderPID();
	driveTrain.resetEncoderDeadband();
	setpoint = distanceSupplier.getAsDouble();

	driveTrain.setEncoderSetpoint(setpoint);
	targetMotorOutput = Math.abs(setpoint) * stopFlatPercentage * DriveTrain.encoderP;
    }

    private final DoubleSupplier distanceSupplier;
    private double setpoint;
    private double targetMotorOutput;
    private final double stopRampPercentage;
    private final double stopFlatPercentage;
    private static final double DEFAULT_STOP_RAMP_PERCENTAGE = 0.33;
    private static final double DEFAULT_STOP_FLAT_PERCENTAGE = 0.33;
    // stop ramping after completed 33% of the turn
    private static final double MINIMUM_OUTPUT = 0.2;

    // Called repeatedly when this Command is scheduled to run

    @Override
    protected void execute() {
	// the error is how far away the robot is from the setpoint
	double averageError = (Math.abs(driveTrain.getLeftPIDError()) + Math.abs(driveTrain.getRightPIDError())) / 2.0;
	double percentCompleted = 1.0 - (averageError / Math.abs(setpoint));

	if (percentCompleted < stopRampPercentage) {
	    // if we have completed 20%, and will stop at 30%, then do 2/3 power
	    // TODO: should this division below be stopRamp or stopFlat? both
	    // might be valid ways
	    double percentOfTargetOutput = percentCompleted / stopRampPercentage;
	    double output = targetMotorOutput * percentOfTargetOutput;
	    if (output < MINIMUM_OUTPUT) {
		output = MINIMUM_OUTPUT; // minimum output needed to move
		// hopefully this doesn't really ever happen because then the
		// robot wouldn't be able to complete the drive anyways
		// using just the pid
	    }
	    output = Math.copySign(output, driveTrain.getLeftPIDOutput());
	    double leftOutput = output;
	    double rightOutput = output;
	    leftOutput = leftOutput + driveTrain.getLeftRightAdjustment();
	    rightOutput = rightOutput - driveTrain.getLeftRightAdjustment();

	    double max = Math.max(Math.abs(leftOutput), Math.abs(rightOutput));

	    if (max > 1) {
		leftOutput = leftOutput / max;
		rightOutput = rightOutput / max;
	    }

	    driveTrain.driveRaw(leftOutput, rightOutput);
	} else if (percentCompleted >= stopRampPercentage && percentCompleted <= stopFlatPercentage) {
	    // move at previous set speed until STOP_FLAT_PERCENTAGE is reached
	} else { // finish drive normally
	    double leftOutput = driveTrain.getLeftPIDOutput();
	    double rightOutput = driveTrain.getRightPIDOutput();

	    leftOutput = leftOutput + driveTrain.getLeftRightAdjustment();
	    rightOutput = rightOutput - driveTrain.getLeftRightAdjustment();

	    double max = Math.max(Math.abs(leftOutput), Math.abs(rightOutput));

	    if (max > 1) {
		leftOutput = leftOutput / max;
		rightOutput = rightOutput / max;
	    }

	    driveTrain.driveRaw(leftOutput, rightOutput);
	}

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	return driveTrain.encodersOnTarget();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	driveTrain.driveRaw(0, 0);
	driveTrain.resetEncoderPIDValues();
	driveTrain.resetEncoderDeadband();
    }
}
