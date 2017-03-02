package org.usfirst.frc.team1732.robot.autocommands.scoregearthenballs;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;

public class ScoreGearThenBallsData {

	// determine distanceStop with tape measure
	private static final double	distanceStop			= 100;
	static final DoubleSupplier	DRIVE_1_LEFT_SETPOINT	= () -> distanceStop - Robot.driveTrain.getTotalLeftDistance();
	static final DoubleSupplier	DRIVE_1_RIGHT_SETPOINT	= () -> distanceStop - Robot.driveTrain.getTotalRightDistance();

	static final double	TURN_1_ANGLE_RED	= -146;
	static final double	TURN_1_ANGLE_BLUE	= -TURN_1_ANGLE_RED;

	static final double		DRIVE_2_SETPOINT	= 65;
	static final double		DRIVE_2_LEFT_SPEED	= 0.7;
	static final double		DRIVE_2_RIGHT_SPEED	= DRIVE_2_LEFT_SPEED;
	static final boolean	DRIVE_2_STOP_AT_END	= false;

	static final double DRIVE_3_SETPOINT = 45;

	static final double SHOOT_TIME = 5;

}
