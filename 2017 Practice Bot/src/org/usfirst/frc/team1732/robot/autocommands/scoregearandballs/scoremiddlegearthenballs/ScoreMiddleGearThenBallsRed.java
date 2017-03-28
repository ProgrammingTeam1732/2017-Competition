package org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoremiddlegearthenballs;

import org.usfirst.frc.team1732.robot.autocommands.drivetohoppersatend.drivetohopperfromboiler.DriveToHopperFromBoilerRed;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.ScoreSideGearPart1Right;
import org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoreballsthengear.ScoreBallsThenGearData;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveUntilEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.gyro.DriveGyro;
import org.usfirst.frc.team1732.robot.commands.drivetrain.gyro.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.flywheel.ShootTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.vision.VisionPlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreMiddleGearThenBallsRed extends CommandGroup {

	public ScoreMiddleGearThenBallsRed() {
		addSequential(new InitGearIntake());

		// GEAR SCORING

		// Positions robot in front of gear peg
		// addSequential(new ScoreSideGearPart1Right()); // right if red
		// only using Part1 of ScoreSideGear because we want to drive back a
		// custom distance

		// clears total distance
		addSequential(new ClearTotalDistance());

		// places the gear, drives back
		addSequential(new VisionPlaceGear(ScoreMiddleGearThenBallsData.DRIVE_1_LEFT_SETPOINT,
				ScoreMiddleGearThenBallsData.DRIVE_1_RIGHT_SETPOINT, ScoreMiddleGearThenBallsData.MAX_SETPOINT, true));

		// BALL SCORING

		// turn to face boiler
		addSequential(new TurnWithGyro(ScoreMiddleGearThenBallsData.TURN_1_ANGLE_RED));

		// drive fast for part of distance
		addSequential(new DriveUntilEncoders(ScoreMiddleGearThenBallsData.DRIVE_2_SETPOINT,
				ScoreMiddleGearThenBallsData.DRIVE_2_LEFT_SPEED, ScoreMiddleGearThenBallsData.DRIVE_2_RIGHT_SPEED,
				ScoreMiddleGearThenBallsData.DRIVE_2_STOP_AT_END));

		// use PID for rest of distance
		// addSequential(new
		// DriveEncoders(ScoreMiddleGearThenBallsData.DRIVE_3_SETPOINT));
		addSequential(new DriveGyro(ScoreMiddleGearThenBallsData.TURN_2_ANGLE_RED,
				ScoreMiddleGearThenBallsData.TURN_2_LEFT_SPEED_RED, ScoreMiddleGearThenBallsData.TURN_2_RIGHT_SPEED_RED));
		// shooting commands
		addSequential(new ShootTime(ScoreMiddleGearThenBallsData.SHOOT_TIME));

		// drive towards hoppers
		addSequential(new DriveToHopperFromBoilerRed());
	}
}
