package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear;

import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.base.ScoreSideGearPart1;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreSideGearPart1Left extends CommandGroup {

	public ScoreSideGearPart1Left() {
		addSequential(new ScoreSideGearPart1(true));
	}
}
