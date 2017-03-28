package org.usfirst.frc.team1732.robot.commands.gearIntake.base.position;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class GearIntakeSetUpNoCheck extends InstantCommand {

	public GearIntakeSetUpNoCheck() {
		super();
		requires(Robot.gearIntake);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.gearIntake.setUp();
	}

}
