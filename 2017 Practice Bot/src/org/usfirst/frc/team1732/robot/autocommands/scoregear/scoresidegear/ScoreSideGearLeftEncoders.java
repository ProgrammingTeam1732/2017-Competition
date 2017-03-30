package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear;

import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreSideGearLeftEncoders extends CommandGroup {

	public ScoreSideGearLeftEncoders() {
		addSequential(new InitGearIntake());

		// get into position to score gear
		addSequential(new ScoreSideGearPart1Left());

		// Drive, place, drive back
		addSequential(new DriveEncoders(50, -40));

	}
}
