package org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoremiddlegearthenballs;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreMiddleGearThenBallsEncoders extends CommandGroup {

	public ScoreMiddleGearThenBallsEncoders() {
		addSequential(new ScoreMiddleGearThenBalls(false));
	}

}
