package org.usfirst.frc.team1732.robot.commands.flywheel;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class DisableFlywheel extends InstantCommand {

	public DisableFlywheel() {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.flywheel);
	}

	// Called once when the command executes
	protected void initialize() {
		Robot.flywheel.disableAutoControl();
	}

}
