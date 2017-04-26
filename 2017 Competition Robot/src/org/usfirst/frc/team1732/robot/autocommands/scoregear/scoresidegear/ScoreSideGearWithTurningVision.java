package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear;

import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.base.ScoreSideGearPart1;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.placegear.EncoderPlaceGear;
import org.usfirst.frc.team1732.robot.commands.vision.lights.TurnLightsOn;
import org.usfirst.frc.team1732.robot.commands.vision.movement.DitherTurnWithVision;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreSideGearWithTurningVision extends CommandGroup {

    public ScoreSideGearWithTurningVision(boolean isLeft) {
	addSequential(new InitGearIntake());
	addSequential(new ScoreSideGearPart1(isLeft));
	addSequential(new TurnLightsOn());
	// addSequential(new Wait(.5));
	addSequential(new DitherTurnWithVision(0));
	addSequential(new EncoderPlaceGear(15, -30));
    }

}
