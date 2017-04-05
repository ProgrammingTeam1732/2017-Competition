package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear;

import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.base.ScoreSideGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreSideGearLeft extends CommandGroup {

	public ScoreSideGearLeft() {
		addSequential(new ScoreSideGear(true));
	}

}
