package org.usfirst.frc.team1732.robot.autocommands.sidetwogearauto;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;

public class SideTwoGearAutoData {

	static final double MAX_SETPOINT = 80;

	static final double	DRIVE_1_SETPOINT	= 56;
	static final double	TURN_1_LEFT			= 60;
	static final double	TURN_1_RIGHT		= -TURN_1_LEFT;

	static final DoubleSupplier	DRIVE_2_LEFT_SETPOINT	= () -> -10 - Robot.driveTrain.getTotalLeftDistance();
	static final DoubleSupplier	DRIVE_2_RIGHT_SETPOINT	= () -> -10 - Robot.driveTrain.getTotalRightDistance();

	static final double	TURN_2_LEFT			= -TURN_1_LEFT;
	static final double	TURN_2_RIGHT_SIDE	= -TURN_2_LEFT;

	static final double DRIVE_2_SPEED = 0.5;

	static final boolean	GRAB_GEAR_USE_TIMEOUT	= false;
	static final double		GRAB_GEAR_TIMEOUT		= 2.5;
	static final double		GRAB_GEAR_DISTANCE		= 40;

	static final double DRIVE_2_STOP_SPEED = 0;

	static final DoubleSupplier	DRIVE_3_LEFT_SETPOINT	= () -> -Robot.driveTrain.getTotalLeftDistance();
	static final DoubleSupplier	DRIVE_3_RIGHT_SETPOINT	= () -> -Robot.driveTrain.getTotalRightDistance();

	static final double WAIT_1_TIME = 0;

	static final double	TURN_3_ANGLE_LEFT	= TURN_1_LEFT;
	static final double	TURN_3_ANGLE_RIGHT	= -TURN_3_ANGLE_LEFT;

	static final double DRIVE_4_DRIVE_BACK_SETPOINT = -25;
}