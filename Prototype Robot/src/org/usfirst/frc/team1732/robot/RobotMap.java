package org.usfirst.frc.team1732.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	// Controller
	public static final int	LEFT_JOYSTICK_Y_AXIS	= 1;
	public static final int	RIGHT_JOYSTICK_Y_AXIS	= 1;
	public static final int	LEFT_JOYSTICK_USB		= 2;
	public static final int	RIGHT_JOYSTICK_USB		= 1;
	public static final int	BUTTONS_USB				= 0;

	// Drive Train
	public static final int	LEFT_MASTER_MOTOR_DEVICE_NUMBER		= 14;
	public static final int	LEFT_1_MOTOR_DEVICE_NUMBER			= 13;
	public static final int	LEFT_2_MOTOR_DEVICE_NUMBER			= 11;
	public static final int	RIGHT_MASTER_MOTOR_DEVICE_NUMBER	= 21;
	public static final int	RIGHT_1_MOTOR_DEVICE_NUMBER			= 22;
	public static final int	RIGHT_2_MOTOR_DEVICE_NUMBER			= 23;
	public static final int	LEFT_ENCODER_CHANNEL_A				= 6;
	public static final int	LEFT_ENCODER_CHANNEL_B				= 7;
	public static final int	RIGHT_ENCODER_CHANNEL_A				= 9;
	public static final int	RIGHT_ENCODER_CHANNEL_B				= 8;
	public static final int	GYRO_CHANNEL_NUMBER					= 0;

	// Gear Intake (Floor/Chute)
	public static final int	GEAR_INTAKE_MOTOR_DEVICE_NUMBER		= 24;
	public static final int	GEAR_MANIPULATOR_SOLENOID_NUMBER	= 6;
	public static final int	GEAR_MANIPULATOR_STORAGE_NUMBER		= 1;

	public static final int	PCM_CAN_ID									= 0;
	public static final int	DRIVE_TRAIN_SHIFTER_SOLENOID_DEVICE_NUMBER	= 2;

	public static final int	LIGHT_PWM_CHANNEL	= 0;
	public static final int	RELAY_CHANNEL		= 0;

}
