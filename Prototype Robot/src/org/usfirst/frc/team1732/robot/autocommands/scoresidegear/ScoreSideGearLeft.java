package org.usfirst.frc.team1732.robot.autocommands.scoresidegear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreSideGearLeft extends CommandGroup {

	public ScoreSideGearLeft() {
		addSequential(new ScoreSideGear(() -> true));
	}

}
