package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveEncodersStraightRamp extends Command {

    public DriveEncodersStraightRamp(DoubleSupplier left, DoubleSupplier right) {
	this(left, right, DEFAULT_STOP_RAMP_PERCENTAGE, DEFAULT_STOP_FLAT_PERCENTAGE);
    }

    public DriveEncodersStraightRamp(DoubleSupplier left, DoubleSupplier right, double stopRampPercent,
	    double stopFlatPercent) {
	this.leftSupplier = left;
	this.rightSupplier = right;
	if (stopFlatPercent < stopRampPercent)
	    stopFlatPercent = stopRampPercent;
	this.stopRampPercentage = stopRampPercent;
	this.stopFlatPercentage = stopFlatPercent;
	setTimeout(4);
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

	leftSetpoint = ((int) (leftSupplier.getAsDouble() * 1000)) / 1000.0;
	rightSetpoint = ((int) (rightSupplier.getAsDouble() * 1000)) / 1000.0;
	System.out.println(leftSetpoint);
	System.out.println(rightSetpoint);
	System.out.println(leftSetpoint == rightSetpoint);
	driveTrain.setLeftEncoderSetpoint(leftSetpoint);
	driveTrain.setRightEncoderSetpoint(rightSetpoint);

	leftRightSetpointRatio = leftSetpoint / rightSetpoint;
	rightLeftSetpointRatio = rightSetpoint / leftSetpoint;

    }

    private final DoubleSupplier leftSupplier;
    private final DoubleSupplier rightSupplier;
    private double leftSetpoint;
    private double rightSetpoint;
    private double leftRightSetpointRatio;
    private double rightLeftSetpointRatio;
    private final double stopRampPercentage;
    private final double stopFlatPercentage;
    private static final double DEFAULT_STOP_RAMP_PERCENTAGE = 0.6;
    private static final double DEFAULT_STOP_FLAT_PERCENTAGE = 0.6;
    // stop ramping after completed 33% of the turn
    private double prevLeft = 0;
    private double prevRight = 0;
    private double prevOutput = 0;
    private static final double RAMP_RATE = 0.03; // max delta % Volt
						  // per 20 ms
    // Called repeatedly when this Command is scheduled to run

    @Override
    protected void execute() {
	// the error is how far away the robot is from the setpoint
	double leftError = Math.abs(driveTrain.getLeftPIDError());
	double rightError = Math.abs(driveTrain.getRightPIDError());

	double leftPercent = leftError / Math.abs(leftSetpoint);
	double rightPercent = rightError / Math.abs(rightSetpoint);
	double averagePercent = (leftPercent + rightPercent) / 2.0;

	double percentCompleted = 1.0 - averagePercent;

	if (percentCompleted < stopRampPercentage) {
	    // if we have completed 20%, and will stop at 30%, then do 2/3 power
	    // TODO: should this division below be stopRamp or stopFlat? both
	    // might be valid ways
	    double leftOutput = driveTrain.getLeftPIDOutput();
	    double rightOutput = driveTrain.getRightPIDOutput();
	    double output = (leftOutput + rightOutput) / 2.0;

	    // if (Math.abs(prevLeft - leftOutput) > RAMP_RATE) {
	    // leftOutput = RAMP_RATE * Math.signum(leftOutput) + prevLeft;
	    // }
	    //
	    // if (Math.abs(prevRight - rightOutput) > RAMP_RATE) {
	    // rightOutput = RAMP_RATE * Math.signum(rightOutput) + prevRight;
	    // }

	    if (Math.abs(prevOutput - output) > RAMP_RATE) {
		output = RAMP_RATE * Math.signum(output) + prevOutput;
	    }

	    double adjust = driveTrain.getLeftRightAdjustment();

	    leftOutput = output + adjust * leftRightSetpointRatio;
	    rightOutput = output - adjust * rightLeftSetpointRatio;

	    double max = Math.max(Math.abs(leftOutput), Math.abs(rightOutput));

	    if (max > 1) {
		leftOutput = leftOutput / max;
		rightOutput = rightOutput / max;
	    }

	    prevLeft = leftOutput;
	    prevRight = rightOutput;
	    prevOutput = output;

	    driveTrain.driveRaw(leftOutput, rightOutput);
	} else if (percentCompleted >= stopRampPercentage && percentCompleted <= stopFlatPercentage) {
	    // move at previous set speed until STOP_FLAT_PERCENTAGE is reached
	} else { // finish drive normally
	    double leftOutput = driveTrain.getLeftPIDOutput();
	    double rightOutput = driveTrain.getRightPIDOutput();
	    double output = (leftOutput + rightOutput) / 2.0;

	    double adjust = driveTrain.getLeftRightAdjustment();

	    leftOutput = output + adjust * leftRightSetpointRatio;
	    rightOutput = output - adjust * rightLeftSetpointRatio;

	    double max = Math.max(Math.abs(leftOutput), Math.abs(rightOutput));

	    if (max > 1) {
		leftOutput = leftOutput / max;
		rightOutput = rightOutput / max;
	    }

	    prevLeft = leftOutput;
	    prevRight = rightOutput;
	    prevOutput = output;

	    driveTrain.driveRaw(leftOutput, rightOutput);
	}

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	boolean leftOvershoot = Math.abs(Robot.driveTrain.getLeftDistance()) > Math.abs(leftSetpoint);
	boolean rightOvershoot = Math.abs(Robot.driveTrain.getRightDistance()) > Math.abs(rightSetpoint);
	return Robot.driveTrain.encodersOnTarget() || (leftOvershoot || rightOvershoot);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	driveTrain.driveRaw(0, 0);
	driveTrain.resetEncoderPIDValues();
	driveTrain.resetEncoderDeadband();
    }
}
