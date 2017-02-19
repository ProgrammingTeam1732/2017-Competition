package org.usfirst.frc.team1732.robot.commands.gearIntake;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class GearIntakeSetDown extends InstantCommand {

	public GearIntakeSetDown() {
		super();
		requires(Robot.gearIntake);
		requires(Robot.ballIntake);
	}

	@Override
	protected void initialize() {
		if (Robot.ballIntake.isPosistionUp()) {
			Robot.ballIntake.setPosistionDown();
		}
		Robot.gearIntake.setDown();
	}
}
