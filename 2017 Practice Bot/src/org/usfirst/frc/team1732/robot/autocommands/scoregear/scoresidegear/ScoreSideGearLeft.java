package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.vision.VisionPlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreSideGearLeft extends CommandGroup {

	public ScoreSideGearLeft() {
		addSequential(new InitGearIntake());
		// wait to move
		addSequential(new Wait(Robot.autoWaitTime.getValue()));

		// get into position to score gear
		addSequential(new ScoreSideGearPart1Left());

		// score gear, drive back 25 inches
		addSequential(new VisionPlaceGear(	ScoreSideGearData.DRIVE_2_DRIVE_BACK_SETPOINT, ScoreSideGearData.MAX_SETPOINT,
											true));

		// drive to hoppers
		// addSequential(new DriveToHopperFromLeftGearPeg());
	}

}
