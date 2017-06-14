package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ResetEncoderPID extends InstantCommand {

	public ResetEncoderPID() {
		super();
	}

	// Called once when the command executes
	protected void initialize() {
		driveTrain.resetEncoderPID();
	}

}
