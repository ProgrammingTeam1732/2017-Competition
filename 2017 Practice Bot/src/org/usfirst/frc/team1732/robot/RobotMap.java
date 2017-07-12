package org.usfirst.frc.team1732.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

    // Controller
    public static final int LEFT_JOYSTICK_Y_AXIS = 1;
    public static final int RIGHT_JOYSTICK_Y_AXIS = 1;

    public static final int LEFT_JOYSTICK_USB = 2;
    public static final int RIGHT_JOYSTICK_USB = 1;
    public static final int BUTTONS_USB = 0;
    public static final int DIAL_USB = 3;

    // Drive Train
    public static final int LEFT_MASTER_MOTOR_DEVICE_NUMBER = 5;
    public static final int LEFT_1_MOTOR_DEVICE_NUMBER = 6;
    public static final int LEFT_2_MOTOR_DEVICE_NUMBER = 7;

    public static final int RIGHT_MASTER_MOTOR_DEVICE_NUMBER = 0;
    public static final int RIGHT_1_MOTOR_DEVICE_NUMBER = 1;
    public static final int RIGHT_2_MOTOR_DEVICE_NUMBER = 2;

    public static final int LEFT_ENCODER_CHANNEL_A = 0;
    public static final int LEFT_ENCODER_CHANNEL_B = 1;
    public static final int RIGHT_ENCODER_CHANNEL_A = 2;
    public static final int RIGHT_ENCODER_CHANNEL_B = 3;

    public static final int GYRO_CHANNEL_NUMBER = 0;

    public static final int DRIVE_TRAIN_SHIFTER_SOLENOID_DEVICE_NUMBER = 0;

    // Flywheel
    public static final int FLYWHEEL_MOTOR_DEVICE_NUMBER = 4;

    // Climber
    public static final int CLIMBER_MOTOR_DEVICE_NUMBER = 8;
    public static final int ARM_SOLENOID_DEVICE_NUMBER = 7;

    // Ball Intake (Floor/Chute/Hopper)
    public static final int BALL_INTAKE_MOTOR_DEVICE_NUMBER = 11;
    public static final int BALL_INTAKE_SOLENOID_DEVICE_NUMBER = 6;

    // Gear Intake (Floor/Chute)
    public static final int GEAR_INTAKE_MOTOR_DEVICE_NUMBER = 9;
    public static final int GEAR_POSITION_SOLENOID_NUMBER = 4;
    public static final int GEAR_STOPPER_SOLENOID_NUMBER = 5;

    // wings
    public static final int WING_SOLENOID_NUMER = 1;

    // Hopper/Feeder/Index
    public static final int FEEDER_MOTOR_DEVICE_NUMBER = 10;

    public static final int PCM_CAN_ID = 0;

    public static final int LIGHT_MOTOR_DEVICE_NUMBER = 3;

}
