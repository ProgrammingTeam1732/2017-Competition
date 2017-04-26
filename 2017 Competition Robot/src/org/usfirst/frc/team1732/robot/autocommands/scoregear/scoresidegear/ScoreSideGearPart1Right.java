package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear;

import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.base.ScoreSideGearPart1;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreSideGearPart1Right extends CommandGroup {

	public ScoreSideGearPart1Right() {
		addSequential(new ScoreSideGearPart1(false));
	}
}
