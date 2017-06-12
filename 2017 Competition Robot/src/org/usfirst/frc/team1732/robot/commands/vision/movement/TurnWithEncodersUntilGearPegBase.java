package org.usfirst.frc.team1732.robot.commands.vision.movement;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;
import static org.usfirst.frc.team1732.robot.Robot.visionMain;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class TurnWithEncodersUntilGearPegBase extends Command {

    private final DoubleSupplier angle;
    private boolean foundOnce = false;

    public TurnWithEncodersUntilGearPegBase(DoubleSupplier angle) {
	this.angle = angle;
	requires(Robot.driveTrain);
    }

    public TurnWithEncodersUntilGearPegBase(double angle) {
	this(() -> angle);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
	System.out.println("Starting turn with encoders");
	// Robot.driveTrain.setEncoderPIDS(0.125, 0, 0);
	// Robot.driveTrain.setEncoderDeadband(3);
	Robot.driveTrain.resetEncoders();
	previousAngle = angle.getAsDouble();
	double setpoint = previousAngle / 360.0 * DriveTrain.TURNING_CIRCUMFERENCE;
	Robot.driveTrain.setLeftEncoderSetpoint(setpoint);
	Robot.driveTrain.setRightEncoderSetpoint(-setpoint);
	Robot.driveTrain.setEncoderToTurningPID();
	Robot.driveTrain.setEncoderDeadband(DriveTrain.ENCODER_TURNING_DEADBAND_INCHES);
	// Robot.driveTrain.driveRaw(0, 0);
	// Robot.driveTrain.setEncoderPIDS(0.125, 0, 0);
	// Robot.driveTrain.setEncoderDeadband(3);
	// Robot.driveTrain.resetEncoders();
	// double setpoint = angle.getAsDouble() / 360.0 *
	// Robot.driveTrain.TURNING_CIRCUMFERENCE;
	// Robot.driveTrain.setLeftEncoderSetpoint(setpoint);
	// Robot.driveTrain.setRightEncoderSetpoint(-setpoint);
	Robot.pixyCamera.turnOnLights();
	this.setTimeout(5);
    }

    private double previousAngle;

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
	visionMain.run();
	if (visionMain.canSeeGearPeg()) {
	    foundOnce = true;
	    Robot.driveTrain.resetEncoders();
	    double angle = visionMain.getAngleToGearPeg();
	    if (angle != previousAngle) {
		previousAngle = angle;
		double setpoint = angle / 360.0 * DriveTrain.TURNING_CIRCUMFERENCE;
		Robot.driveTrain.setLeftEncoderSetpoint(setpoint);
		Robot.driveTrain.setRightEncoderSetpoint(-setpoint);
	    }
	}
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

	// double left = Robot.driveTrain.getLeftPIDOutput();
	// double right = Robot.driveTrain.getRightPIDOutput();
	// Robot.driveTrain.driveRaw(left, right);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	return (foundOnce && visionMain.isGearPIDOnTarget()) || driveTrain.encodersOnTarget();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	Robot.driveTrain.driveRaw(0, 0);
	Robot.driveTrain.resetEncoderPIDValues();
	Robot.driveTrain.resetEncoderDeadband();
    }
}
