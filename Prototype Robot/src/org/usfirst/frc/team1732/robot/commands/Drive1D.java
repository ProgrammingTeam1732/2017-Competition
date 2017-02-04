package org.usfirst.frc.team1732.robot.commands;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;
import static org.usfirst.frc.team1732.robot.Robot.oi;

import edu.wpi.first.wpilibj.command.Command;
/**
 *
 */
public class Drive1D extends Command {
	private boolean direction; //Forward (false) or backwards(true)
	private double SPEED;

    public Drive1D(double sec, boolean direction) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(driveTrain);
    	setTimeout(sec);
    	if(direction)
    		SPEED = .5;
    	else
    		SPEED = -.5;
    }

    // Called just before this Command runs the first time
    protected void initialize() { }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	driveTrain.tankDrive(SPEED, SPEED);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    	driveTrain.tankDrive(0, 0);
    }

    protected void interrupted() {
    	end();
    }
}
