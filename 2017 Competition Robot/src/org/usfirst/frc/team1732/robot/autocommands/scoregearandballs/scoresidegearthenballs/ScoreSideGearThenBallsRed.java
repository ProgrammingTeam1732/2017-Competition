package org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoresidegearthenballs;

import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.ScoreSideGearPart1Right;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveUntilEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.vision.VisionPlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreSideGearThenBallsRed extends CommandGroup {

	public ScoreSideGearThenBallsRed() {
		addSequential(new InitGearIntake());

		// GEAR SCORING

		// Positions robot in front of gear peg
		addSequential(new ScoreSideGearPart1Right()); // right if red
		// only using Part1 of ScoreSideGear because we want to drive back a
		// custom distance

		// clears total distance
		addSequential(new ClearTotalDistance());

		// places the gear, drives back
		addSequential(new VisionPlaceGear(	ScoreSideGearThenBallsData.DRIVE_1_LEFT_SETPOINT,
											ScoreSideGearThenBallsData.DRIVE_1_RIGHT_SETPOINT,
											ScoreSideGearThenBallsData.MAX_SETPOINT, true));

		// BALL SCORING

		// turn to face boiler
		addSequential(new TurnWithEncoders(ScoreSideGearThenBallsData.TURN_1_ANGLE_RED));

		// drive fast for part of distance
		addSequential(new DriveUntilEncoders(	ScoreSideGearThenBallsData.DRIVE_2_SETPOINT,
												ScoreSideGearThenBallsData.DRIVE_2_LEFT_SPEED,
												ScoreSideGearThenBallsData.DRIVE_2_RIGHT_SPEED,
												ScoreSideGearThenBallsData.DRIVE_2_STOP_AT_END));

		// use PID for rest of distance
		addSequential(new DriveEncoders(ScoreSideGearThenBallsData.DRIVE_3_SETPOINT));

		// shooting commands
		// addSequential(new ShootTime(ScoreGearThenBallsData.SHOOT_TIME));

		// drive towards hoppers
		// addSequential(new DriveToHopperFromBoilerRed());
	}
}
