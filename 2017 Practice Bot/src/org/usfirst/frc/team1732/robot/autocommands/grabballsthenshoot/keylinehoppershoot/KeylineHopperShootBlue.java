package org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.keylinehoppershoot;

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
public class KeylineHopperShootBlue extends CommandGroup {

	public KeylineHopperShootBlue() {
		addSequential(new InitGearIntake());
		// Drive Along Keyline To Hopper
		addSequential(new DriveEncoders(KeylineHopperShootData.DRIVE_1_SETPOINT, 0));
		// Turn to Face Hopper
		addSequential(new DriveTime(KeylineHopperShootData.TURN_TO_HOPPER_TIME,
				KeylineHopperShootData.TURN_TO_HOPPER_LEFT_SPEED,
				KeylineHopperShootData.TURN_TO_HOPPER_RIGHT_SPEED));
		// addSequential(new DriveTime(1, .1, .4));
		// addSequential(new DriveTime(.5, .3, .3));
		// Drive Into Hopper
		addSequential(new DriveTime(KeylineHopperShootData.DRIVE_INTO_HOPPER_TIME,
				KeylineHopperShootData.DRIVE_INTO_HOPPER_LEFT_SPEED,
				KeylineHopperShootData.DRIVE_INTO_HOPPER_RIGHT_SPEED));
		// addSequential(new
		// DriveTime(GrabBallsKeylineAndShootData.DRIVE_3_SETPOINT, .4));
		// Wait to fill up balls
		addSequential(new Wait(KeylineHopperShootData.WAIT_1_TIME));
		// Drive Backwards
		addSequential(new DriveTime(KeylineHopperShootData.DRIVE_BACK_TIME,
				KeylineHopperShootData.DRIVE_BACK_SPEED));
		// Turn to face boiler
		addSequential(new TurnWithEncoders(KeylineHopperShootData.TURN_2_ANGLE_BLUE));
		addParallel(new EnableFlywheel());

		// Drive to boiler
		addSequential(new DriveEncoders(KeylineHopperShootData.DRIVE_4_SETPOINT, 0));

		// Turn to face boiler
		addSequential(new DriveTime(KeylineHopperShootData.TURN_3_TIME_BLUE,
				KeylineHopperShootData.DRIVE_LEFT_SPEED_BLUE,
				KeylineHopperShootData.DRIVE_RIGHT_SPEED_BLUE));
		// addSequential(new DriveTime(.2, 1, .2));
		// Creep Forward while shooting
		addParallel(new DriveTime(KeylineHopperShootData.SHOOT_TIME,
				KeylineHopperShootData.CREEP_TOWARD_BOILER_SPEED));
		addSequential(new ShootTime(KeylineHopperShootData.SHOOT_TIME));
	}
}