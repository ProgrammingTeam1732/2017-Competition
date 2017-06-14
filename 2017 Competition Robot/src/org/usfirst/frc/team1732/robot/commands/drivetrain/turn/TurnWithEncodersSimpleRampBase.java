package org.usfirst.frc.team1732.robot.commands.drivetrain.turn;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.drivetrain.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class TurnWithEncodersSimpleRampBase extends Command {

    private static int id;

    public TurnWithEncodersSimpleRampBase(DoubleSupplier angle) {
	this(angle, DEFAULT_STOP_RAMP_PERCENTAGE, DEFAULT_STOP_FLAT_PERCENTAGE);
    }

    public TurnWithEncodersSimpleRampBase(double angle) {
	this(angle, DEFAULT_STOP_RAMP_PERCENTAGE, DEFAULT_STOP_FLAT_PERCENTAGE);
    }

    public TurnWithEncodersSimpleRampBase(DoubleSupplier angle, double stopRampPercent, double stopFlatPercent) {
	requires(Robot.driveTrain);
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
	System.out.println("Start TurnWithEncodersSimpleRampBase " + id);
	driveTrain.resetEncoders();

	angle = angleSupplier.getAsDouble();
	setpoint = angle / 360.0 * DriveTrain.EFFECTIVE_TURNING_CIRCUMFERENCE;

	driveTrain.mainController.setSetpoint(setpoint, -setpoint);
	driveTrain.mainController.setPIDValues(driveTrain.turningPID);
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
	double averageError = (Math.abs(driveTrain.mainController.left.getError())
		+ Math.abs(driveTrain.mainController.right.getError())) / 2.0;
	double percentCompleted = 1.0 - (averageError / Math.abs(setpoint));
	// System.out.println("% = " + percentCompleted);
	if (percentCompleted < stopRampPercentage) {

	    double left = driveTrain.mainController.left.getOutput();
	    double right = driveTrain.mainController.right.getOutput();

	    if (Math.abs(prevLeft - left) > RAMP_RATE) {
		left = RAMP_RATE * Math.signum(left) + prevLeft;
	    }
	    if (Math.abs(prevRight - right) > RAMP_RATE) {
		right = RAMP_RATE * Math.signum(right) + prevRight;
	    }
	    // System.out.println("left: " + left);
	    // System.out.println("right: " + right);
	    double leftError = driveTrain.mainController.left.getError();
	    double rightError = driveTrain.mainController.right.getError();
	    double leftRightAdjustment = (leftError + rightError) * DriveTrain.ERROR_DIFFERENCE_SCALAR;
	    left = left + leftRightAdjustment;
	    right = right + leftRightAdjustment;
	    double max = Math.max(Math.abs(left), Math.abs(right));
	    if (max > 1) {
		left = left / max;
		right = right / max;
	    }
	    prevLeft = left;
	    prevRight = right;
	    driveTrain.driveRaw(left, right);
	} else if (percentCompleted >= stopRampPercentage && percentCompleted <= stopFlatPercentage) {
	    // move at previous set speed until STOP_FLAT_PERCENTAGE is reached
	} else { // finish turn normally

	    double leftError = driveTrain.mainController.left.getError();
	    double rightError = driveTrain.mainController.right.getError();
	    double leftRightAdjustment = (leftError + rightError) * DriveTrain.ERROR_DIFFERENCE_SCALAR;

	    double left = driveTrain.mainController.left.getOutput();
	    double right = driveTrain.mainController.right.getOutput();

	    left = left + leftRightAdjustment;
	    right = right + leftRightAdjustment;

	    double max = Math.max(Math.abs(left), Math.abs(right));
	    if (max > 1) {
		left = left / max;
		right = right / max;
	    }

	    driveTrain.driveRaw(left, right);
	}

    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	return driveTrain.mainController.areBothOnTarget();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	System.out.println("End TurnWithEncodersSimpleRampBase " + id);
	id++;
	driveTrain.driveRaw(0, 0);
	driveTrain.mainController.resetPIDValues();
    }
}
