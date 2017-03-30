package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear;

import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.EncoderPlaceGear;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreSideGearRightEncoders extends CommandGroup {

	public ScoreSideGearRightEncoders() {
		addSequential(new InitGearIntake());

		// get into position to score gear
		addSequential(new ScoreSideGearPart1Right());

		// Drive and place
		addSequential(new EncoderPlaceGear(45));
	}
}
