package org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.manual;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class FlywheelStop extends InstantCommand {

	public FlywheelStop() {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.flywheel);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.flywheel.setSpeed(0);
		;
	}

}
