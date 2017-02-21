package org.usfirst.frc.team1732.robot.commands.individual;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MotorLTBackReverse extends Command {

    public MotorLTBackReverse() {
        // Use requires() here to declare subsystem dependencies
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.runMotorLtBack(-1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.runMotorLtBack(-1);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.runMotorLtBack(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}