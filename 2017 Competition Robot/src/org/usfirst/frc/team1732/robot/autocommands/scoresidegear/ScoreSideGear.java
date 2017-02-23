package org.usfirst.frc.team1732.robot.autocommands.scoresidegear;

import java.util.function.BooleanSupplier;

import org.usfirst.frc.team1732.robot.autocommands.visionplacegear.VisionPlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

class ScoreSideGear extends CommandGroup {

	public ScoreSideGear(BooleanSupplier isLeft) {
		// get into position to score gear
		addSequential(new ScoreSideGearPart1(isLeft));

		// score gear, drive back 25 inches
		addSequential(new VisionPlaceGear(-25));
	}
}
