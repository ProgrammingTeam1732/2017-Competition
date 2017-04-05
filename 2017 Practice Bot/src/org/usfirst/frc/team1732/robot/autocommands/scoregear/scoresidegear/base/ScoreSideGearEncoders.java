package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.base;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.EncoderPlaceGear;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreSideGearEncoders extends CommandGroup {

	public ScoreSideGearEncoders(boolean isLeft) {
		addSequential(new InitGearIntake());
		// wait to move
		addSequential(new Wait(Robot.autoWaitTime.getValue()));

		// get into position to score gear
		addSequential(new ScoreSideGearPart1(isLeft));

		// Drive, place, drive back
		double driveForwardDistance = 50;
		double driveBackDistance = -25;
		addSequential(new EncoderPlaceGear(driveForwardDistance, driveBackDistance));

	}
}
