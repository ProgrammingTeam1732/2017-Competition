package org.usfirst.frc.team1732.robot.commands.drivetrain.motors.motorleftbottom;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class MotorLTBottomStop extends InstantCommand {

	public MotorLTBottomStop() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.driveTrain.runMotorLtBottom(0);
	}

}