package org.usfirst.frc.team1732.robot.autocommands.scoresidegear;

import org.usfirst.frc.team1732.robot.autocommands.visionplacegear.VisionPlaceGear;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreSideGearRight extends CommandGroup {

	public ScoreSideGearRight() {
		addSequential(new InitGearIntake());

		// get into position to score gear
		addSequential(new ScoreSideGearPart1Right());

		// score gear, drive back 25 inches
		addSequential(new VisionPlaceGear(ScoreSideGearData.DRIVE_2_DRIVE_BACK_SETPOINT));

		// drive to hoppers
		// addSequential(new DriveToHopperFromRightGearPeg());
	}
}
