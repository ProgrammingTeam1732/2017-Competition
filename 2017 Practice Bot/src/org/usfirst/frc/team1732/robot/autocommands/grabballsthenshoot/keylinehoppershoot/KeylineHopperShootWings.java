package org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.keylinehoppershoot;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.ballsystem.feeder.FeederSetSpeed;
import org.usfirst.frc.team1732.robot.commands.ballsystem.feeder.FeederSetStop;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.ballsystem.shooting.ShootTime;
import org.usfirst.frc.team1732.robot.commands.ballsystem.shooting.ShuffleBallsWithWait;
import org.usfirst.frc.team1732.robot.commands.drivetrain.BrakeDriveNoShift;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncodersSimpleRamp;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.wings.WingsSetIn;
import org.usfirst.frc.team1732.robot.commands.wings.WingsSetOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class KeylineHopperShootWings extends CommandGroup {

    public KeylineHopperShootWings() {
	boolean isRed = Robot.isRedAlliance.getValue();

	addSequential(new InitGearIntake());

	// wait to move
	addSequential(new Wait(Robot.autoWaitTime.getValue()));

	// Drive Along Keyline To Hopper
	double keylineDriveDistance = 0;
	if (isRed) {
	    keylineDriveDistance = 70;
	} else {
	    keylineDriveDistance = 70;
	}
	addSequential(new DriveEncoders(keylineDriveDistance));

	addSequential(new WingsSetOut());

	// Turn to Face Hopper
	double faceHopperTime = 1;
	double faceHopperLeftSpeed = 0;
	double faceHopperRightSpeed = 0;
	if (isRed) {
	    faceHopperLeftSpeed = .4;
	    faceHopperRightSpeed = 0.1;
	} else {
	    faceHopperLeftSpeed = 0.1;
	    faceHopperRightSpeed = .4;
	}
	addSequential(new DriveTime(faceHopperTime, faceHopperLeftSpeed, faceHopperRightSpeed));

	// turn on feeder while getting balls
	double feederSpeed = -1;
	addSequential(new FeederSetSpeed(feederSpeed));

	// Drive Into Hopper
	double driveIntoHopperTime = 1.3;
	double driveIntoHopperLeftSpeed = 0.8;
	double driveIntoHopperRightSpeed = 0.8;
	addSequential(new DriveTime(driveIntoHopperTime, driveIntoHopperLeftSpeed, driveIntoHopperRightSpeed));

	// Wait to fill up balls
	double ballFillUpWaitTime = 0.3;
	addSequential(new Wait(ballFillUpWaitTime));

	// Drive Backwards away from hopper
	double driveAwayFromHopperTime = .5;
	double driveAwayFromHopperSpeed = -.6;
	addSequential(new DriveTime(driveAwayFromHopperTime, driveAwayFromHopperSpeed));

	addSequential(new WingsSetIn());

	addSequential(new BrakeDriveNoShift());

	// Turn towards boiler
	double turnTowardsBoilerAngle = 0;
	if (isRed) {
	    turnTowardsBoilerAngle = 90; // 125
	} else {
	    turnTowardsBoilerAngle = -90; // -125
	}
	// normal TurnWithEncoders
	addSequential(new TurnWithEncodersSimpleRamp(turnTowardsBoilerAngle));

	// Turn towards boiler
	// double turnTowardsBoilerAngle = 0;
	// if (isRed) {
	// turnTowardsBoilerAngle = 125;
	// } else {
	// turnTowardsBoilerAngle = -125;
	// }
	// addSequential(new TurnWithEncoders(turnTowardsBoilerAngle));

	// turn on flywheel
	addSequential(new EnableFlywheel());

	// Drive to boiler
	double driveToBoilerDistance = 60;
	addSequential(new DriveEncoders(driveToBoilerDistance));

	// Turn to face boiler
	double faceBoilerTime = .5;
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

	// addSequential(new DriveTime(.2, 1, .2));
	// turn off feeder
	addSequential(new FeederSetStop());

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

}