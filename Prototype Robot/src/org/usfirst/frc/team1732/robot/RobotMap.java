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
	public static final int	LEFT_ENCODER_CHANNEL_A				= 0;
	public static final int	LEFT_ENCODER_CHANNEL_B				= 1;
	public static final int	RIGHT_ENCODER_CHANNEL_A				= 2;
	public static final int	RIGHT_ENCODER_CHANNEL_B				= 3;
	public static final int	GYRO_CHANNEL_NUMBER					= 0;

	// Flywheel
	public static final int FLYWHEEL_MOTER_DEVICE_NUMBER = 0;

	// Other shooter
	public static final int OTHER_SHOOTER_MOTER_DEVICE_NUMBER = 0;

	// Climber
	public static final int CLIMBER_MOTER_DEVICE_NUMBER = 0;

	// Ball Intake (Floor/Chute/Hopper)
	public static final int BALL_INTAKE_MOTER_DEVICE_NUMBER = 0;

	// Gear Intake (Floor/Chute)
	public static final int	GEAR_INTAKE_MOTER_DEVICE_NUMBER		= 24;
	public static final int	GEAR_MANIPULATOR_SOLENOID_NUMBER	= 6;

	// Hopper/Feeder/Index
	public static final int FEEDER_MOTER_DEVICE_NUMBER = 0;

	// Available motors IDs:
	public static final int	MOTOR_ONE_DEVICE_ID	= 12;
	public static final int	MOTOR_TWO_DEVICE_ID	= 24;

}
