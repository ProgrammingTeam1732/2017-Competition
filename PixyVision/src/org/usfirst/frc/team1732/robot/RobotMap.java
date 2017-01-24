package org.usfirst.frc.team1732.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// Controller
	public static final int	LEFT_JOYSTICK_AXIS	= 1;
	public static final int	RIGHT_JOYSTICK_AXIS	= 5;

	// Drive Train
	public static final int	LEFT_MASTER_MOTOR_DEVICE_NUMBER		= 15;
	public static final int	LEFT_1_MOTOR_DEVICE_NUMBER			= 14;
	public static final int	LEFT_2_MOTOR_DEVICE_NUMBER			= 13;
	public static final int	RIGHT_MASTER_MOTOR_DEVICE_NUMBER	= 21;
	public static final int	RIGHT_1_MOTOR_DEVICE_NUMBER			= 19;
	public static final int	RIGHT_2_MOTOR_DEVICE_NUMBER			= 20;
	public static final int	GYRO_CHANNEL_NUMBER					= 1;
	public static final int	LEFT_ENCODER_CHANNEL_A				= 3;
	public static final int	LEFT_ENCODER_CHANNEL_B				= 2;
	public static final int	RIGHT_ENCODER_CHANNEL_A				= 1;
	public static final int	RIGHT_ENCODER_CHANNEL_B				= 0;
}