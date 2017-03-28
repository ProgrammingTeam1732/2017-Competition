package org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorrightback;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class MotorRTBackStop extends InstantCommand {

	public MotorRTBackStop() {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.driveTrain.runMotorRtBack(0);
	}

}
