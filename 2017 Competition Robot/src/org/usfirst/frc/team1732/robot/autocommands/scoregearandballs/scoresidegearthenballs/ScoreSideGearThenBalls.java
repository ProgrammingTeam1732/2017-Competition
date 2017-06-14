package org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoresidegearthenballs;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.ScoreSideGearPart1Left;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.ScoreSideGearPart1Right;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.ballsystem.shooting.ShootTime;
import org.usfirst.frc.team1732.robot.commands.ballsystem.shooting.ShuffleBallsWithWait;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveUntilEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.placegear.EncoderPlaceGear;
import org.usfirst.frc.team1732.robot.commands.placegear.VisionPlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreSideGearThenBalls extends CommandGroup {

	public ScoreSideGearThenBalls(boolean useVision) {
		boolean isRed = Robot.isRedAlliance.getValue();

		addSequential(new InitGearIntake());

		// wait to move
		addSequential(new Wait(Robot.autoWaitTime::getValue));

		// GEAR SCORING

		// Positions robot in front of gear peg
		if (isRed) {
			addSequential(new ScoreSideGearPart1Right());
		} else {
			addSequential(new ScoreSideGearPart1Left());
		}
		// clears total distance
		addSequential(new ClearTotalDistance());

		// places the gear, drives back
		double placeGearBackwardDistance = -50;
		if (useVision) {
			double placeGearMaxSetpoint = 80;
			addSequential(new VisionPlaceGear(placeGearBackwardDistance, placeGearMaxSetpoint, true));
		} else {
			double placeGearForward = 50;
			addSequential(new EncoderPlaceGear(placeGearForward, placeGearBackwardDistance));
		}

		// BALL SCORING

		// turns on flywheel
		addSequential(new EnableFlywheel());

		// turn to face boiler
		double turnTowardBoilerAngle = 0;
		if (isRed) {
			turnTowardBoilerAngle = -146;
		} else {
			turnTowardBoilerAngle = 146;
		}
		addSequential(new TurnWithEncoders(turnTowardBoilerAngle));

		// drive fast for part of distance
		// Drive to boiler
		// drive fast for part of distance
		double driveToBoilerFastDistance = 65;
		double driveToBoilerFastLeftSpeed = 0.7;
		double driveToBoilerFastRightSpeed = 0.7;
		boolean driveToBoilerFastStop = false;
		addSequential(new DriveUntilEncoders(	driveToBoilerFastDistance, driveToBoilerFastLeftSpeed,
												driveToBoilerFastRightSpeed, driveToBoilerFastStop));

		// use PID for rest of distance
		double driveToBoilerPIDDistance = 45;
		addSequential(new DriveEncoders(driveToBoilerPIDDistance));

		double shootTime = 15;
		// Creep Forward while shooting
		double creepTime = shootTime;
		double creepSpeed = 0.3;
		addParallel(new DriveTime(creepTime, creepSpeed));

		// Shuffle balls while shooting, wait until against boiler to shuffle
		addParallel(new CommandGroup() {
			{
				addSequential(new Wait(2));
				addSequential(new ShuffleBallsWithWait(), shootTime);
			}
		});

		// shoot balls
		addSequential(new ShootTime(shootTime));
	}

	@Override
	public void interrupted() {
		end();
	}

	@Override
	public void end() {
		Robot.feeder.setStop(); // stop feeder
		Robot.gearIntake.setUp(); // make sure gearIntake is up after shuffling
		Robot.flywheel.disableAutoControl(); // turn off flywheel
	}
}
