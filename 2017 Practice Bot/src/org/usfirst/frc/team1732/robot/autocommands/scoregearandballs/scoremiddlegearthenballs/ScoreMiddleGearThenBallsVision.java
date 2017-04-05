package org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoremiddlegearthenballs;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreMiddleGearThenBallsVision extends CommandGroup {

	public ScoreMiddleGearThenBallsVision() {
		addSequential(new ScoreMiddleGearThenBalls(true));
	}

}
