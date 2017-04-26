package org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.straighthoppershoot;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.ballsystem.feeder.FeederSetSpeed;
import org.usfirst.frc.team1732.robot.commands.ballsystem.feeder.FeederSetStop;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.ballsystem.shooting.ShootTime;
import org.usfirst.frc.team1732.robot.commands.ballsystem.shooting.ShuffleBallsWithWait;
import org.usfirst.frc.team1732.robot.commands.drivetrain.BrakeDriveNoShift;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncodersSimpleRamp;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveUntilEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncodersSimpleRamp;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.wings.WingsSetIn;
import org.usfirst.frc.team1732.robot.commands.wings.WingsSetOut;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StraightHopperShootArc extends CommandGroup {

    public StraightHopperShootArc() {
	boolean isRed = Robot.isRedAlliance.getValue();

	addSequential(new InitGearIntake());

	// wait to move
	addSequential(new Wait(Robot.autoWaitTime.getValue()));

	// drive towards hopper
	double driveTowardHopperDistance = 20;
	double driveTowardHopperSpeed = 1;
	boolean stop = false;
	addSequential(new DriveUntilEncoders(driveTowardHopperDistance, driveTowardHopperSpeed, driveTowardHopperSpeed,
		stop));

	addSequential(new WingsSetOut());

	DoubleSupplier leftArc;
	DoubleSupplier rightArc;
	double innerRadius = 35;
	double outerRadius = innerRadius + 25;
	double innerCircle = Math.PI * 2 * innerRadius / 4; // C = 2pi*r/4
	double outerCircle = Math.PI * 2 * outerRadius / 4; // C = 2pi*r/4
	double ratio = innerCircle / outerCircle;
	// double A = a;
	// double B = A / ratio;
	if (isRed) {
	    leftArc = () -> outerCircle;
	    rightArc = () -> innerCircle;
	} else {
	    leftArc = () -> innerCircle;
	    rightArc = () -> outerCircle;
	}

	addSequential(new DriveEncodersSimpleRamp(leftArc, rightArc));
	// double faceHopperAngle = 0;
	// if (isRed) {
	// faceHopperAngle = 90;
	// } else {
	// faceHopperAngle = -90;
	// }
	// addSequential(new TurnWithEncodersSimpleRamp(faceHopperAngle));

	// turn on feeder while getting balls
	double feederSpeed = -0.5;
	addSequential(new FeederSetSpeed(feederSpeed));

	// drive into hopper
	double driveIntoHopperTime = 0.5;
	double driveIntoHopperSpeed = 0.3;
	addSequential(new DriveTime(driveIntoHopperTime, driveIntoHopperSpeed));

	// wait while picking up balls
	double ballWaitTime = 1;
	addSequential(new Wait(ballWaitTime));

	addSequential(new BrakeDriveNoShift());

	// Start of new
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
	double driveToBoilerDistance = 55;
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
    // turn off feeder
    /*
     * addSequential(new FeederSetStop());
     * 
     * // Drive Backwards away from hopper double driveAwayFromHopperTime = .5;
     * double driveAwayFromHopperSpeed = -.6; addSequential(new
     * DriveTime(driveAwayFromHopperTime, driveAwayFromHopperSpeed));
     * 
     * addSequential(new WingsSetIn());
     * 
     * // Turn towards boiler double turnTowardsBoilerAngle = 0; if (isRed) {
     * turnTowardsBoilerAngle = 90; } else { turnTowardsBoilerAngle = -90; }
     * addSequential(new TurnWithEncodersSimpleRamp(turnTowardsBoilerAngle));
     * 
     * // turn on flywheel addSequential(new EnableFlywheel());
     * 
     * // Drive to boiler double driveToBoilerDistance = 50; addSequential(new
     * DriveEncoders(driveToBoilerDistance));
     * 
     * // Turn to face boiler double faceBoilerTime = .5; double
     * faceBoilerLeftSpeed = 0; double faceBoilerRightSpeed = 0; if (isRed) {
     * faceBoilerLeftSpeed = -0.1; faceBoilerRightSpeed = 0.5; } else {
     * faceBoilerLeftSpeed = 0.5; faceBoilerRightSpeed = -0.1; }
     * addSequential(new DriveTime(faceBoilerTime, faceBoilerLeftSpeed,
     * faceBoilerRightSpeed));
     * 
     * // addSequential(new DriveTime(.2, 1, .2)); // turn off feeder
     * addSequential(new FeederSetStop());
     * 
     * double shootTime = 15; // Creep Forward while shooting double creepTime =
     * shootTime; double creepSpeed = 0.3; addParallel(new DriveTime(creepTime,
     * creepSpeed));
     * 
     * // Shuffle balls while shooting, wait until against boiler to shuffle
     * addParallel(new CommandGroup() { { addSequential(new Wait(2));
     * addSequential(new ShuffleBallsWithWait(), shootTime); } });
     * 
     * // shoot balls addSequential(new ShootTime(shootTime)); }
     */

}