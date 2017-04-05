package org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoresidegearthenballs;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreSideGearThenBallsEncoders extends CommandGroup {

	public ScoreSideGearThenBallsEncoders() {
		addSequential(new ScoreSideGearThenBalls(false));
	}
}
