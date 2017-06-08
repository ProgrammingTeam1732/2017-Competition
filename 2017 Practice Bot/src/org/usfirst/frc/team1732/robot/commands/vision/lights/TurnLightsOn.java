package org.usfirst.frc.team1732.robot.commands.vision.lights;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class TurnLightsOn extends InstantCommand {

	public TurnLightsOn() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.pixyCamera);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.pixyCamera.turnOnLights();
	}

}