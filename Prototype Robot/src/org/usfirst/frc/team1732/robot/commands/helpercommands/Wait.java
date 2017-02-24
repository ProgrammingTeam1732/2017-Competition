package org.usfirst.frc.team1732.robot.commands.helpercommands;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * Command that waits
 */
public class Wait extends TimedCommand {

	/**
	 * Timeout is the time to wait in seconds
	 */
	public Wait(double timeout) {
		super(timeout);
	}

	@Override
	public void initialize() {
		System.out.println("Start wait");
	}

	@Override
	public void end() {
		System.out.println("End wait");
	}
}