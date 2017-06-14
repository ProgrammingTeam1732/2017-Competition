package org.usfirst.frc.team1732.robot.commands.drivetrain.drive;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveUntilEncoders extends Command {

    private static int id = 0;

    private final double leftSetpoint;
    private final double rightSetpoint;
    private final double left;
    private final double right;
    private boolean stop;

    public DriveUntilEncoders(double leftSetpoint, double rightSetpoint, double leftSpeed, double rightSpeed,
	    boolean stop) {
	requires(driveTrain);
	this.leftSetpoint = leftSetpoint;
	this.rightSetpoint = rightSetpoint;
	left = leftSpeed;
	right = rightSpeed;
	this.stop = stop;
    }

    public DriveUntilEncoders(double distanceInches, double leftSpeed, double rightSpeed, boolean stop) {
	this(distanceInches, distanceInches, leftSpeed, rightSpeed, stop);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
	System.out.println("Start DriveUntilEncoders " + id);
	driveTrain.resetEncoders();
	driveTrain.mainController.setSetpoint(leftSetpoint, rightSetpoint);
	driveTrain.driveRaw(left, right);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	boolean leftOvershoot = Math.abs(driveTrain.leftEncoder.getDistance()) > Math.abs(leftSetpoint);
	boolean rightOvershoot = Math.abs(driveTrain.rightEncoder.getDistance()) > Math.abs(rightSetpoint);
	return driveTrain.mainController.areBothOnTarget() || (leftOvershoot && rightOvershoot);
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	System.out.println("End DriveUntilEncoders " + id);
	id++;
	if (stop)
	    driveTrain.driveRaw(0, 0);
    }
}
