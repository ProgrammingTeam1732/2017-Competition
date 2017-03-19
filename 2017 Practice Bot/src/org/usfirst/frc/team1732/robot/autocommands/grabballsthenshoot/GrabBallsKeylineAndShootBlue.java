package org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot;

import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveGyro;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.flywheel.ShootTime;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GrabBallsKeylineAndShootBlue extends CommandGroup {

	public GrabBallsKeylineAndShootBlue() {
		addSequential(new DriveEncoders(GrabBallsKeylineAndShootData.DRIVE_1_SETPOINT));
		addSequential(new TurnWithGyro(GrabBallsKeylineAndShootData.TURN_1_ANGLE_BLUE));
		addSequential(new DriveEncoders(GrabBallsKeylineAndShootData.DRIVE_3_SETPOINT));
		addSequential(new TurnWithGyro(GrabBallsKeylineAndShootData.TURN_2_ANGLE_BLUE));
		addSequential(new DriveEncoders(GrabBallsKeylineAndShootData.DRIVE_4_SETPOINT));
		addSequential(new DriveGyro(GrabBallsKeylineAndShootData.TURN_3_ANGLE_BLUE,
				GrabBallsKeylineAndShootData.DRIVE_LEFT_SPEED_BLUE,
				GrabBallsKeylineAndShootData.DRIVE_RIGHT_SPEED_BLUE));
		addParallel(new EnableFlywheel());
		addSequential(new ShootTime(GrabBallsKeylineAndShootData.SHOOT_TIME));
	}
}