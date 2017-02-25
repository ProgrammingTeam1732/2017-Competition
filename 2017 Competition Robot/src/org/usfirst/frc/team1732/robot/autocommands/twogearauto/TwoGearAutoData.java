package org.usfirst.frc.team1732.robot.autocommands.twogearauto;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;

public class TwoGearAutoData {

	static final DoubleSupplier	DRIVE_1_LEFT_SETPOINT	= () -> Robot.getDistanceToDriveBackForTwoGear()
			- Robot.driveTrain.getTotalLeftDistance();
	static final DoubleSupplier	DRIVE_1_RIGHT_SETPOINT	= () -> Robot.getDistanceToDriveBackForTwoGear()
			- Robot.driveTrain.getTotalRightDistance();

	static final double	TURN_1_ANGLE_RED	= -90;
	static final double	TURN_1_ANGLE_BLUE	= -TURN_1_ANGLE_RED;

	static final double DRIVE_2_SETPOINT = 30;

	static final double DRIVE_3_SETPOINT = -DRIVE_2_SETPOINT;

	static final double WAIT_1_TIME = 0;

	static final double	TURN_2_ANGLE_RED	= 90;
	static final double	TURN_2_ANGLE_BLUE	= -TURN_2_ANGLE_RED;

	static final double DRIVE_4_DRIVE_BACK_SETPOINT = -25;
}