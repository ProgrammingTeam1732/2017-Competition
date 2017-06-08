package org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoresidegearthenballs;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreSideGearThenBallsVision extends CommandGroup {

	public ScoreSideGearThenBallsVision() {
		addSequential(new ScoreSideGearThenBalls(true));
	}
}
