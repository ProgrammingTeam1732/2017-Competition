package org.usfirst.frc.team1732.robot.autocommands.scoresidegearthenballs;

import org.usfirst.frc.team1732.robot.autocommands.scoresidegear.ScoreSideGearPart1Right;
import org.usfirst.frc.team1732.robot.autocommands.visionplacegear.VisionPlaceGear;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveUntilEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.gyro.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreGearThenBallsRed extends CommandGroup {

	public ScoreGearThenBallsRed() {
		addSequential(new InitGearIntake());

		// GEAR SCORING

		// Positions robot in front of gear peg
		addSequential(new ScoreSideGearPart1Right()); // right if red
		// only using Part1 of ScoreSideGear because we want to drive back a
		// custom distance

		// clears total distance
		addSequential(new ClearTotalDistance());

		// places the gear, drives back
		addSequential(new VisionPlaceGear(ScoreGearThenBallsData.DRIVE_1_LEFT_SETPOINT,
				ScoreGearThenBallsData.DRIVE_1_RIGHT_SETPOINT, ScoreGearThenBallsData.MAX_SETPOINT, true));

		// BALL SCORING

		// turn to face boiler
		addSequential(new TurnWithGyro(ScoreGearThenBallsData.TURN_1_ANGLE_RED));

		// drive fast for part of distance
		addSequential(new DriveUntilEncoders(ScoreGearThenBallsData.DRIVE_2_SETPOINT,
				ScoreGearThenBallsData.DRIVE_2_LEFT_SPEED, ScoreGearThenBallsData.DRIVE_2_RIGHT_SPEED,
				ScoreGearThenBallsData.DRIVE_2_STOP_AT_END));

		// use PID for rest of distance
		addSequential(new DriveEncoders(ScoreGearThenBallsData.DRIVE_3_SETPOINT, 0));

		// shooting commands
		// addSequential(new ShootTime(ScoreGearThenBallsData.SHOOT_TIME));

		// drive towards hoppers
		// addSequential(new DriveToHopperFromBoilerRed());
	}
}
