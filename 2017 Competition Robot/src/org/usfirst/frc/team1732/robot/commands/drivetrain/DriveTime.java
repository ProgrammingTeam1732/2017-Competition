package org.usfirst.frc.team1732.robot.commands.drivetrain;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveTime extends Command {
    private double left;
    private double right;

    public DriveTime(double sec, double speed) {
	this(sec, speed, speed);
    }

    public DriveTime(double sec, double leftSpeed, double rightSpeed) {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(driveTrain);
	setTimeout(sec);
	left = leftSpeed;
	right = rightSpeed;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
	driveTrain.driveRaw(left, right);
    }

    private double leftMax = 0;
    private double rightMax = 0;

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
	double d;
	if (Math.abs((d = Robot.driveTrain.getLeftVelocity())) > Math.abs(leftMax)) {
	    leftMax = d;
	}
	if (Math.abs((d = Robot.driveTrain.getRightVelocity())) > Math.abs(rightMax)) {
	    rightMax = d;
	}
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	return isTimedOut();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	System.out.println("Max Velocity");
	System.out.println(leftMax);
	System.out.println(rightMax);
	driveTrain.driveRaw(0, 0);
    }

}
