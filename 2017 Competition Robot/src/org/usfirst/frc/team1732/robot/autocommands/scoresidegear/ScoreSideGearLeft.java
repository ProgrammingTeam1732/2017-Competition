package org.usfirst.frc.team1732.robot.autocommands.scoresidegear;

import org.usfirst.frc.team1732.robot.autocommands.visionplacegear.VisionPlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreSideGearLeft extends CommandGroup {

	public ScoreSideGearLeft() {
		// get into position to score gear
		addSequential(new ScoreSideGearPart1Left());

		// score gear, drive back 25 inches
		addSequential(new VisionPlaceGear(ScoreSideGearData.DRIVE_2_DRIVE_BACK_SETPOINT));
	}

}
