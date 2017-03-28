package org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class EnableFlywheel extends InstantCommand {

	public EnableFlywheel() {
		super();
		requires(Robot.flywheel);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.flywheel.enableAutoControl();
	}

}
