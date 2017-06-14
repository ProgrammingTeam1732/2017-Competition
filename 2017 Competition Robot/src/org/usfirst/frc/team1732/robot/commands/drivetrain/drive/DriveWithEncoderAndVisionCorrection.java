package org.usfirst.frc.team1732.robot.commands.drivetrain.drive;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveWithEncoderAndVisionCorrection extends Command {

    private static int id;

    private DoubleSupplier distance;

    public static final double ANGLE_P = 0.04;

    public DriveWithEncoderAndVisionCorrection(DoubleSupplier distance) {
	requires(Robot.driveTrain);
	requires(Robot.pixyCamera);
	this.distance = distance;
    }

    public DriveWithEncoderAndVisionCorrection(double distance) {
	this(() -> distance);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
	System.out.println("Start DriveWithEncoderAndVisionCorrection " + id);
	Robot.pixyCamera.turnOnLights();
	driveTrain.resetEncoders();
	driveTrain.mainController.setSetpoint(distance.getAsDouble());
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
	double left = driveTrain.mainController.left.getOutput();
	double right = driveTrain.mainController.right.getOutput();
	double output = (left + right) / 2.0;
	Robot.visionMain.run();
	double angle = Robot.visionMain.getAngleToGearPeg();
	double angleAdjust = angle * ANGLE_P;
	double leftOutput = output + angleAdjust;
	double rightOutput = output - angleAdjust;
	driveTrain.driveRaw(leftOutput, rightOutput);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	return driveTrain.mainController.left.onTarget() || driveTrain.mainController.right.onTarget();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	Robot.pixyCamera.turnOffLights();
	System.out.println("End DriveWithEncoderAndVisionCorrection " + id);
	driveTrain.driveRaw(0, 0);
    }

}
