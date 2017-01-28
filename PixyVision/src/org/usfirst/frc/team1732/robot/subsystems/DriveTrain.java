package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;
import org.usfirst.frc.team1732.robot.commands.DriveWithJoysticks;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DriveTrain extends Subsystem {

	// motors
	// left motors
	private final CANTalon	leftMaster	= new CANTalon(RobotMap.LEFT_MASTER_MOTOR_DEVICE_NUMBER);
	private final CANTalon	left1		= new CANTalon(RobotMap.LEFT_1_MOTOR_DEVICE_NUMBER);
	private final CANTalon	left2		= new CANTalon(RobotMap.LEFT_2_MOTOR_DEVICE_NUMBER);
	// right motors
	private final CANTalon	rightMaster	= new CANTalon(RobotMap.RIGHT_MASTER_MOTOR_DEVICE_NUMBER);
	private final CANTalon	right1		= new CANTalon(RobotMap.RIGHT_1_MOTOR_DEVICE_NUMBER);
	private final CANTalon	right2		= new CANTalon(RobotMap.RIGHT_2_MOTOR_DEVICE_NUMBER);

	// gyro
	// gyro sensors
	private final AnalogGyro	gyro								= new AnalogGyro(RobotMap.GYRO_CHANNEL_NUMBER);
	private final PIDOutput		gyroOutput							= speed -> {
																		rightMaster.set(speed);
																		leftMaster.set(-speed);
																	};
	public static final double	GYRO_VOLTS_PER_DEGREE_PER_SECOND	= 0.007;
	// gyro controllers
	private final PIDController	gyroController			= new PIDController(gyroP, gyroI, gyroD, gyro, gyroOutput);
	public static final double	GYRO_DEADBAND_DEGREES	= 5;
	private static double		gyroP					= 0.1;
	private static double		gyroI					= 0;
	private static double		gyroD					= 0;

	// encoders
	// encoder sensors
	private final Encoder		leftEncoder					= new Encoder(	RobotMap.LEFT_ENCODER_CHANNEL_A,
																			RobotMap.LEFT_ENCODER_CHANNEL_B);
	private final Encoder		rightEncoder				= new Encoder(	RobotMap.RIGHT_ENCODER_CHANNEL_A,
																			RobotMap.RIGHT_ENCODER_CHANNEL_B);
	public static final double	INCHES_PER_ENCODER_COUNT	= 108.0 / (458.0 * 4);
	public static final double	LEFT_MOTOR_OFFSET			= 1.1;
	// encoder controllers
	private final PIDController leftEncoderController = new PIDController(	encoderP, encoderI, encoderD, leftEncoder,
																			d -> leftMaster
																					.set(-d * LEFT_MOTOR_OFFSET));
	// FIXME (both use left)
	private final PIDController	rightEncoderController	= new PIDController(encoderP, encoderI, encoderD, leftEncoder,
																			d -> rightMaster.set(-d));
	public static final double	ENCODER_DEADBAND_INCHES	= 7;
	private static double		encoderP				= 0.01;
	private static double		encoderI				= 0;
	private static double		encoderD				= 0;

	public static final double	MAX_OUTPUT	= 0.5;
	public static final double	MIN_OUTPUT	= -0.5;

	public DriveTrain() {
		super("Drive Train");
		leftMaster.setInverted(true);
		left1.changeControlMode(TalonControlMode.Follower);
		left1.set(leftMaster.getDeviceID());
		left2.changeControlMode(TalonControlMode.Follower);
		left2.set(leftMaster.getDeviceID());
		// left1.reverseOutput(true);
		// left2.reverseOutput(true);

		right1.changeControlMode(TalonControlMode.Follower);
		right1.set(rightMaster.getDeviceID());
		right2.changeControlMode(TalonControlMode.Follower);
		right2.set(rightMaster.getDeviceID());
		// right1.reverseOutput(true);
		// right2.reverseOutput(true);

		gyro.initGyro();
		gyro.calibrate();
		gyro.setPIDSourceType(PIDSourceType.kDisplacement);
		gyro.setSensitivity(GYRO_VOLTS_PER_DEGREE_PER_SECOND);

		gyroController.setAbsoluteTolerance(GYRO_DEADBAND_DEGREES);
		gyroController.setContinuous(false);
		gyroController.setOutputRange(MIN_OUTPUT, MAX_OUTPUT);

		leftEncoder.setDistancePerPulse(INCHES_PER_ENCODER_COUNT);
		rightEncoder.setDistancePerPulse(INCHES_PER_ENCODER_COUNT);
		leftEncoder.setReverseDirection(true);
		rightEncoder.setReverseDirection(false);
		leftEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
		rightEncoder.setPIDSourceType(PIDSourceType.kDisplacement);

		leftEncoderController.setAbsoluteTolerance(ENCODER_DEADBAND_INCHES);
		rightEncoderController.setAbsoluteTolerance(ENCODER_DEADBAND_INCHES);
		leftEncoderController.setContinuous(false);
		rightEncoderController.setContinuous(false);
		leftEncoderController.setOutputRange(MIN_OUTPUT, MAX_OUTPUT);
		rightEncoderController.setOutputRange(MIN_OUTPUT, MAX_OUTPUT);
		setDriveManual();
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks());
	}

	public void tankDrive(double left, double right) {
		driveRaw(left, right);
	}

	private void driveRaw(double left, double right) {
		leftMaster.set(limit(left));
		rightMaster.set(limit(right));
	}

	private double limit(double speed) {
		return speed < MIN_OUTPUT ? MIN_OUTPUT : (speed > MAX_OUTPUT ? MAX_OUTPUT : speed);
	}

	public void zeroGyro() {
		gyro.reset();
	}

	public void setGyroSetpointDegrees(double setpoint) {
		gyroController.setSetpoint(setpoint);
	}

	public boolean isAtGyroSetpoint() {
		return gyroController.onTarget();
	}

	public void zeroEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
	}

	public void setEncoderSetpointInches(double leftSetpoint, double rightSetpoint) {
		leftEncoderController.setSetpoint(leftSetpoint);
		rightEncoderController.setSetpoint(rightSetpoint);
	}

	public boolean isAtEncoderSetpoint() {
		return leftEncoderController.onTarget() && rightEncoderController.onTarget();
	}

	public double getLeftEncoderDistance() {
		return leftEncoder.getDistance();
	}

	public double getRightEncoderDistance() {
		return rightEncoder.getDistance();
	}

	public void setDriveManual() {
		gyroController.disable();
		leftEncoderController.disable();
		rightEncoderController.disable();
	}

	public void setDriveWithEncoders() {
		gyroController.disable();
		leftEncoderController.enable();
		rightEncoderController.enable();
	}

	public void setDriveWithGyros() {
		gyroController.enable();
		leftEncoderController.disable();
		rightEncoderController.disable();
	}

	public double getLeftEncoderCount() {
		return leftEncoder.get();
	}

	public double getRightEncoderCount() {
		return rightEncoder.get();
	}

	public double getLeftEncoderSetpoint() {
		return leftEncoderController.getSetpoint();
	}

	public double getRightEncoderSetpoint() {
		return rightEncoderController.getSetpoint();
	}

}
