package org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.straighthoppershoot;

import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.ShootTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StraightHopperShootBlue extends CommandGroup {

	public StraightHopperShootBlue() {
		addSequential(new InitGearIntake());
		addSequential(new DriveEncoders(StraightHopperShootData.DRIVE_1_SETPOINT));

		addSequential(new TurnWithEncoders(StraightHopperShootData.TURN_1_ANGLE_BLUE));

		addSequential(new DriveTime(StraightHopperShootData.DRIVE_2_TIME, StraightHopperShootData.DRIVE_2_SPEED));

		addSequential(new Wait(StraightHopperShootData.WAIT_1_TIME));

		addSequential(new DriveTime(StraightHopperShootData.DRIVE_3_TIME, StraightHopperShootData.DRIVE_3_SPEED));

		addSequential(new TurnWithEncoders(StraightHopperShootData.TURN_2_ANGLE_BLUE));

		addSequential(new DriveEncoders(StraightHopperShootData.DRIVE_4_SETPOINT));
		addParallel(new EnableFlywheel());
		addSequential(new DriveTime(StraightHopperShootData.TURN_3_TIME_BLUE,
									StraightHopperShootData.DRIVE_LEFT_SPEED_BLUE,
									StraightHopperShootData.DRIVE_RIGHT_SPEED_BLUE));
		addSequential(new ShootTime(StraightHopperShootData.SHOOT_TIME));
	}
}