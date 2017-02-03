package org.usfirst.frc.team1732.robot.commands.gearIntake;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class GearIntakeSetUp extends InstantCommand {

	public GearIntakeSetUp() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		super();
		requires(Robot.gearIntake);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.gearIntake.setUp();
	}

}
