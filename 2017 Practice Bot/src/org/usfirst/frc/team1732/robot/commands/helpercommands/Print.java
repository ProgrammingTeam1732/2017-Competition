package org.usfirst.frc.team1732.robot.commands.helpercommands;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class Print extends InstantCommand {

	public Print(String toPrint) {
		super();
		System.out.println(toPrint);
	}

}
