package org.usfirst.frc.team1732.robot.commands.drivetrain.turn;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.drivetrain.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DitherTurn extends Command {

    private static int id;
    private final double angleSetpoint;
    private final double ditherInterval;
    private final double ditherLength;

    private long startTime;

    public DitherTurn(double angle, double ditherInterval, double ditherLength) {
	requires(Robot.driveTrain);
	angleSetpoint = angle;
	this.ditherInterval = ditherInterval;
	this.ditherLength = ditherLength;
	setTimeout(10);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
	System.out.println("Start DitherTurn" + id);
	startTime = System.currentTimeMillis();
	driveTrain.resetEncoders();
	double setpoint = angleSetpoint / 360.0 * DriveTrain.TURNING_CIRCUMFERENCE;

	driveTrain.mainController.setSetpoint(setpoint, -setpoint);
	driveTrain.mainController.setPIDValues(driveTrain.turningPID);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
	double leftError = driveTrain.mainController.left.getError();
	double rightError = driveTrain.mainController.right.getError();
	double leftRightAdjustment = (leftError + rightError) * DriveTrain.ERROR_DIFFERENCE_SCALAR;

	double left = driveTrain.mainController.left.getOutput();
	double right = driveTrain.mainController.right.getOutput();

	if (System.currentTimeMillis() - startTime > ditherInterval) {
	    if (System.currentTimeMillis() - startTime - ditherInterval < ditherLength) {// &&
											 // Math.abs(output)
											 // <.35)
											 // {
		left = Math.copySign(.35, left);
		right = Math.copySign(.35, right);
	    } else {
		startTime = System.currentTimeMillis();
	    }
	} else {
	    left = 0;
	    right = 0;
	}

	left = left + leftRightAdjustment;
	right = right + leftRightAdjustment;
	driveTrain.driveRawAbsoluteLimit(left, right, .178, 1);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	return driveTrain.mainController.areBothOnTarget();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	System.out.println("End DitherTurn" + id);
	id++;
	driveTrain.driveRaw(0, 0);
	driveTrain.mainController.resetPIDValues();
    }

}
