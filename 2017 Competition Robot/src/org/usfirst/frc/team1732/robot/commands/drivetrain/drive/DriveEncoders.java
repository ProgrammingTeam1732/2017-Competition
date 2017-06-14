package org.usfirst.frc.team1732.robot.commands.drivetrain.drive;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.command.Command;

public class DriveEncoders extends Command {

    private static int id = 0;

    private final DoubleSupplier leftDistance;
    private final DoubleSupplier rightDistance;

    private double left = 0;
    private double right = 0;

    public DriveEncoders(DoubleSupplier distanceInches) {
	this(distanceInches, distanceInches);
    }

    public DriveEncoders(DoubleSupplier leftInches, DoubleSupplier rightInches) {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(driveTrain);
	leftDistance = leftInches;
	rightDistance = rightInches;
    }

    public DriveEncoders(double distanceInches) {
	this(distanceInches, distanceInches);
    }

    public DriveEncoders(double leftInches, double rightInches) {
	this(() -> leftInches, () -> rightInches);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
	System.out.println("Start DriveEncoders " + id);
	left = ((int) (leftDistance.getAsDouble() * 1000)) / 1000.0;
	right = ((int) (rightDistance.getAsDouble() * 1000)) / 1000.0;
	driveTrain.resetEncoders();
	driveTrain.mainController.setSetpoint(left, right);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
	double leftOutput = driveTrain.mainController.left.getOutput();
	double rightOutput = driveTrain.mainController.right.getOutput();

	if (leftDistance == rightDistance) {
	    double adjust = driveTrain.getLeftRightAdjustment(driveTrain.mainController.left, driveTrain.mainController.right);
	    leftOutput = leftOutput + adjust;
	    rightOutput = rightOutput - adjust;
	}

	double max = Math.max(Math.abs(leftOutput), Math.abs(rightOutput));

	if (max > 1) {
	    leftOutput = leftOutput / max;
	    rightOutput = rightOutput / max;
	}

	driveTrain.driveRaw(leftOutput, rightOutput);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	return driveTrain.mainController.areBothOnTarget();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	System.out.println("End DriveEncoders " + id);
	driveTrain.driveRaw(0, 0);
	id++;
    }

}
