package org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot;

import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveGyro;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.flywheel.ShootTime;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GrabBallsForwardAndShootRed extends CommandGroup {

	public GrabBallsForwardAndShootRed() {
		addSequential(new DriveEncoders(GrabBallsForwardAndShootData.DRIVE_1_SETPOINT));
		addSequential(new TurnWithGyro(GrabBallsForwardAndShootData.TURN_1_ANGLE_RED));
		addSequential(new DriveEncoders(GrabBallsForwardAndShootData.DRIVE_3_SETPOINT));
		addSequential(new TurnWithGyro(GrabBallsForwardAndShootData.TURN_2_ANGLE_RED));
		addSequential(new DriveEncoders(GrabBallsForwardAndShootData.DRIVE_4_SETPOINT));
		addSequential(new DriveGyro(GrabBallsForwardAndShootData.TURN_3_ANGLE_RED,
				GrabBallsForwardAndShootData.DRIVE_LEFT_SPEED_RED, GrabBallsForwardAndShootData.DRIVE_RIGHT_SPEED_RED));
		addParallel(new EnableFlywheel());
		addSequential(new ShootTime(GrabBallsForwardAndShootData.SHOOT_TIME));
	}
}
