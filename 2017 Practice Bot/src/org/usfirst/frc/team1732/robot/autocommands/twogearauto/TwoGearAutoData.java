package org.usfirst.frc.team1732.robot.autocommands.twogearauto;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;

public class TwoGearAutoData {

	static final DoubleSupplier	DRIVE_1_LEFT_SETPOINT	= () -> Robot.getDistanceToDriveBackForTwoGear()
			- Robot.driveTrain.getTotalLeftDistance();
	static final DoubleSupplier	DRIVE_1_RIGHT_SETPOINT	= () -> Robot.getDistanceToDriveBackForTwoGear()
			- Robot.driveTrain.getTotalRightDistance();

	static final double	TURN_1_ANGLE_LEFT	= -162;
	static final double	TURN_1_ANGLE_RIGHT	= -TURN_1_ANGLE_LEFT;

	static final double DRIVE_2_SPEED = 0.5;

	static final boolean	GRAB_GEAR_USE_TIMEOUT	= false;
	static final double		GRAB_GEAR_TIMEOUT		= 2.5;
	static final double		GRAB_GEAR_DISTANCE		= 30;

	static final double DRIVE_2_STOP_SPEED = 0;

	static final DoubleSupplier	DRIVE_3_LEFT_SETPOINT	= () -> -Robot.driveTrain.getTotalLeftDistance();
	static final DoubleSupplier	DRIVE_3_RIGHT_SETPOINT	= () -> -Robot.driveTrain.getTotalRightDistance();

	static final double WAIT_1_TIME = 0;

	static final double	TURN_2_ANGLE_LEFT	= -TURN_1_ANGLE_LEFT;
	static final double	TURN_2_ANGLE_RIGHT	= -TURN_2_ANGLE_LEFT;

	static final double DRIVE_4_DRIVE_BACK_SETPOINT = -25;
}