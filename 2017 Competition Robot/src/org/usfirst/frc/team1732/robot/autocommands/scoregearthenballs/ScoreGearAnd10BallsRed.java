package org.usfirst.frc.team1732.robot.autocommands.scoregearthenballs;

import org.usfirst.frc.team1732.robot.autocommands.scoresidegear.ScoreSideGearRight;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveUntilEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;

class ScoreGearAnd10BallsRed extends CommandGroup {
	public ScoreGearAnd10BallsRed() {
		addSequential(new ScoreSideGearRight());
		addSequential(new TurnWithGyro(-146));
		// addSequential(new Wait(0.2));
		addSequential(new DriveUntilEncoders(65, 0.7, 0.7, false));
		addSequential(new DriveEncoders(40));
	}
}
