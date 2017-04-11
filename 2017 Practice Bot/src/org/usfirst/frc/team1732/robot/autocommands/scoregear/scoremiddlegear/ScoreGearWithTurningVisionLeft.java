package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoremiddlegear;

import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.ScoreSideGearPart1Left;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.EncoderPlaceGear;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.vision.lights.TurnLightsOn;
import org.usfirst.frc.team1732.robot.commands.vision.movement.TurnWithVision;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreGearWithTurningVisionLeft extends CommandGroup {

	public ScoreGearWithTurningVisionLeft() {
		addSequential(new ScoreSideGearPart1Left());
		addSequential(new TurnLightsOn());
		addSequential(new Wait(.5));
		addSequential(new TurnWithVision(0));
		addSequential(new EncoderPlaceGear(15, -30));
	}

}
