package org.usfirst.frc.team1732.robot.subsystems;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import org.usfirst.frc.team1732.robot.RobotMap;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveWithJoysticks;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardGroup;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem implements SmartDashboardGroup {

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
	public final AnalogGyro		gyro								= new AnalogGyro(RobotMap.GYRO_CHANNEL_NUMBER);
	public static final double	GYRO_VOLTS_PER_DEGREE_PER_SECOND	= 0.007;

	// gyro controllers
	public final PIDController	gyroPID					= new PIDController(gyroP, gyroI, gyroD, gyro,
																			DriveTrain::voidMethod);
	public static final double	GYRO_DEADBAND_DEGREES	= 3;
	public static final double	gyroP					= -0.01;
	public static final double	gyroI					= 0;
	public static final double	gyroD					= 0;

	// encoders
	// encoder sensors
	public final Encoder		leftEncoder					= new Encoder(	RobotMap.LEFT_ENCODER_CHANNEL_A,
																			RobotMap.LEFT_ENCODER_CHANNEL_B);
	public final Encoder		rightEncoder				= new Encoder(	RobotMap.RIGHT_ENCODER_CHANNEL_A,
																			RobotMap.RIGHT_ENCODER_CHANNEL_B);
	public static final double	INCHES_PER_ENCODER_COUNT	= 0.0134 * 4;
	public static final double	LEFT_MOTOR_OFFSET			= 1.1;

	// encoder controllers
	public final PIDController	leftEncoderPID			= new PIDController(encoderP, encoderI, encoderD, leftEncoder,
																			DriveTrain::voidMethod);
	public final PIDController	rightEncoderPID			= new PIDController(encoderP, encoderI, encoderD, rightEncoder,
																			DriveTrain::voidMethod);
	public static final double	encoderP				= 0.02;
	public static final double	encoderI				= 0;
	public static final double	encoderD				= 0;
	public static final double	ENCODER_DEADBAND_INCHES	= 4;

	// Min and max output
	public static final double	MAX_OUTPUT	= 1;
	public static final double	MIN_OUTPUT	= -MAX_OUTPUT;

	public static final String NAME = "Drive Train";

	public DriveTrain() {
		super(NAME);
		left1.changeControlMode(TalonControlMode.Follower);
		left1.set(leftMaster.getDeviceID());
		left2.changeControlMode(TalonControlMode.Follower);
		left2.set(leftMaster.getDeviceID());
		left1.reverseOutput(true);
		left2.reverseOutput(true);

		rightMaster.setInverted(true);
		right1.changeControlMode(TalonControlMode.Follower);
		right1.set(rightMaster.getDeviceID());
		right2.changeControlMode(TalonControlMode.Follower);
		right2.set(rightMaster.getDeviceID());
		right1.reverseOutput(true);
		right2.reverseOutput(true);

		leftEncoder.setDistancePerPulse(INCHES_PER_ENCODER_COUNT);
		rightEncoder.setDistancePerPulse(INCHES_PER_ENCODER_COUNT);
		leftEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
		rightEncoder.setPIDSourceType(PIDSourceType.kDisplacement);

		leftEncoder.setSamplesToAverage(3);
		rightEncoder.setSamplesToAverage(3);
		rightEncoder.setReverseDirection(true);

		leftEncoderPID.setAbsoluteTolerance(ENCODER_DEADBAND_INCHES);
		rightEncoderPID.setAbsoluteTolerance(ENCODER_DEADBAND_INCHES);
		leftEncoderPID.setContinuous(false);
		rightEncoderPID.setContinuous(false);
		leftEncoderPID.setOutputRange(MIN_OUTPUT, MAX_OUTPUT);
		rightEncoderPID.setOutputRange(MIN_OUTPUT, MAX_OUTPUT);

		leftEncoderPID.setSetpoint(100);
		rightEncoderPID.setSetpoint(100);

		// Gyro
		gyro.initGyro();
		gyro.calibrate();
		gyro.setPIDSourceType(PIDSourceType.kDisplacement);
		gyro.setSensitivity(GYRO_VOLTS_PER_DEGREE_PER_SECOND);

		gyroPID.setAbsoluteTolerance(GYRO_DEADBAND_DEGREES);
		gyroPID.setContinuous(false);
		gyroPID.setOutputRange(MIN_OUTPUT, MAX_OUTPUT);
		gyroPID.enable();

		leftEncoderPID.enable();
		rightEncoderPID.enable();
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks());
	}

	public void tankDrive(double left, double right) {
		driveRawNoLimit(left, right);
	}

	public void driveRawNoLimit(double left, double right) {
		leftMaster.set(-left);
		rightMaster.set(-right);
	}

	public void driveRaw(double left, double right) {
		leftMaster.set(limit(-left));
		rightMaster.set(limit(-right));
	}

	private double limit(double speed) {
		return speed < MIN_OUTPUT ? MIN_OUTPUT : (speed > MAX_OUTPUT ? MAX_OUTPUT : speed);
	}

	private static void voidMethod(double d) {}

	@Override
	public void addToSmartDashboard(MySmartDashboard dashboard) {
		String directory = NAME + "/";

		// Left
		String leftDirectory = directory + "Left/";
		dashboard.addItem(SmartDashboardItem.newNumberSender(	leftDirectory + "Left Encoder Raw Counts",
																leftEncoder::getRaw));
		dashboard.addItem(SmartDashboardItem.newNumberSender(leftDirectory + "Left Encoder Counts", leftEncoder::get));
		dashboard.addItem(SmartDashboardItem.newNumberSender(	leftDirectory + "Left Encoder Distance",
																leftEncoder::getDistance));
		dashboard.addItem(SmartDashboardItem.newNumberSender(	leftDirectory + "Left Encoder Setpoint",
																leftEncoderPID::getSetpoint));
		dashboard.addItem(SmartDashboardItem.newNumberSender(leftDirectory + "Left Error", leftEncoderPID::getError));
		dashboard.addItem(SmartDashboardItem.newBooleanSender(	leftDirectory + "At left setpoint?",
																leftEncoderPID::onTarget));
		dashboard.addItem(SmartDashboardItem.newNumberSender(leftDirectory + "Left PID Output", leftEncoderPID::get));
		SmartDashboard.putData("Left PID", leftEncoderPID);

		// Right
		String rightDirectory = directory + "Right/";
		dashboard.addItem(SmartDashboardItem.newNumberSender(	rightDirectory + "Right Encoder Raw Counts",
																rightEncoder::getRaw));
		dashboard.addItem(SmartDashboardItem.newNumberSender(	rightDirectory + "Right Encoder Counts",
																rightEncoder::get));
		dashboard.addItem(SmartDashboardItem.newNumberSender(	rightDirectory + "Right Encoder Distance",
																rightEncoder::getDistance));
		dashboard.addItem(SmartDashboardItem.newNumberSender(	rightDirectory + "Right Encoder Setpoint",
																rightEncoderPID::getSetpoint));
		dashboard
				.addItem(SmartDashboardItem.newNumberSender(rightDirectory + "Right Error", rightEncoderPID::getError));
		dashboard.addItem(SmartDashboardItem.newBooleanSender(	rightDirectory + "At right setpoint?",
																rightEncoderPID::onTarget));
		dashboard
				.addItem(SmartDashboardItem.newNumberSender(rightDirectory + "Right PID Output", rightEncoderPID::get));
		SmartDashboard.putData("Right PID", rightEncoderPID);

		// Gyro
		String gyroDirectory = directory + "gyro/";
		dashboard.addItem(SmartDashboardItem.newNumberSender(gyroDirectory + "Gyro Degrees", gyro::getAngle));
		dashboard.addItem(SmartDashboardItem.newNumberSender(gyroDirectory + "Gyro Setpoint", gyroPID::getSetpoint));
		dashboard.addItem(SmartDashboardItem.newNumberSender(gyroDirectory + "Gyro Error", gyroPID::getError));
		dashboard.addItem(SmartDashboardItem.newBooleanSender(gyroDirectory + "At gyro setpoint?", gyroPID::onTarget));
		dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Gyro PID Output", gyroPID::get));
		SmartDashboard.putData("Gyro PID", gyroPID);
	}

	public void resetGyroPID() {
		gyroPID.setPID(gyroP, gyroI, gyroD);
	}

	public boolean isErrorNegative() {
		return driveTrain.rightEncoderPID.getError() < 0 && driveTrain.leftEncoderPID.getError() < 0;
	}

	public boolean encodersOnTarget() {
		return driveTrain.rightEncoderPID.onTarget() && driveTrain.leftEncoderPID.onTarget();
	}

}