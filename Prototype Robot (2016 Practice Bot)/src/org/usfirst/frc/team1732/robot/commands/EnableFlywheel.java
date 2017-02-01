package org.usfirst.frc.team1732.robot.commands;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class EnableFlywheel extends InstantCommand {

	public EnableFlywheel() {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.flywheel);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.flywheel.enable();
	}

}
