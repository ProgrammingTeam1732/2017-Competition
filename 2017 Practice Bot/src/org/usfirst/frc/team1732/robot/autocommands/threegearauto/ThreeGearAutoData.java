package org.usfirst.frc.team1732.robot.autocommands.threegearauto;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;

public class ThreeGearAutoData {

	static final DoubleSupplier	DRIVE_1_LEFT_SETPOINT	= () -> Robot.getDistanceToDriveBackForTwoGear()
			- Robot.driveTrain.getTotalLeftDistance();
	static final DoubleSupplier	DRIVE_1_RIGHT_SETPOINT	= () -> Robot.getDistanceToDriveBackForTwoGear()
			- Robot.driveTrain.getTotalRightDistance();

	static final double	TURN_1_ANGLE_RED	= -90;
	static final double	TURN_1_ANGLE_BLUE	= -TURN_1_ANGLE_RED;

	static final double DRIVE_2_SPEED = 0.5;

	static final boolean	GRAB_GEAR_USE_TIMEOUT	= true;
	static final double		GRAB_GEAR_TIMEOUT		= 2.5;

	static final double DRIVE_2_STOP = 0;

	static final DoubleSupplier	DRIVE_3_LEFT_SETPOINT	= () -> -Robot.driveTrain.getTotalLeftDistance();
	static final DoubleSupplier	DRIVE_3_RIGHT_SETPOINT	= () -> -Robot.driveTrain.getTotalRightDistance();

	static final double WAIT_1_TIME = 0;

	static final double	TURN_2_ANGLE_RED	= 75;
	static final double	TURN_2_ANGLE_BLUE	= -TURN_2_ANGLE_RED;

	static final double DRIVE_4_DRIVE_BACK_SETPOINT = -25;
}