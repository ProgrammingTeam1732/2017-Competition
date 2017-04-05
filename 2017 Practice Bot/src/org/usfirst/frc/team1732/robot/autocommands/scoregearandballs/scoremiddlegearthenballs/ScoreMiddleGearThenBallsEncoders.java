package org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoremiddlegearthenballs;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoremiddlegear.ScoreMiddleGearEncoders;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.ShootTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveUntilEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.ShuffleBallsWithWait;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreMiddleGearThenBallsEncoders extends CommandGroup {

	public ScoreMiddleGearThenBallsEncoders() {
		boolean isRed = Robot.isRedAlliance.getValue();

		// GEAR SCORING

		// places the gear, drives back
		double driveBackDistance = -50;
		addSequential(new ScoreMiddleGearEncoders(driveBackDistance));

		// BALL SCORING

		// turn to face boiler
		double turnToBoilerAngle = 0;
		if (isRed) {
			turnToBoilerAngle = 137;
		} else {
			turnToBoilerAngle = -137;
		}
		addSequential(new TurnWithEncoders(turnToBoilerAngle));

		// wait to move
		addSequential(new Wait(Robot.autoWaitTime.getValue()));

		// turn on flywheel
		addSequential(new EnableFlywheel());

		// Drive to boiler
		// drive fast for part of distance
		double driveToBoilerFastDistance = 97;
		double driveToBoilerFastLeftSpeed = 0.7;
		double driveToBoilerFastRightSpeed = 0.7;
		boolean driveToBoilerFastStop = false;
		addSequential(new DriveUntilEncoders(	driveToBoilerFastDistance, driveToBoilerFastLeftSpeed,
												driveToBoilerFastRightSpeed, driveToBoilerFastStop));

		// use PID for rest of distance
		// double driveToBoilerPIDDistance = 45;
		// addSequential(new
		// DriveEncoders(driveToBoilerPIDDistance));

		// Face the boiler
		double faceBoilerTime = 0.43;
		double faceBoilerLeftSpeed = 0;
		double faceBoilerRightSpeed = 0;
		if (isRed) {
			faceBoilerLeftSpeed = 0.5;
			faceBoilerRightSpeed = -0.1;
		} else {
			faceBoilerLeftSpeed = -0.1;
			faceBoilerRightSpeed = 0.5;
		}
		addSequential(new DriveTime(faceBoilerTime, faceBoilerLeftSpeed, faceBoilerRightSpeed));

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
