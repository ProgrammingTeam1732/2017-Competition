package org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.straighthoppershoot;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.ballsystem.feeder.FeederSetSpeed;
import org.usfirst.frc.team1732.robot.commands.ballsystem.feeder.FeederSetStop;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.ShootTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.ShuffleBallsWithWait;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StraightHopperShoot extends CommandGroup {

	public StraightHopperShoot() {
		boolean isRed = Robot.isRedAlliance.getValue();

		addSequential(new InitGearIntake());

		// wait to move
		addSequential(new Wait(Robot.autoWaitTime.getValue()));

		// drive towards hopper
		double driveTowardHopperDistance = 77;
		addSequential(new DriveEncoders(driveTowardHopperDistance));

		double faceHopperAngle = 0;
		if (isRed) {
			faceHopperAngle = 90;
		} else {
			faceHopperAngle = -90;
		}
		addSequential(new TurnWithEncoders(faceHopperAngle));

		// drive into hopper
		double driveIntoHopperTime = 2;
		double driveIntoHopperSpeed = 0.4;
		addSequential(new DriveTime(driveIntoHopperTime, driveIntoHopperSpeed));

		// turn on feeder while getting balls
		double feederSpeed = -1;
		addSequential(new FeederSetSpeed(feederSpeed));

		// wait while picking up balls
		double ballWaitTime = 1;
		addSequential(new Wait(ballWaitTime));

		// turn off feeder
		addSequential(new FeederSetStop());

		// drive away from hopper
		double driveAwayFromHopperTime = 0.6;
		double driveAwayFromHopperSpeed = -0.5;
		addSequential(new DriveTime(driveAwayFromHopperTime, driveAwayFromHopperSpeed));

		// turn towards boiler
		double turnTowardsBoilerAngle = 0;
		if (isRed) {
			turnTowardsBoilerAngle = 90;
		} else {
			turnTowardsBoilerAngle = -90;
		}
		addSequential(new TurnWithEncoders(turnTowardsBoilerAngle));

		// turn on flywheel
		addParallel(new EnableFlywheel());

		// drive to boiler
		double driveToBoilerDistance = 50;
		addSequential(new DriveEncoders(driveToBoilerDistance));

		// face boiler
		// Turn to face boiler
		double faceBoilerTime = 1;
		double faceBoilerLeftSpeed = 0;
		double faceBoilerRightSpeed = 0;
		if (isRed) {
			faceBoilerLeftSpeed = -0.1;
			faceBoilerRightSpeed = 0.5;
		} else {
			faceBoilerLeftSpeed = 0.5;
			faceBoilerRightSpeed = -0.1;
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