package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.base;

import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreSideGearPart1 extends CommandGroup {

	public ScoreSideGearPart1(boolean isLeft) {
		// drive forward to turn to position
		double driveForward = 85;
		addSequential(new DriveEncoders(driveForward));

		// turn to face gear peg
		double angle = 0;
		if (isLeft) {
			angle = 50;
		} else {
			angle = -50;
		}
		addSequential(new TurnWithEncoders(angle));

		// wait after turning
		double wait = 0.2;
		addSequential(new Wait(wait));
	}
}
