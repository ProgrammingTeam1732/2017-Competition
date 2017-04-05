package org.usfirst.frc.team1732.robot.commands.drivetrain;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class SetMotorSpeed extends InstantCommand {

	private final double	left;
	private final double	right;

	public SetMotorSpeed(double leftspeed, double rightspeed) {
		super();
		requires(Robot.driveTrain);
		left = leftspeed;
		right = rightspeed;
	}

	public SetMotorSpeed(double speed) {
		this(speed, speed);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		Robot.driveTrain.driveRaw(left, right);
	}

}
