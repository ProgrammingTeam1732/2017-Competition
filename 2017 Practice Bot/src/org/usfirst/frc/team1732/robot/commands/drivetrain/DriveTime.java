package org.usfirst.frc.team1732.robot.commands.drivetrain;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

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
	System.out.println("start drive time");
	driveTrain.driveRaw(left, right);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	return isTimedOut();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	System.out.println("end drive time");
	driveTrain.driveRaw(0, 0);
    }

}
