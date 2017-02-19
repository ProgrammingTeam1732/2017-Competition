package org.usfirst.frc.team1732.robot.autocommands;

import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveUntilEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreGearAnd10BallsBlue extends CommandGroup {

	public ScoreGearAnd10BallsBlue() {
		// addSequential(new Wait(0.1));
		addSequential(new ScoreSideGearLeft());
		addSequential(new TurnWithGyro(120));
		// addSequential(new Wait(0.2));
		addSequential(new DriveUntilEncoders(70, 0.7, 0.7, false));
		addSequential(new DriveEncoders(40));
	}
}