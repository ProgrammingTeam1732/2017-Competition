package org.usfirst.frc.team1732.robot.commands;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * Timeout is the time to wait in seconds
 */
public class Wait extends TimedCommand {

	public Wait(double timeout) {
		super(timeout);
	}
}