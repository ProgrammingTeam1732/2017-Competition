package org.usfirst.frc.team1732.robot.commands.BallIntake;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class BallIntakeSetUp extends InstantCommand {

    public BallIntakeSetUp() {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.ballIntake);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.ballIntake.setUp();
    }

}
