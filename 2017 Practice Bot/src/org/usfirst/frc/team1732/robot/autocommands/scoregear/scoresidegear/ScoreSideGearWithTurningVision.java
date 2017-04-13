package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear;

import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.placegear.EncoderPlaceGear;
import org.usfirst.frc.team1732.robot.commands.vision.lights.TurnLightsOn;
import org.usfirst.frc.team1732.robot.commands.vision.movement.TurnWithVision;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreSideGearWithTurningVision extends CommandGroup {

	public ScoreSideGearWithTurningVision(boolean isLeft) {
		addSequential(new InitGearIntake());
		if (isLeft) {
			addSequential(new ScoreSideGearPart1Left());
		} else {
			addSequential(new ScoreSideGearPart1Right());
		}
		addSequential(new TurnLightsOn());
		addSequential(new Wait(.5));
		addSequential(new TurnWithVision(0));
		addSequential(new EncoderPlaceGear(15, -30));
	}

}
