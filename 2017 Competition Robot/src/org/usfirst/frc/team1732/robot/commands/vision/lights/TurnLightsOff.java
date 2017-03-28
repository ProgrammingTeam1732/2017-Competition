package org.usfirst.frc.team1732.robot.commands.vision.lights;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class TurnLightsOff extends InstantCommand {

    public TurnLightsOff() {
        //super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.pixyCamera);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.pixyCamera.turnOffLights();
    }

}