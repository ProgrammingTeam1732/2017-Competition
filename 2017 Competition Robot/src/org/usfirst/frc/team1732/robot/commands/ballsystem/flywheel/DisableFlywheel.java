package org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class DisableFlywheel extends InstantCommand {

	public DisableFlywheel() {
		super();
		requires(Robot.flywheel);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.flywheel.disableAutoControl();
	}

}
