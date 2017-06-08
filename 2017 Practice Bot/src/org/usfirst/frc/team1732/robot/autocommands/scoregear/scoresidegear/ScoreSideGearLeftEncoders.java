package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear;

import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.base.ScoreSideGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreSideGearLeftEncoders extends CommandGroup {

	public ScoreSideGearLeftEncoders() {
		addSequential(new ScoreSideGear(false, true));
	}
}