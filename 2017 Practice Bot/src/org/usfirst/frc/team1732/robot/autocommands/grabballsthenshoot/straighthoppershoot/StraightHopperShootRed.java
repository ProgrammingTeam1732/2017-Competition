package org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.straighthoppershoot;

import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;
import org.usfirst.frc.team1732.robot.commands.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.flywheel.ShootTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StraightHopperShootRed extends CommandGroup {

	public StraightHopperShootRed() {
		addSequential(new InitGearIntake());
		addSequential(new DriveEncoders(StraightHopperShootData.DRIVE_1_SETPOINT, 0));

		addSequential(new TurnWithEncoders(StraightHopperShootData.TURN_1_ANGLE_RED));

		addSequential(new DriveTime(StraightHopperShootData.DRIVE_2_TIME,
									StraightHopperShootData.DRIVE_2_SPEED));

		addSequential(new Wait(StraightHopperShootData.WAIT_1_TIME));

		addSequential(new DriveTime(StraightHopperShootData.DRIVE_3_TIME,
									StraightHopperShootData.DRIVE_3_SPEED));

		addSequential(new TurnWithEncoders(StraightHopperShootData.TURN_2_ANGLE_RED));

		addSequential(new DriveEncoders(StraightHopperShootData.DRIVE_4_SETPOINT, 0));
		addParallel(new EnableFlywheel());
		addSequential(new DriveTime(StraightHopperShootData.TURN_3_TIME_RED,
									StraightHopperShootData.DRIVE_LEFT_SPEED_RED,
									StraightHopperShootData.DRIVE_RIGHT_SPEED_RED));
		addSequential(new ShootTime(StraightHopperShootData.SHOOT_TIME));
	}
}