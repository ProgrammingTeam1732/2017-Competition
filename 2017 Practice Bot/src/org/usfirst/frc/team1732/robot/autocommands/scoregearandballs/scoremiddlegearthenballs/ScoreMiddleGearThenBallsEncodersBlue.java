package org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoremiddlegearthenballs;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoremiddlegear.ScoreMiddleGearEncoders;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.ShootTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveUntilEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.ShuffleBallsWithWait;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

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
		addSequential(new ScoreMiddleGearEncoders(ScoreMiddleGearThenBallsData.DRIVE_1_SETPOINT));
		// BALL SCORING

		// turn to face boiler
		addSequential(new TurnWithEncoders(ScoreMiddleGearThenBallsData.TURN_1_ANGLE_BLUE));

		// wait to move
		addSequential(new Wait(Robot.autoWaitTime::getValue));

		addSequential(new EnableFlywheel());

		// drive fast for part of distance
		addSequential(new DriveUntilEncoders(ScoreMiddleGearThenBallsData.DRIVE_2_SETPOINT,
				ScoreMiddleGearThenBallsData.DRIVE_2_LEFT_SPEED, ScoreMiddleGearThenBallsData.DRIVE_2_RIGHT_SPEED,
				ScoreMiddleGearThenBallsData.DRIVE_2_STOP_AT_END));

		// use PID for rest of distance
		// addSequential(new
		// DriveEncoders(ScoreMiddleGearThenBallsData.DRIVE_3_SETPOINT));
		addSequential(new DriveTime(ScoreMiddleGearThenBallsData.DRIVE_INTO_BOILER_TIME,
				ScoreMiddleGearThenBallsData.DRIVE_INTO_BOILER_LEFT_SPEED_BLUE,
				ScoreMiddleGearThenBallsData.DRIVE_INTO_BOILER_RIGHT_SPEED_BLUE));

		addParallel(new DriveTime(ScoreMiddleGearThenBallsData.SHOOT_TIME,
				ScoreMiddleGearThenBallsData.CREEP_TOWARD_BOILER_SPEED));

		// shooting commands
		addParallel(new CommandGroup() {
			{
				addSequential(new Wait(2));
				addSequential(new ShuffleBallsWithWait());
			}
		});

		addSequential(new ShootTime(ScoreMiddleGearThenBallsData.SHOOT_TIME));

		// drive towards hoppers
		// addSequential(new DriveToHopperFromBoilerRed());
	}
}
