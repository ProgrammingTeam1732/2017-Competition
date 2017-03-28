package org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FlywheelBangBang extends Command {

	public FlywheelBangBang() {
		requires(Robot.flywheel);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.flywheel.enableAutoControl();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.flywheel.disableAutoControl();
	}

}
