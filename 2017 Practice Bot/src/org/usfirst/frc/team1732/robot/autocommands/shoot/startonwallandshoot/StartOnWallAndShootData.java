package org.usfirst.frc.team1732.robot.autocommands.shoot.startonwallandshoot;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;

public class StartOnWallAndShootData {

	static final DoubleSupplier DISTANCE_TO_MOVE = Robot.startOnWallAndShootDistance::getValue;

	static final double	DRIVE_INTO_BOILER_LEFT_SPEED_RED	= 0.7;
	static final double	DRIVE_INTO_BOILER_RIGHT_SPEED_RED	= 0.1;
	static final double	DRIVE_INTO_BOILER_LEFT_SPEED_BLUE	= DRIVE_INTO_BOILER_RIGHT_SPEED_RED;
	static final double	DRIVE_INTO_BOILER_RIGHT_SPEED_BLUE	= DRIVE_INTO_BOILER_LEFT_SPEED_RED;
	static final double	DRIVE_INTO_BOILER_TIME				= 2;

	static final double SHOOT_TIME = 15;

}