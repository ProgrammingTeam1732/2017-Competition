package org.usfirst.frc.team1732.robot.commands.drivetrain.turn;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.subsystems.drivetrain.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class TurnWithEncodersBase extends Command {

    private static int id;
    private final DoubleSupplier angle;

    public TurnWithEncodersBase(DoubleSupplier angle) {
	requires(driveTrain);
	this.angle = angle;
    }

    public TurnWithEncodersBase(double angle) {
	this(() -> angle);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
	System.out.println("Start TurnWithEncodersBase " + id);
	// driveTrain.setEncoderPIDS(0.125, 0, 0);
	// driveTrain.setEncoderDeadband(3);
	driveTrain.resetEncoders();
	double setpoint = angle.getAsDouble() / 360.0 * DriveTrain.TURNING_CIRCUMFERENCE;
	driveTrain.mainController.setSetpoint(setpoint, -setpoint);
	driveTrain.mainController.setPIDValues(driveTrain.turningPID);
	// driveTrain.driveRaw(0, 0);
	// driveTrain.setEncoderPIDS(0.125, 0, 0);
	// driveTrain.setEncoderDeadband(3);
	// driveTrain.resetEncoders();
	// double setpoint = angle.getAsDouble() / 360.0 *
	// driveTrain.TURNING_CIRCUMFERENCE;
	// driveTrain.setLeftEncoderSetpoint(setpoint);
	// driveTrain.setRightEncoderSetpoint(-setpoint);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
	double leftError = driveTrain.mainController.left.getError();
	double rightError = driveTrain.mainController.right.getError();
	double leftRightAdjustment = (leftError + rightError) * DriveTrain.ERROR_DIFFERENCE_SCALAR;

	double left = driveTrain.mainController.left.getOutput();
	double right = driveTrain.mainController.right.getOutput();

	left = left + leftRightAdjustment;
	right = right + leftRightAdjustment;
	driveTrain.driveRaw(left, right);

	// double left = driveTrain.getLeftPIDOutput();
	// double right = driveTrain.getRightPIDOutput();
	// driveTrain.driveRaw(left, right);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	return driveTrain.mainController.areBothOnTarget();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	System.out.println("End TurnWithEncodersBase" + id);
	id++;
	driveTrain.driveRaw(0, 0);
	driveTrain.mainController.resetPIDValues();
    }
}
