package org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoreballsthensidegear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreBallsThenSideGearEncoders extends CommandGroup {

	public ScoreBallsThenSideGearEncoders() {
		addSequential(new ScoreBallsThenSideGear(false));
	}
}
