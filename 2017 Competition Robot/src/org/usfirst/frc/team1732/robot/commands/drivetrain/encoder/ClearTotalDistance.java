package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ClearTotalDistance extends InstantCommand {

	public ClearTotalDistance() {
		super();
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.driveTrain.resetEncoders();
		Robot.driveTrain.clearTotalDistance();
	}

}
