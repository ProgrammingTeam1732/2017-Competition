package org.usfirst.frc.team1732.robot.commands.gearIntake;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class GearIntakeSetOut extends InstantCommand {

	public GearIntakeSetOut() {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.gearIntake);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.gearIntake.setOut();
	}

}
