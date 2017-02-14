package org.usfirst.frc.team1732.robot.autocommands;

import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreGearAnd10Balls extends CommandGroup {

	public ScoreGearAnd10Balls() {
		// addSequential(new SetDriveTrainBrakeMode(true));
		addSequential(new ScoreSideGearLeft());
		addSequential(new TurnWithGyro(165));
		// addSequential(new SetDriveTrainBrakeMode(false));
		// addSequential(new DriveUntilEncoders(70, 1, 1));
		addSequential(new DriveEncoders(90));
		// addSequential(new SetDriveTrainBrakeMode(true));
	}
}
