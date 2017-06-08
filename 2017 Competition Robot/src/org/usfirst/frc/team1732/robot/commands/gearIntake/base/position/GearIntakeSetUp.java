package org.usfirst.frc.team1732.robot.commands.gearIntake.base.position;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class GearIntakeSetUp extends InstantCommand {

	public GearIntakeSetUp() {
		super();
		requires(Robot.gearIntake);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if (Robot.gearIntake.isStopperIn() && Robot.gearIntake.isDown()) {
			Robot.gearIntake.setStopperOut();
		}
		Robot.gearIntake.setUp();
	}

}
