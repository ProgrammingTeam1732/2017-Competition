package org.usfirst.frc.team1732.robot.commands.feeder;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class FeederSetIn extends InstantCommand {

	public FeederSetIn() {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.feeder);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.feeder.setIn();
	}

}
