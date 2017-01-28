package org.usfirst.frc.team1732.robot.commands;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class GearIntakeSetDown extends InstantCommand {

	public GearIntakeSetDown() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		super();
		requires(Robot.gearIntake);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.gearIntake.setDown();
	}
}
