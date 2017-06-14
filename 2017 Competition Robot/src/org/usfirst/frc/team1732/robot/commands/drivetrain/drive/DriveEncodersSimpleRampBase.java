package org.usfirst.frc.team1732.robot.commands.drivetrain.drive;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.subsystems.drivetrain.DriveTrain;
import org.usfirst.frc.team1732.robot.subsystems.drivetrain.PIDData;

import edu.wpi.first.wpilibj.command.Command;

public class DriveEncodersSimpleRampBase extends Command {

    private static int id = 0;

    public DriveEncodersSimpleRampBase(DoubleSupplier left, DoubleSupplier right) {
	this(left, right, DEFAULT_STOP_RAMP_PERCENTAGE, DEFAULT_STOP_FLAT_PERCENTAGE);
    }

    public DriveEncodersSimpleRampBase(DoubleSupplier left, DoubleSupplier right, double stopRampPercent,
	    double stopFlatPercent) {
	requires(driveTrain);
	this.leftSupplier = left;
	this.rightSupplier = right;
	if (stopFlatPercent < stopRampPercent)
	    stopFlatPercent = stopRampPercent;
	this.stopRampPercentage = stopRampPercent;
	this.stopFlatPercentage = stopFlatPercent;
	setTimeout(5);
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
    private double prevOutput = 0;
    private static final double RAMP_RATE = 0.025; // max delta % Volt
						   // per 20 ms

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
	System.out.println("Start DriveEncodersSimpleRampBase " + id);
	// TODO: might need to use a different set of PID for ramping puposes
	// driveTrain.setEncoderPIDS(0.125, 0, 0);
	// driveTrain.setEncoderDeadband(3);
	driveTrain.resetEncoders();

	driveTrain.resetEncoders();

	PIDData newValues = DriveTrain.normalPID.changeDeadband(5);
	driveTrain.mainController.setPIDValues(newValues);

	leftSetpoint = leftSupplier.getAsDouble();
	rightSetpoint = rightSupplier.getAsDouble();

	leftRightSetpointRatio = leftSetpoint / rightSetpoint;
	rightLeftSetpointRatio = rightSetpoint / leftSetpoint;

	driveTrain.mainController.setSetpoint(leftSetpoint, rightSetpoint);
    }

    // Called repeatedly when this Command is scheduled to run

    @Override
    protected void execute() {
	// the error is how far away the robot is from the setpoint
	double leftError = Math.abs(driveTrain.mainController.left.getError());
	double rightError = Math.abs(driveTrain.mainController.right.getError());

	double leftPercent = leftError / Math.abs(leftSetpoint);
	double rightPercent = rightError / Math.abs(rightSetpoint);
	double averagePercent = (leftPercent + rightPercent) / 2.0;

	double percentCompleted = 1.0 - averagePercent;

	// if (percentCompleted < stopRampPercentage) {
	// // if we have completed 20%, and will stop at 30%, then do 2/3 power
	// // TODO: should this division below be stopRamp or stopFlat? both
	// // might be valid ways
	// double leftOutput = driveTrain.getLeftPIDOutput();
	// double rightOutput = driveTrain.getRightPIDOutput();
	//
	// double output = (leftOutput + rightOutput) / 2.0;
	//
	// if (Math.abs(prevOutput - output) > RAMP_RATE) {
	// output = RAMP_RATE * Math.signum(output) + output;
	// }
	//
	// // double percentDifference = leftPercent - rightPercent;
	// // double percentAdjustement = 2;
	// //
	// // leftOutput = leftOutput - (percentDifference *
	// // percentAdjustement) * leftRightSetpointRatio;
	// // rightOutput = rightOutput + (percentDifference *
	// // percentAdjustement) * rightLeftSetpointRatio;
	//
	// leftOutput = output * leftRightSetpointRatio;
	// rightOutput = output * rightLeftSetpointRatio;
	//
	// double max = Math.max(Math.abs(leftOutput), Math.abs(rightOutput));
	//
	// if (max > 1) {
	// leftOutput = leftOutput / max;
	// rightOutput = rightOutput / max;
	// }
	//
	// prevOutput = output;
	//
	// driveTrain.driveRaw(leftOutput, rightOutput);
	// } else if (percentCompleted >= stopRampPercentage && percentCompleted
	// <= stopFlatPercentage) {
	// // move at previous set speed until STOP_FLAT_PERCENTAGE is reached
	// } else { // finish drive normally
	double leftOutput = driveTrain.mainController.left.getOutput();
	double rightOutput = driveTrain.mainController.right.getOutput();

	double output = (leftOutput + rightOutput) / 2.0;

	// leftOutput = leftOutput + driveTrain.getLeftRightAdjustment();
	// rightOutput = rightOutput - driveTrain.getLeftRightAdjustment();

	// leftOutput *= leftRightSetpointRatio;
	// rightOutput *= rightLeftSetpointRatio;

	leftOutput = output * leftRightSetpointRatio;
	rightOutput = output * rightLeftSetpointRatio;

	double max = Math.max(Math.abs(leftOutput), Math.abs(rightOutput));

	double maxValue = 0.85;

	if (max > maxValue) {
	    leftOutput = (leftOutput / max) * maxValue;
	    rightOutput = (rightOutput / max) * maxValue;
	}

	prevOutput = output;

	driveTrain.driveRaw(leftOutput, rightOutput);
	// }

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	boolean leftOvershoot = Math.abs(driveTrain.leftEncoder.getDistance()) > Math.abs(leftSetpoint);
	boolean rightOvershoot = Math.abs(driveTrain.rightEncoder.getDistance()) > Math.abs(rightSetpoint);
	return driveTrain.mainController.areBothOnTarget() || (leftOvershoot || rightOvershoot);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	System.out.println("End DriveEncodersSimpleRampBase" + id);
	id++;
	driveTrain.mainController.resetPIDValues();
	driveTrain.driveRaw(0, 0);
    }
}
