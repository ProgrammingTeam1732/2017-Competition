package org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoresidegearthenballs;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.ScoreSideGearPart1Right;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveUntilEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.EncoderPlaceGear;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreSideGearThenBallsEncodersRed extends CommandGroup {

	public ScoreSideGearThenBallsEncodersRed() {
		addSequential(new InitGearIntake());
		// wait to move
		addSequential(new Wait(Robot.autoWaitTime.getValue()));

		// GEAR SCORING

		// Positions robot in front of gear peg
		addSequential(new ScoreSideGearPart1Right()); // right if red
		// only using Part1 of ScoreSideGear because we want to drive back a
		// custom distance

		// clears total distance
		addSequential(new ClearTotalDistance());

		// places the gear, drives back
		addSequential(new EncoderPlaceGear(	ScoreSideGearThenBallsData.DRIVE_INTO_GEAR_PEG_SETPOINT,
											ScoreSideGearThenBallsData.DRIVE_BACK_SETPOINT));

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
