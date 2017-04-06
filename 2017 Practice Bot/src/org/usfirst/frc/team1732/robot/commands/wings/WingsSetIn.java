package org.usfirst.frc.team1732.robot.commands.wings;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class WingsSetIn extends InstantCommand {

	public WingsSetIn() {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.wings);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.wings.setIn();
	}

}
