package org.usfirst.frc.team1732.robot.autocommands.shoot.startonwallandshoot;

import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.ShootTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncodersGetSetpointAtRuntime;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartOnWallAndShootBlue extends CommandGroup {

	public StartOnWallAndShootBlue() {
		// wait to move
		addSequential(new Wait(StartOnWallAndShootData.WAIT_TO_MOVE_TIME));
		// drive to boiler
		addSequential(new DriveEncodersGetSetpointAtRuntime(StartOnWallAndShootData.DISTANCE_TO_MOVE));
		addSequential(new EnableFlywheel());
		// turn into boiler
		addSequential(new DriveTime(StartOnWallAndShootData.DRIVE_INTO_BOILER_TIME,
									StartOnWallAndShootData.DRIVE_INTO_BOILER_LEFT_SPEED_BLUE,
									StartOnWallAndShootData.DRIVE_INTO_BOILER_RIGHT_SPEED_BLUE));
		// shoot balls
		addSequential(new ShootTime(StartOnWallAndShootData.SHOOT_TIME));
	}
}
