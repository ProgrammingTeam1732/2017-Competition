package org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoreballsthensidegear;

public class ScoreBallsThenGearSideData {

	static final double MAX_SETPOINT = 80;

	static final double DRIVE_1_SETPOINT = 15;

	static final double	TURN_1_ANGLE_RED		= 80;
	static final double	TURN_1_LEFT_SPEED_RED	= 0.7;
	static final double	TURN_1_RIGHT_SPEED_RED	= -0.3;

	static final double	TURN_1_ANGLE_BLUE		= -TURN_1_ANGLE_RED;
	static final double	TURN_1_LEFT_SPEED_BLUE	= TURN_1_RIGHT_SPEED_RED;
	static final double	TURN_1_RIGHT_SPEED_BLUE	= TURN_1_LEFT_SPEED_RED;

	static final double SHOOT_TIME = 7;

	static final double DRIVE_2_SETPOINT = -15;

	static final double	TURN_2_ANGLE_RED		= 140;
	static final double	TURN_2_LEFT_SPEED_RED	= 0;
	static final double	TURN_2_RIGHT_SPEED_RED	= -1;

	static final double	TURN_2_ANGLE_BLUE		= -TURN_2_ANGLE_RED;
	static final double	TURN_2_LEFT_SPEED_BLUE	= TURN_2_RIGHT_SPEED_RED;
	static final double	TURN_2_RIGHT_SPEED_BLUE	= TURN_2_LEFT_SPEED_RED;

	static final double DRIVE_3_SETPOINT = -15;

	static final double DRIVE_4_DRIVE_BACK_SETPOINT = -40;

}