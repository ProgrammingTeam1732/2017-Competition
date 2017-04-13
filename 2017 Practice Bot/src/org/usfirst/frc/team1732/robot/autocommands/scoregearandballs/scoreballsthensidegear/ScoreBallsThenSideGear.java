package org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoreballsthensidegear;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.DisableFlywheel;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.ballsystem.shooting.ShootTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.gyro.DriveGyro;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.placegear.EncoderPlaceGear;
import org.usfirst.frc.team1732.robot.commands.placegear.VisionPlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreBallsThenSideGear extends CommandGroup {

	public ScoreBallsThenSideGear(boolean useVision) {
		boolean isRed = Robot.isRedAlliance.getValue();

		addSequential(new InitGearIntake());

		// turn on flywheel
		addSequential(new EnableFlywheel());

		// drive forward slightly
		double inchForwardDistance = 15;
		addSequential(new DriveEncoders(inchForwardDistance));

		// face boiler
		double faceBoilerTime = 0.5;
		double faceBoilerLeftSpeed = 0;
		double faceBoilerRightSpeed = 0;
		if (isRed) {
			faceBoilerLeftSpeed = 0.5; // 0.7
			faceBoilerRightSpeed = -0.1; // -0.3
		} else {
			faceBoilerLeftSpeed = -0.1; // -0.3
			faceBoilerRightSpeed = 0.5; // 0.7
		}
		addSequential(new DriveGyro(faceBoilerTime, faceBoilerLeftSpeed, faceBoilerRightSpeed));

		double shootTime = 7;
		// Creep Forward while shooting
		double creepTime = shootTime;
		double creepSpeed = 0.3;
		addParallel(new DriveTime(creepTime, creepSpeed));

		// shoot balls
		addSequential(new ShootTime(shootTime));

		// wait to move
		addSequential(new Wait(Robot.autoWaitTime.getValue()));

		// turn off flywheel
		// might as turn off after wait to catch missed shots
		addSequential(new DisableFlywheel());

		// drive backwards slightly to avoid knocking wall
		double driveAwayFromBoilerDistance = -15;
		addSequential(new DriveEncoders(driveAwayFromBoilerDistance));

		// turn to face peg, curving path
		double faceGearPegTime = 1;
		double faceGearPegLeftSpeed = 0;
		double faceGearPegRightSpeed = 0;
		if (isRed) {
			faceGearPegLeftSpeed = 0;
			faceGearPegRightSpeed = -1;
		} else {
			faceGearPegLeftSpeed = -1;
			faceGearPegRightSpeed = 0;
		}
		addSequential(new DriveTime(faceGearPegTime, faceGearPegLeftSpeed, faceGearPegRightSpeed));

		// place gear, drive back 40 inches
		double driveBackDistance = -40;
		if (useVision) {
			double maxDistance = 80;
			addSequential(new VisionPlaceGear(driveBackDistance, maxDistance, true));
		} else {
			double driveForwardDistance = 50;
			addSequential(new EncoderPlaceGear(driveForwardDistance, driveBackDistance));
		}

		// drive to hoppers
		// addSequential(new DriveToHopperFromRightGearPeg());
	}
}
