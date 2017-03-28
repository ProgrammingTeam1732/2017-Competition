package org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.keylinehoppershoot;

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
public class KeylineHopperShootRed extends CommandGroup {

	public KeylineHopperShootRed() {
		addSequential(new InitGearIntake());
		addSequential(new DriveEncoders(KeylineHopperShootData.DRIVE_1_SETPOINT, 0));
		addSequential(new TurnWithGyro(KeylineHopperShootData.TURN_1_ANGLE_RED));
		addSequential(new DriveEncoders(KeylineHopperShootData.DRIVE_3_SETPOINT, 0));
		addSequential(new TurnWithGyro(KeylineHopperShootData.TURN_2_ANGLE_RED));
		addSequential(new DriveEncoders(KeylineHopperShootData.DRIVE_4_SETPOINT, 0));
		addParallel(new EnableFlywheel());
		addSequential(new DriveGyro(KeylineHopperShootData.TURN_3_TIME_RED,
				KeylineHopperShootData.DRIVE_LEFT_SPEED_RED, KeylineHopperShootData.DRIVE_RIGHT_SPEED_RED));
		addSequential(new ShootTime(KeylineHopperShootData.SHOOT_TIME));
	}
}