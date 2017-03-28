package org.usfirst.frc.team1732.robot.commands.helpercommands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command that waits
 */
public class Wait extends Command {

	private final DoubleSupplier timeout;

	/**
	 * Timeout is the time to wait in seconds
	 */
	public Wait(double timeout) {
		this.timeout = () -> timeout;
	}

	public Wait(DoubleSupplier timeout) {
		this.timeout = timeout;
	}

	@Override
	public void initialize() {
		super.setTimeout(timeout.getAsDouble());
	}

	@Override
	protected boolean isFinished() {
		return super.isTimedOut();
	}
}