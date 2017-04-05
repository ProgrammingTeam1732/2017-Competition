package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.base;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.vision.VisionPlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreSideGear extends CommandGroup {

	public ScoreSideGear(boolean isLeft) {
		addSequential(new InitGearIntake());
		// wait to move
		addSequential(new Wait(Robot.autoWaitTime.getValue()));

		// get into position to score gear
		addSequential(new ScoreSideGearPart1(isLeft));

		// score gear, drive back 25 inches
		double driveBackDistance = -25;
		double maxSetpoint = 80;
		addSequential(new VisionPlaceGear(driveBackDistance, maxSetpoint, true));

		// drive to hoppers
		// addSequential(new DriveToHopperFromLeftGearPeg());
	}

}
