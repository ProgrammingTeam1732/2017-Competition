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
	private final AnalogGyro	gyro								= new AnalogGyro(RobotMap.GYRO_CHANNEL_NUMBER);
	public static final double	GYRO_VOLTS_PER_DEGREE_PER_SECOND	= 0.007;

	// gyro controllers
	private final PIDController	gyroPID					= new PIDController(gyroP, gyroI, gyroD, gyro,
																			DriveTrain::voidMethod);
	public static final double	GYRO_DEADBAND_DEGREES	= 6;
	public static final double	gyroP					= 0.013;
	public static final double	gyroI					= 0.00001;
	public static final double	gyroD					= 0;

	// encoders
	// encoder sensors
	private final Encoder		leftEncoder					= new Encoder(	RobotMap.LEFT_ENCODER_CHANNEL_A,
																			RobotMap.LEFT_ENCODER_CHANNEL_B);
	private final Encoder		rightEncoder				= new Encoder(	RobotMap.RIGHT_ENCODER_CHANNEL_A,
																			RobotMap.RIGHT_ENCODER_CHANNEL_B);
	public static final double	INCHES_PER_ENCODER_COUNT	= 0.0134 * 4;
	public static final double	LEFT_MOTOR_OFFSET			= 1.1;

	// encoder controllers
	private final PIDController	leftEncoderPID			= new PIDController(encoderP, encoderI, encoderD, leftEncoder,
																			DriveTrain::voidMethod);
	private final PIDController	rightEncoderPID			= new PIDController(encoderP, encoderI, encoderD, rightEncoder,
																			DriveTrain::voidMethod);
	public static final double	encoderP				= 0.03;
	public static final double	encoderI				= 0;
	public static final double	encoderD				= 0;
	public static final double	ENCODER_DEADBAND_INCHES	= 6;

	// Min and max output
	public static final double	ENCODER_MAX_OUTPUT	= 0.5;
	public static final double	ENCODER_MIN_OUTPUT	= -ENCODER_MAX_OUTPUT;
	public static final double	GYRO_MAX_OUTPUT		= 1;
	public static final double	GYRO_MIN_OUTPUT		= -GYRO_MAX_OUTPUT;
	// public static final double MAX_OUTPUT = 0.5;
	// public static final double MIN_OUTPUT = -ENCODER_MAX_OUTPUT;

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
		setBrakeMode(true);

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
		leftEncoderPID.setOutputRange(ENCODER_MIN_OUTPUT, ENCODER_MAX_OUTPUT);
		rightEncoderPID.setOutputRange(ENCODER_MIN_OUTPUT, ENCODER_MAX_OUTPUT);

		// Gyro
		gyro.initGyro();
		gyro.calibrate();
		gyro.setPIDSourceType(PIDSourceType.kDisplacement);
		gyro.setSensitivity(GYRO_VOLTS_PER_DEGREE_PER_SECOND);

		gyroPID.setAbsoluteTolerance(GYRO_DEADBAND_DEGREES);
		gyroPID.setContinuous(false);
		gyroPID.setOutputRange(GYRO_MIN_OUTPUT, GYRO_MAX_OUTPUT);
		gyroPID.enable();

		leftEncoderPID.enable();
		rightEncoderPID.enable();
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks());
	}

	public void tankDrive(double left, double right) {
		driveRaw(left, right);
	}

	public void driveRaw(double left, double right) {
		driveRawLimit(left, right, -1, 1);
	}

	public void driveRawLimit(double left, double right, double lower, double upper) {
		leftMaster.set(limit(-left, lower, upper));
		rightMaster.set(limit(-right, lower, upper));
	}

	private double limit(double speed, double lower, double upper) {
		return speed < lower ? lower : (speed > upper ? upper : speed);
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
		String gyroDirectory = directory + "Gyro/";
		dashboard.addItem(SmartDashboardItem.newNumberSender(gyroDirectory + "Gyro Degrees", gyro::getAngle));
		dashboard.addItem(SmartDashboardItem.newNumberSender(gyroDirectory + "Gyro Setpoint", gyroPID::getSetpoint));
		dashboard.addItem(SmartDashboardItem.newNumberSender(gyroDirectory + "Gyro Error", gyroPID::getError));
		dashboard.addItem(SmartDashboardItem.newBooleanSender(gyroDirectory + "At gyro setpoint?", gyroPID::onTarget));
		dashboard.addItem(SmartDashboardItem.newNumberSender(gyroDirectory + "Gyro PID Output", gyroPID::get));
		SmartDashboard.putData("Gyro PID", gyroPID);
	}

	public void resetGyroPIDValues() {
		gyroPID.setPID(gyroP, gyroI, gyroD);
	}

	public boolean isErrorNegative() {
		return driveTrain.rightEncoderPID.getError() < 0 && driveTrain.leftEncoderPID.getError() < 0;
	}

	public void resetEncoderPIDValues() {
		leftEncoderPID.setPID(encoderP, encoderI, encoderD);
		rightEncoderPID.setPID(encoderP, encoderI, encoderD);
	}

	public void setBrakeMode(boolean brake) {
		rightMaster.enableBrakeMode(brake);
		right1.enableBrakeMode(brake);
		right2.enableBrakeMode(brake);
		leftMaster.enableBrakeMode(brake);
		left1.enableBrakeMode(brake);
		left2.enableBrakeMode(brake);
	}

	public void clearGyroIntgral() {
		gyroPID.reset();
		gyroPID.enable();
	}

	public void clearEncoderIntgral() {
		leftEncoderPID.reset();
		leftEncoderPID.enable();
		rightEncoderPID.reset();
		rightEncoderPID.enable();
	}

	public void setLeftEncoderSetpoint(double setpoint) {
		leftEncoderPID.setSetpoint(setpoint);
	}

	public void setRightEncoderSetpoint(double setpoint) {
		rightEncoderPID.setSetpoint(setpoint);
	}

	public void setEncoderSetpoint(double setpoint) {
		leftEncoderPID.setSetpoint(setpoint);
		rightEncoderPID.setSetpoint(setpoint);
	}

	public void setGyroSetpoint(double angle) {
		gyroPID.setSetpoint(angle);
	}

	public double getLeftPIDOutput() {
		return leftEncoderPID.get();
	}

	public double getRightPIDOutput() {
		return rightEncoderPID.get();
	}

	public double getGyroPIDOutput() {
		return gyroPID.get();
	}

	public void resetGyro() {
		gyro.reset();
	}

	public void resetEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
	}

	public double getLeftDistance() {
		return leftEncoder.getDistance();
	}

	public double getRightDistance() {
		return rightEncoder.getDistance();
	}

	public boolean leftEncoderOnTarget() {
		return leftEncoderPID.onTarget();
	}

	public boolean rightEncoderOnTarget() {
		return leftEncoderPID.onTarget();
	}

	public boolean encodersOnTarget() {
		return leftEncoderOnTarget() && rightEncoderOnTarget();
	}

	public boolean gyroOnTarget() {
		return gyroPID.onTarget();
	}

}