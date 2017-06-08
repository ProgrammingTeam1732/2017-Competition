package org.usfirst.frc.team1732.robot.commands.ballsystem.feeder;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class FeederSetSpeed extends InstantCommand {

	private final double speed;

	public FeederSetSpeed(double speed) {
		requires(Robot.feeder);
		this.speed = speed;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.feeder.setSpeed(speed);
	}
}
