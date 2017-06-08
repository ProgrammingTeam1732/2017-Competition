package org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoreballsthensidegear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreBallsThenSideGearVision extends CommandGroup {

	public ScoreBallsThenSideGearVision() {
		addSequential(new ScoreBallsThenSideGear(true));
	}
}
