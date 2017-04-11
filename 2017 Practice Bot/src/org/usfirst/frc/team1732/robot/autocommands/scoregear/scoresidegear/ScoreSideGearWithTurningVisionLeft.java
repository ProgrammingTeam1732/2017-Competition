package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreSideGearWithTurningVisionLeft extends CommandGroup {

	public ScoreSideGearWithTurningVisionLeft() {
		addSequential(new ScoreSideGearWithTurningVision(true));
	}

}
