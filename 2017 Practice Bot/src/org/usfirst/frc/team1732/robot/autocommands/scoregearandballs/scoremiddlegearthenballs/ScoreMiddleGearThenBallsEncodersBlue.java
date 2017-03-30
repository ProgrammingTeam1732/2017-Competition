package org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoremiddlegearthenballs;

import org.usfirst.frc.team1732.robot.autocommands.drivetohoppersatend.drivetohopperfromboiler.DriveToHopperFromBoilerRed;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.ShootTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveUntilEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.EncoderPlaceGear;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.gyro.DriveGyro;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreMiddleGearThenBallsEncodersBlue extends CommandGroup {

	public ScoreMiddleGearThenBallsEncodersBlue() {
		addSequential(new InitGearIntake());

		// GEAR SCORING

		// Positions robot in front of gear peg
		// addSequential(new ScoreSideGearPart1Right()); // right if red
		// only using Part1 of ScoreSideGear because we want to drive back a
		// custom distance

		// clears total distance
		addSequential(new ClearTotalDistance());

		// places the gear, drives back
		addSequential(new EncoderPlaceGear(	() -> ScoreMiddleGearThenBallsData.DRIVE_FORWARD_INTO_GEAR_PEG,
											ScoreMiddleGearThenBallsData.DRIVE_1_LEFT_SETPOINT,
											ScoreMiddleGearThenBallsData.DRIVE_1_RIGHT_SETPOINT));

		// BALL SCORING

		// turn to face boiler
		addSequential(new TurnWithEncoders(ScoreMiddleGearThenBallsData.TURN_1_ANGLE_BLUE));

		// drive fast for part of distance
		addSequential(new DriveUntilEncoders(	ScoreMiddleGearThenBallsData.DRIVE_2_SETPOINT,
												ScoreMiddleGearThenBallsData.DRIVE_2_LEFT_SPEED,
												ScoreMiddleGearThenBallsData.DRIVE_2_RIGHT_SPEED,
												ScoreMiddleGearThenBallsData.DRIVE_2_STOP_AT_END));

		// use PID for rest of distance
		// addSequential(new
		// DriveEncoders(ScoreMiddleGearThenBallsData.DRIVE_3_SETPOINT));
		addSequential(new DriveGyro(ScoreMiddleGearThenBallsData.TURN_2_ANGLE_RED,
									ScoreMiddleGearThenBallsData.TURN_2_LEFT_SPEED_RED,
									ScoreMiddleGearThenBallsData.TURN_2_RIGHT_SPEED_RED));
		// shooting commands
		addSequential(new ShootTime(ScoreMiddleGearThenBallsData.SHOOT_TIME));

		// drive towards hoppers
		addSequential(new DriveToHopperFromBoilerRed());
	}
}
