package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class SetEncoderPID extends InstantCommand {

	private final double P;
	private final double I;
	private final double D;

	public SetEncoderPID(double p, double i, double d) {
		super();
		this.P = p;
		this.I = i;
		this.D = d;
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

	// Called once when the command executes
	protected void initialize() {
		driveTrain.setEncoderPIDS(P, I, D);
	}

}
