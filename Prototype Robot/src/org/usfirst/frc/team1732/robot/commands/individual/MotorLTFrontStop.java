package org.usfirst.frc.team1732.robot.commands.individual;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class MotorLTFrontStop extends InstantCommand {

	public MotorLTFrontStop() {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.driveTrain.runMotorLtFront(0);
	}

}
