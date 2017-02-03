package org.usfirst.frc.team1732.robot.commands.gearIntake;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 *
 */
public class IntakeOutDownTimer extends Command {

    public IntakeOutDownTimer() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.gearIntake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.gearIntake.setForward();
    	setTimeout(.5);
    }
    
    protected void end() {
    	Robot.gearIntake.setStop();
    	Scheduler.getInstance().add(new IntakeOutDownTimer());
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
