package org.usfirst.frc.team1732.robot.commands;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class IntakeInDownTimer extends Command {

    public IntakeInDownTimer() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gearIntake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.gearIntake.setReverse();
    	setTimeout(.5);
    }
    
    protected void end() {
    	Robot.gearIntake.setStop();
    }
    protected void interrupted() {
    	end();
    }

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

    
}
