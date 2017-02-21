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
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 */
public class DriveTrain extends Subsystem implements SmartDashboardGroup {

	// motors
	// left motors
	/**
	 * The motor that the other left motors follow
	 */
	private final CANTalon	leftMaster	= new CANTalon(RobotMap.LEFT_MASTER_MOTOR_DEVICE_NUMBER);
	private final CANTalon	left1		= new CANTalon(RobotMap.LEFT_1_MOTOR_DEVICE_NUMBER);
	private final CANTalon	left2		= new CANTalon(RobotMap.LEFT_2_MOTOR_DEVICE_NUMBER);
	// right motors
	/**
	 * The motor the other right motors follow
	 */
	private final CANTalon	rightMaster	= new CANTalon(RobotMap.RIGHT_MASTER_MOTOR_DEVICE_NUMBER);
	private final CANTalon	right1		= new CANTalon(RobotMap.RIGHT_1_MOTOR_DEVICE_NUMBER);
	private final CANTalon	right2		= new CANTalon(RobotMap.RIGHT_2_MOTOR_DEVICE_NUMBER);

	private final Solenoid		shifter		= new Solenoid(RobotMap.DRIVE_TRAIN_SHIFTER_SOLENOID_DEVICE_NUMBER);
	public static final boolean	HIGH_GEAR	= true;
	public static final boolean	LOW_GEAR	= !HIGH_GEAR;

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
		// sets the left motors to follow left master
		leftMaster.setInverted(true);
		left1.changeControlMode(TalonControlMode.Follower);
		left1.set(leftMaster.getDeviceID());
		left2.changeControlMode(TalonControlMode.Follower);
		left2.set(leftMaster.getDeviceID());
		// reverse the slave motors

		// reverse the whole right side
		// sets right motors to follow right master
		right1.changeControlMode(TalonControlMode.Follower);
		right1.set(rightMaster.getDeviceID());
		right2.changeControlMode(TalonControlMode.Follower);
		right2.set(rightMaster.getDeviceID());
		// reverses the slave motors

		// makes sure braking is enabled
		setBrakeMode(true);

		// configures the inches per count of the encoders
		leftEncoder.setDistancePerPulse(INCHES_PER_ENCODER_COUNT);
		rightEncoder.setDistancePerPulse(INCHES_PER_ENCODER_COUNT);
		// configures the PID loops to use displacement (distance) rather than
		// speed
		leftEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
		rightEncoder.setPIDSourceType(PIDSourceType.kDisplacement);

		// sets encoder samples to average
		leftEncoder.setSamplesToAverage(3);
		rightEncoder.setSamplesToAverage(3);
		// reverses the right encoder
		rightEncoder.setReverseDirection(true);

		// sets the tolerance of the encoders
		leftEncoderPID.setAbsoluteTolerance(ENCODER_DEADBAND_INCHES);
		rightEncoderPID.setAbsoluteTolerance(ENCODER_DEADBAND_INCHES);
		// sets the encoders to not be continious
		leftEncoderPID.setContinuous(false);
		rightEncoderPID.setContinuous(false);
		// sets the minimum/maximum PID loop output
		leftEncoderPID.setOutputRange(ENCODER_MIN_OUTPUT, ENCODER_MAX_OUTPUT);
		rightEncoderPID.setOutputRange(ENCODER_MIN_OUTPUT, ENCODER_MAX_OUTPUT);

		// Gyro
		// not actually needed, this gets done in the gyro constructor
		// gyro.initGyro();
		// gyro.calibrate();
		// sets the gyro to measure angle not rate of angle change
		gyro.setPIDSourceType(PIDSourceType.kDisplacement);
		// sets the sensitivity of the gyro
		gyro.setSensitivity(GYRO_VOLTS_PER_DEGREE_PER_SECOND);

		// sets the tolerance of the gyroPID
		gyroPID.setAbsoluteTolerance(GYRO_DEADBAND_DEGREES);
		// sets the gyroPID to not measure continiously
		gyroPID.setContinuous(false);
		// sets the minimum/maximum PID loop output
		gyroPID.setOutputRange(GYRO_MIN_OUTPUT, GYRO_MAX_OUTPUT);

		shiftHighGear();

		// turns on the gyro and encoders PID loops
		gyroPID.enable();
		leftEncoderPID.enable();
		rightEncoderPID.enable();
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks());
	}

	/**
	 * Runs the driveTrain at voltages left and right with no limit using tank
	 * drive method<br>
	 * Intended for use with the joystick input (allows for more flexibility
	 * rather than just using driveRaw)
	 * 
	 * @param left
	 *            left % voltage
	 * @param right
	 *            right % voltage
	 */
	public void driveWithJoysticks(double left, double right) {
		tankDrive(left, right);
		// we could change this to other things if we wanted, like
		// "driveMechanum" or "driveArcade" without having to change code in
		// other places
	}

	/**
	 * Intermediate method in case we want to change the behavior of tank drive
	 * 
	 * @param left
	 *            left % voltage
	 * @param right
	 *            right % voltage
	 */
	private void tankDrive(double left, double right) {
		driveRaw(left, right);
	}

	/**
	 * Runs the driveTrain at voltages left and right with no limit
	 * 
	 * @param left
	 *            left % voltage
	 * @param right
	 *            right % voltage
	 */
	public void driveRaw(double left, double right) {
		driveRawLimit(left, right, -1, 1);
	}

	/**
	 * Runs the driveTrain at voltages left and right with specified limits <br>
	 * lower <= left, right <= upper
	 * 
	 * @param left
	 *            left % voltage
	 * @param right
	 *            right % voltage
	 * @param lower
	 *            maximum negative % voltage
	 * @param upper
	 *            maximum positive % voltage
	 */
	public void driveRawLimit(double left, double right, double lower, double upper) {
		leftMaster.set(limit(-left, lower, upper));
		rightMaster.set(limit(-right, lower, upper));
	}

	/**
	 * Limits the value so that lower <= speed <= upper
	 * 
	 * @param value
	 *            input value to limit
	 * @param lower
	 *            the lower limit
	 * @param upper
	 *            the upper limit
	 * @return the limited value
	 */
	private double limit(double value, double lower, double upper) {
		return value < lower ? lower : (value > upper ? upper : value);
	}

	/**
	 * Void method to supply PIDloops with a PIDOutput object easily
	 * 
	 * @param d
	 *            input double to method to fulfill requirements of PIDOutput
	 *            interface
	 */
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

	/**
	 * Resets the gyro's PID values to the defaults saved in the code
	 */
	public void resetGyroPIDValues() {
		gyroPID.setPID(gyroP, gyroI, gyroD);
	}

	/**
	 * Checks if the encoder PID error is negative
	 * 
	 * @return if the right AND left encoder PID error is negative
	 */
	public boolean isErrorNegative() {
		return driveTrain.rightEncoderPID.getError() < 0 && driveTrain.leftEncoderPID.getError() < 0;
	}

	/**
	 * * Resets the left and right encoders' PID values to the defaults saved in
	 * the code
	 * 
	 */
	public void resetEncoderPIDValues() {
		leftEncoderPID.setPID(encoderP, encoderI, encoderD);
		rightEncoderPID.setPID(encoderP, encoderI, encoderD);
	}

	/**
	 * Sets the brake mode of the drive train.<br>
	 * The brake mode is what happens when the motor input voltage is 0 (either
	 * coast or brake)
	 * 
	 * @param brake
	 *            the brake mode of the drive train, false to coast, true to
	 *            brake
	 */
	public void setBrakeMode(boolean brake) {
		rightMaster.enableBrakeMode(brake);
		right1.enableBrakeMode(brake);
		right2.enableBrakeMode(brake);
		leftMaster.enableBrakeMode(brake);
		left1.enableBrakeMode(brake);
		left2.enableBrakeMode(brake);
	}

	/**
	 * Clears the integral buildup of the gyro PID <br>
	 * Happens automatically when within the deadband
	 */
	public void clearGyroIntgral() {
		gyroPID.reset();
		gyroPID.enable();
	}

	/**
	 * Clears the integral buildup of the encoder PIDs <br>
	 * Happens automatically when within the deadband
	 */
	public void clearEncoderIntgral() {
		leftEncoderPID.reset();
		leftEncoderPID.enable();
		rightEncoderPID.reset();
		rightEncoderPID.enable();
	}

	/**
	 * Sets the left encoder setpoint in inches
	 * 
	 * @param setpoint
	 *            the setpoint in inches
	 */
	public void setLeftEncoderSetpoint(double setpoint) {
		leftEncoderPID.setSetpoint(setpoint);
	}

	/**
	 * Sets the right encoder setpoint in inches
	 * 
	 * @param setpoint
	 *            the setpoint in inches
	 */
	public void setRightEncoderSetpoint(double setpoint) {
		rightEncoderPID.setSetpoint(setpoint);
	}

	/**
	 * Sets the both encoders' setpoint in inches
	 * 
	 * @param setpoint
	 *            the setpoint in inches
	 */
	public void setEncoderSetpoint(double setpoint) {
		leftEncoderPID.setSetpoint(setpoint);
		rightEncoderPID.setSetpoint(setpoint);
	}

	/**
	 * Sets the gyro setpoint in degrees
	 * 
	 * @param setpoint
	 *            the setpoint in degrees
	 */
	public void setGyroSetpoint(double angle) {
		gyroPID.setSetpoint(angle);
	}

	/**
	 * Gets the left Encoder PID output
	 * 
	 * @return the left Encoder PID output (constrained by the min and max set
	 *         in code)
	 */
	public double getLeftPIDOutput() {
		return leftEncoderPID.get();
	}

	/**
	 * Gets the right Encoder PID output
	 * 
	 * @return the right Encoder PID output (constrained by the min and max set
	 *         in code)
	 */
	public double getRightPIDOutput() {
		return rightEncoderPID.get();
	}

	/**
	 * Gets the gyro PID output
	 * 
	 * @return the gyro PID output (constrained by the min and max set in code)
	 */
	public double getGyroPIDOutput() {
		return gyroPID.get();
	}

	/**
	 * Zeros the gyro so that the current angle is 0
	 */
	public void resetGyro() {
		gyro.reset();
	}

	/**
	 * Zeros the encoders so that the current distance is 0
	 */
	public void resetEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
	}

	/**
	 * Gets the left encoder's distance
	 * 
	 * @return distance in inches measured by the left encoder
	 */
	public double getLeftDistance() {
		return leftEncoder.getDistance();
	}

	/**
	 * Gets the right encoder's distance
	 * 
	 * @return distance in inches measured by the right encoder
	 */
	public double getRightDistance() {
		return rightEncoder.getDistance();
	}

	/**
	 * Checks if the left encoder is within the deadband of the setpoint
	 * 
	 * @return if the left encoder is within the deadband of the setpoint
	 */
	public boolean leftEncoderOnTarget() {
		return leftEncoderPID.onTarget();
	}

	/**
	 * Checks if the right encoder is within the deadband of the setpoint
	 * 
	 * @return if the right encoder is within the deadband of the setpoint
	 */
	public boolean rightEncoderOnTarget() {
		return leftEncoderPID.onTarget();
	}

	/**
	 * Checks if the left AND right encoders are within the deadband of the
	 * setpoint
	 * 
	 * @return if the left AND right encoders are within the deadband of the
	 *         setpoint
	 */
	public boolean encodersOnTarget() {
		return leftEncoderOnTarget() && rightEncoderOnTarget();
	}

	/**
	 * Checks if the gyro is within the deadband of the setpoint
	 * 
	 * @return if the gyro is within the deadband of the setpoint
	 */
	public boolean gyroOnTarget() {
		return gyroPID.onTarget();
	}

	public void shiftHighGear() {
		shifter.set(HIGH_GEAR);
	}

	public void shiftLowGear() {
		shifter.set(LOW_GEAR);
	}

	public boolean isHighGear() {
		return shifter.get() == HIGH_GEAR;
	}

	public boolean isLowGear() {
		return shifter.get() == LOW_GEAR;
	}

	/**
	 * Runs individual motors for testing
	 */

	public void runMotorLtBottom(int speed) {
		leftMaster.set(speed);
	}

	public void runMotorLtBack(int speed) {
		left1.set(speed);
	}

	public void runMotorLtFront(int speed) {
		left2.set(speed);
	}

	public void runMotorRtBottom(int speed) {
		rightMaster.set(speed);
	}

	public void runMotorRtBack(int speed) {
		right1.set(speed);
	}

	public void runMotorRtFront(int speed) {
		right2.set(speed);
	}

}