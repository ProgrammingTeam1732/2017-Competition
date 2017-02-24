package org.usfirst.frc.team1732.robot.autocommands.scoresidegear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreSideGearRight extends CommandGroup {

	public ScoreSideGearRight() {
		addSequential(new ScoreSideGear(() -> false));
	}
}
