package org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GrabBallsKeylineAndShootData {
	static final double DRIVE_1_SETPOINT = 90;

	static final double TURN_1_ANGLE_RED = 30;
	static final double TURN_1_ANGLE_BLUE = -TURN_1_ANGLE_RED;

	static final double DRIVE_2_TIME = 1;

	static final double WAIT_1_TIME = 2;

	static final double DRIVE_3_SETPOINT = 1;

	static final double TURN_2_ANGLE_RED = 110;
	static final double TURN_2_ANGLE_BLUE = -TURN_2_ANGLE_RED;

	static final double DRIVE_4_SETPOINT = 50;

	static final double	TURN_3_TIME_RED		= .5;
	static final double	TURN_3_TIME_BLUE		= .5;
	static final double	DRIVE_RIGHT_SPEED_RED	= .5;
	static final double	DRIVE_LEFT_SPEED_RED	= -.1;
	static final double	DRIVE_RIGHT_SPEED_BLUE	= -.1;
	static final double	DRIVE_LEFT_SPEED_BLUE	= .5;

	static final double SHOOT_TIME = 5;

}