package org.usfirst.frc.team1732.robot.commands.climber;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ClimberSetDown extends InstantCommand {

    public ClimberSetDown() {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.climber);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.climber.setDown();
    }

}
