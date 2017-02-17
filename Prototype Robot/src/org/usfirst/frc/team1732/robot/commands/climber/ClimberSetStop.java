package org.usfirst.frc.team1732.robot.commands.climber;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ClimberSetStop extends InstantCommand {

	public ClimberSetStop() {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.climber);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.climber.setStop();
	}

}
