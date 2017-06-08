package org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorrightbottom;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class MotorRTBottomStop extends InstantCommand {

	public MotorRTBottomStop() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.driveTrain.runMotorRtBottom(0);
	}

}