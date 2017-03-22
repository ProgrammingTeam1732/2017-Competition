package org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot;

import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.gyro.DriveGyro;
import org.usfirst.frc.team1732.robot.commands.drivetrain.gyro.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.flywheel.ShootTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GrabBallsKeylineAndShootRed extends CommandGroup {

	public GrabBallsKeylineAndShootRed() {
		addSequential(new InitGearIntake());
		addSequential(new DriveEncoders(GrabBallsKeylineAndShootData.DRIVE_1_SETPOINT, 0));
		addSequential(new TurnWithGyro(GrabBallsKeylineAndShootData.TURN_1_ANGLE_RED));
		addSequential(new DriveEncoders(GrabBallsKeylineAndShootData.DRIVE_3_SETPOINT, 0));
		addSequential(new TurnWithGyro(GrabBallsKeylineAndShootData.TURN_2_ANGLE_RED));
		addSequential(new DriveEncoders(GrabBallsKeylineAndShootData.DRIVE_4_SETPOINT, 0));
		addParallel(new EnableFlywheel());
		addSequential(new DriveGyro(GrabBallsKeylineAndShootData.TURN_3_TIME_RED,
				GrabBallsKeylineAndShootData.DRIVE_LEFT_SPEED_RED, GrabBallsKeylineAndShootData.DRIVE_RIGHT_SPEED_RED));
		addSequential(new ShootTime(GrabBallsKeylineAndShootData.SHOOT_TIME));
	}
}