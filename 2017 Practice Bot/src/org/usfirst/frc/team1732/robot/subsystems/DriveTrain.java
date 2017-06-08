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

/**
 * 
 */
public class DriveTrain extends Subsystem implements SmartDashboardGroup {

    public static final double RIGHT_PERCENTAGE_FORWARD = 1;
    public static final double RIGHT_PERCENTAGE_BACKWARD = 1;
    public static final double LEFT_PERCENTAGE_FORWARD = 1;// 0.95811863563969703302801219362789;
    public static final double LEFT_PERCENTAGE_BACKWARD = 1;// 0.90787825604332140794575823714271;
    // motors
    // left motors
    /**
     * The motor that the other left motors follow
     */
    private final CANTalon leftMaster = new CANTalon(RobotMap.LEFT_MASTER_MOTOR_DEVICE_NUMBER);
    private final CANTalon left1 = new CANTalon(RobotMap.LEFT_1_MOTOR_DEVICE_NUMBER);
    private final CANTalon left2 = new CANTalon(RobotMap.LEFT_2_MOTOR_DEVICE_NUMBER);
    // right motors
    /**
     * The motor the other right motors follow
     */
    private final CANTalon rightMaster = new CANTalon(RobotMap.RIGHT_MASTER_MOTOR_DEVICE_NUMBER);
    private final CANTalon right1 = new CANTalon(RobotMap.RIGHT_1_MOTOR_DEVICE_NUMBER);
    private final CANTalon right2 = new CANTalon(RobotMap.RIGHT_2_MOTOR_DEVICE_NUMBER);

    private final Solenoid shifter = new Solenoid(RobotMap.PCM_CAN_ID,
	    RobotMap.DRIVE_TRAIN_SHIFTER_SOLENOID_DEVICE_NUMBER);
    public static final boolean HIGH_GEAR = false;
    public static final boolean LOW_GEAR = !HIGH_GEAR;

    // gyro
    // gyro sensors
    private final AnalogGyro gyro = new AnalogGyro(RobotMap.GYRO_CHANNEL_NUMBER);
    public static final double GYRO_VOLTS_PER_DEGREE_PER_SECOND = 0.007;

    // gyro controllers
    private final PIDController gyroPID = new PIDController(gyroP, gyroI, gyroD, gyro, DriveTrain::voidMethod);
    public static final double GYRO_DEADBAND_DEGREES = 4; // 5
    public static final double gyroP = 0.0192; // 0.0085 // 0.025
    public static final double gyroI = 0; // 0.000005
    public static final double gyroD = 0.1;

    public static final double GYRO_IZONE = 25;
    public static final double GYRO_IZONE_I = -0.00032;
    // encoders
    // encoder sensors
    private final Encoder leftEncoder = new Encoder(RobotMap.LEFT_ENCODER_CHANNEL_A, RobotMap.LEFT_ENCODER_CHANNEL_B);
    private final Encoder rightEncoder = new Encoder(RobotMap.RIGHT_ENCODER_CHANNEL_A,
	    RobotMap.RIGHT_ENCODER_CHANNEL_B);
    public static final double INCHES_PER_ENCODER_COUNT = 44 / 5425.4;
    // 0.0134 * 4;
    // public static final double LEFT_MOTOR_OFFSET = 1.0;

    // encoder controllers
    private final PIDController leftEncoderPID = new PIDController(encoderP, encoderI, encoderD, leftEncoder,
	    DriveTrain::voidMethod);
    private final PIDController rightEncoderPID = new PIDController(encoderP, encoderI, encoderD, rightEncoder,
	    DriveTrain::voidMethod);
    public static final double encoderP = 0.03; // 0.02
    public static final double encoderI = 0;
    public static final double encoderD = 0;
    public static final double ENCODER_DEADBAND_INCHES = 3;
    public static final double errorDifferenceScalar = 0.035;

    public static final double ENCODER_IZONE = 20;
    public static final double ENCODER_IZONE_I = 0.0004;

    public static final double encoderTurningP = 0.055;
    public static final double encoderTurningI = 0;
    public static final double encoderTurningD = 0.04;
    public static final double ENCODER_TURNING_DEADBAND_INCHES = 0.75;

    public static final double ENCODER_IZONE_TURNING = 4;
    public static final double ENCODER_IZONE_TURNING_I = 0.001;

    // Min and max output
    public static final double ENCODER_MAX_OUTPUT = 1;
    public static final double ENCODER_MIN_OUTPUT = -ENCODER_MAX_OUTPUT;
    // public static final double ENCODER_NOMINAL_POSITIVE = 0.1;
    // public static final double ENCODER_NOMINAL_NEGATIVE =
    // -ENCODER_NOMINAL_POSITIVE;
    public static final double GYRO_MAX_OUTPUT = 0.8;
    public static final double GYRO_MIN_OUTPUT = -GYRO_MAX_OUTPUT;
    // public static final double MAX_OUTPUT = 0.5;
    // public static final double MIN_OUTPUT = -ENCODER_MAX_OUTPUT;

    // public static final double VOLTAGE_RAMP_RATE = 6;

    public static final double ROBOT_WIDTH_INCHES = 26;
    public static final double ROBOT_LENGTH_INCHES = 34.5;
    public static final double TURNING_CIRCUMFERENCE = Math.PI * ROBOT_WIDTH_INCHES;
    public static final double EFFECTIVE_TURNING_CIRCUMFERENCE = TURNING_CIRCUMFERENCE * 1;

    public static final String NAME = "Drive Train";

    public DriveTrain() {
	super(NAME);
	// reverses whole left side
	leftMaster.setInverted(true);
	left1.changeControlMode(TalonControlMode.Follower);
	left1.set(leftMaster.getDeviceID());
	left2.changeControlMode(TalonControlMode.Follower);
	left2.set(leftMaster.getDeviceID());

	// leftMaster.setVoltageRampRate(VOLTAGE_RAMP_RATE);
	// left1.setVoltageRampRate(VOLTAGE_RAMP_RATE);
	// left2.setVoltageRampRate(VOLTAGE_RAMP_RATE);
	// right1.setVoltageRampRate(VOLTAGE_RAMP_RATE);
	// right2.setVoltageRampRate(VOLTAGE_RAMP_RATE);
	// rightMaster.setVoltageRampRate(VOLTAGE_RAMP_RATE);

	// sets right motors to follow right master
	right1.changeControlMode(TalonControlMode.Follower);
	right1.set(rightMaster.getDeviceID());
	right2.changeControlMode(TalonControlMode.Follower);
	right2.set(rightMaster.getDeviceID());

	// makes sure braking is enabled
	setBrakeMode(true);

	// configures the inches per count of the encoders
	leftEncoder.setDistancePerPulse(INCHES_PER_ENCODER_COUNT);
	rightEncoder.setDistancePerPulse(INCHES_PER_ENCODER_COUNT);
	// configures the PID loops to use displacement (distance) rather than
	// speed
	leftEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
	rightEncoder.setPIDSourceType(PIDSourceType.kDisplacement);
	rightEncoder.setReverseDirection(true); // left
	// rightEncoder.setReverseDirection(true);

	// sets encoder samples to average
	leftEncoder.setSamplesToAverage(3);
	rightEncoder.setSamplesToAverage(3);
	// reverses the right encoder
	// rightEncoder.setReverseDirection(true);

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
	// sets the gyroPID to not measure continuously
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
	driveRawLimit(left, right, -1, 1);
	// driveRawWithRamp(left, right);
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

    public void driveRawWithRamp(double left, double right) {
	driveRawLimitWithRamp(left, right, -1, 1);
    }

    /**
     * Runs the driveTrain at voltages left and right with specified limits <br>
     * lower <= left, right <= upper
     * 
     * @param left
     *            left % voltage
     * @param right
     *            right % voltage
     * @param maximumNegative
     *            maximum negative % voltage
     * @param maximumPositive
     *            maximum positive % voltage
     */
    public void driveRawLimitWithRamp(double left, double right, double maximumNegative, double maximumPositive) {
	left = rampVoltage(prevLeft, limit(left, maximumNegative, maximumPositive));
	right = rampVoltage(prevRight, limit(right, maximumNegative, maximumPositive));
	prevLeft = left;
	prevRight = right;
	left *= left < 0 ? LEFT_PERCENTAGE_BACKWARD : LEFT_PERCENTAGE_FORWARD;
	right *= right < 0 ? RIGHT_PERCENTAGE_BACKWARD : RIGHT_PERCENTAGE_FORWARD;
	leftMaster.set(left);
	rightMaster.set(right);
    }

    public void driveRawLimit(double left, double right, double maximumNegative, double maximumPositive) {
	driveRawLimit(left, right, maximumNegative, 0, maximumPositive, 0);
    }

    public void driveRawLimit(double left, double right, double maximumNegative, double minimumNegative,
	    double maximumPositive, double minimumPositive) {
	// HAVE TO NEGATE MOTORS
	left = limit(-left, maximumNegative, minimumNegative, maximumPositive, minimumPositive);
	right = limit(-right, maximumNegative, minimumNegative, maximumPositive, minimumPositive);
	prevLeft = left;
	prevRight = right;
	left *= left < 0 ? LEFT_PERCENTAGE_BACKWARD : LEFT_PERCENTAGE_FORWARD;
	right *= right < 0 ? RIGHT_PERCENTAGE_BACKWARD : RIGHT_PERCENTAGE_FORWARD;
	leftMaster.set(left);
	rightMaster.set(right);
    }

    public void driveRawAbsoluteLimit(double left, double right, double absoluteMinimum, double absoluteMaximum) {
	driveRawLimit(left, right, -absoluteMaximum, -absoluteMinimum, absoluteMaximum, absoluteMinimum);
    }

    public String driveRawAbsLimit(double left, double right, double lower, double upper) {
	double leftDir = (left / Math.abs(left));
	double rightDir = (right / Math.abs(right));
	left = Math.abs(left) < lower ? leftDir * lower : Math.abs(left) > upper ? leftDir * upper : left;
	right = Math.abs(right) < rightDir * lower ? lower : Math.abs(right) > upper ? rightDir * upper : right;
	prevLeft = left;
	prevRight = right;
	left *= left < 0 ? LEFT_PERCENTAGE_BACKWARD : LEFT_PERCENTAGE_FORWARD;
	right *= right < 0 ? RIGHT_PERCENTAGE_BACKWARD : RIGHT_PERCENTAGE_FORWARD;
	leftMaster.set(left);
	rightMaster.set(right);
	return right + " " + left;
    }

    private double prevLeft = 0;
    private double prevRight = 0;

    public static final double RAMP_RATE = 0.2;
    public static final double MIN_RAMP_LEVEL = 0.5;

    public double rampVoltage(double prev, double requested) {
	if (isHighGear() && Math.abs(requested) > MIN_RAMP_LEVEL && Math.abs(prev - requested) > RAMP_RATE) {
	    return RAMP_RATE * Math.signum(prev) + prev;
	} else {
	    return requested;
	}
    }

    public static final double ENCODER_RAMP_RATE = 0;
    public static final double MAX_INCHES_PER_SECOND = 40;

    public double rampWithEncoders(double prev, double requested, Encoder encoder) {
	if (isHighGear() && Math.abs(requested) > MIN_RAMP_LEVEL
		&& Math.abs(encoder.getRate()) < MAX_INCHES_PER_SECOND) {
	    return prev + RAMP_RATE * Math.signum(prev);
	} else {
	    return requested;
	}
    }

    /**
     * Limits the value so that lower <= speed <= upper
     * 
     * @param value
     *            input value to limit
     * @param maximumNegative
     *            the lower limit
     * @param maximumPositive
     *            the upper limit
     * @return the limited value
     */
    private double limit(double value, double maximumNegative, double maximumPositive) {
	return limit(value, maximumNegative, 0, maximumPositive, 0);
    }

    @SuppressWarnings("unused")
    private double limitAbsolute(double value, double absoluteMinimum, double absoluteMaximum) {
	return limit(value, -absoluteMaximum, -absoluteMinimum, absoluteMaximum, absoluteMinimum);
    }

    private double limit(double value, double maximumNegative, double minimumNegative, double maximumPositive,
	    double minimumPositive) {
	if (value < 0) {
	    if (value < maximumNegative)
		return maximumNegative;
	    else if (value > minimumNegative)
		return minimumNegative;
	    else
		return value;
	} else {
	    if (value > maximumPositive)
		return maximumPositive;
	    else if (value < minimumPositive)
		return minimumPositive;
	    else
		return value;
	}
    }

    /**
     * Void method to supply PIDloops with a PIDOutput object easily
     * 
     * @param d
     *            input double to method to fulfill requirements of PIDOutput
     *            interface
     */
    private static void voidMethod(double d) {
    }

    private SmartDashboardItem<Double> gyroISmartDashboard;
    @SuppressWarnings("unused")
    private SmartDashboardItem<Double> gyroPSmartDashboard;
    @SuppressWarnings("unused")
    private SmartDashboardItem<Double> gyroDSmartDashboard;

    public double getSmartDashboardGyroI() {
	if (gyroISmartDashboard != null) {
	    return gyroISmartDashboard.getValue();
	} else
	    return gyroI;
    }

    @Override
    public void addToSmartDashboard(MySmartDashboard dashboard) {
	String directory = NAME + "/";

	// Left
	String leftDirectory = directory + "Left/";
	dashboard.addItem(
		SmartDashboardItem.newNumberSender(leftDirectory + "Left Encoder Raw Counts", leftEncoder::getRaw));
	dashboard.addItem(SmartDashboardItem.newNumberSender(leftDirectory + "Left Encoder Counts", leftEncoder::get));
	dashboard.addItem(
		SmartDashboardItem.newNumberSender(leftDirectory + "Left Encoder Distance", leftEncoder::getDistance));
	dashboard.addItem(SmartDashboardItem.newNumberSender(leftDirectory + "Left Encoder Setpoint",
		leftEncoderPID::getSetpoint));
	dashboard.addItem(SmartDashboardItem.newNumberSender(leftDirectory + "Left Error", leftEncoderPID::getError));
	dashboard.addItem(
		SmartDashboardItem.newBooleanSender(leftDirectory + "At left setpoint?", leftEncoderPID::onTarget));
	dashboard.addItem(SmartDashboardItem.newNumberSender(leftDirectory + "Left PID Output", leftEncoderPID::get));
	dashboard.addItem(SmartDashboardItem.newNumberSender(leftDirectory + "Left Percent Volts", () -> prevLeft));
	dashboard.addItem(
		SmartDashboardItem.newNumberSender(leftDirectory + "Left Voltage", leftMaster::getOutputVoltage));

	// SmartDashboard.putData("Left PID", leftEncoderPID);

	// Right
	String rightDirectory = directory + "Right/";
	dashboard.addItem(
		SmartDashboardItem.newNumberSender(rightDirectory + "Right Encoder Raw Counts", rightEncoder::getRaw));
	dashboard.addItem(
		SmartDashboardItem.newNumberSender(rightDirectory + "Right Encoder Counts", rightEncoder::get));
	dashboard.addItem(SmartDashboardItem.newNumberSender(rightDirectory + "Right Encoder Distance",
		rightEncoder::getDistance));
	dashboard.addItem(SmartDashboardItem.newNumberSender(rightDirectory + "Right Encoder Setpoint",
		rightEncoderPID::getSetpoint));
	dashboard
		.addItem(SmartDashboardItem.newNumberSender(rightDirectory + "Right Error", rightEncoderPID::getError));
	dashboard.addItem(
		SmartDashboardItem.newBooleanSender(rightDirectory + "At right setpoint?", rightEncoderPID::onTarget));
	dashboard
		.addItem(SmartDashboardItem.newNumberSender(rightDirectory + "Right PID Output", rightEncoderPID::get));
	dashboard.addItem(SmartDashboardItem.newNumberSender(rightDirectory + "Right Percent Volts", () -> prevRight));
	dashboard.addItem(
		SmartDashboardItem.newNumberSender(rightDirectory + "Right Voltage", rightMaster::getOutputVoltage));
	// SmartDashboard.putData("Right PID", rightEncoderPID);

	String gyroDirectory = directory + "Gyro/";
	gyroISmartDashboard = dashboard
		.addItem(SmartDashboardItem.newDoubleReciever(gyroDirectory + "Gyro I", gyroI, this::setGyroI));
	gyroPSmartDashboard = dashboard
		.addItem(SmartDashboardItem.newDoubleReciever(gyroDirectory + "Gyro P", gyroP, this::setGyroP));
	gyroDSmartDashboard = dashboard
		.addItem(SmartDashboardItem.newDoubleReciever(gyroDirectory + "Gyro D", gyroD, this::setGyroD));
	// Gyro
	dashboard.addItem(SmartDashboardItem.newNumberSender(gyroDirectory + "Gyro Degrees", gyro::getAngle));
	dashboard.addItem(SmartDashboardItem.newNumberSender(gyroDirectory + "Gyro Setpoint", gyroPID::getSetpoint));
	dashboard.addItem(SmartDashboardItem.newNumberSender(gyroDirectory + "Gyro Error", gyroPID::getError));
	dashboard.addItem(SmartDashboardItem.newBooleanSender(gyroDirectory + "At gyro setpoint?", gyroPID::onTarget));
	dashboard.addItem(SmartDashboardItem.newNumberSender(gyroDirectory + "Gyro PID Output", gyroPID::get));
	// SmartDashboard.putData("Gyro PID", gyroPID);
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
     * Total distance the robot has traveled (only incremented on reset of
     * encoders)
     */
    private double leftDistanceTraveled = 0;
    private double rightDistanceTraveled = 0;

    /**
     * @return the total distance the left encoder has traveled
     */
    public double getTotalLeftDistance() {
	return leftDistanceTraveled + leftEncoder.getDistance();
    }

    /**
     * @return the total distance the right encoder has traveled
     */
    public double getTotalRightDistance() {
	return rightDistanceTraveled + rightEncoder.getDistance();
    }

    /**
     * Clears the distance traveled
     */
    public void clearTotalDistance() {
	leftDistanceTraveled = 0;
	rightDistanceTraveled = 0;
    }

    /**
     * Zeros the encoders so that the current distance is 0 Also adds to the
     * distance travled variables
     */
    public void resetEncoders() {
	leftDistanceTraveled += leftEncoder.getDistance();
	rightDistanceTraveled += rightEncoder.getDistance();
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
     * Gets the gyro's angle
     * 
     * @return the gyro angle in degreees
     */
    public double getAngle() {
	return gyro.getAngle();
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

    public boolean encodersOnTarget(double offset) {
	return Math.abs(leftEncoderPID.getError()) - offset < ENCODER_DEADBAND_INCHES
		&& Math.abs(rightEncoderPID.getError()) - offset < ENCODER_DEADBAND_INCHES;
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

    public void setEncoderPIDS(double p, double i, double d) {
	leftEncoderPID.setPID(p, i, d);
	rightEncoderPID.setPID(p, i, d);
    }

    public void setGyroPIDS(double p, double i, double d) {
	gyroPID.setPID(p, i, d);
    }

    public void setGyroI(double i) {
	gyroPID.setPID(gyroPID.getP(), i, gyroPID.getD());
    }

    public void setGyroP(double i) {
	gyroPID.setPID(i, gyroPID.getI(), gyroPID.getD());
    }

    public void setGyroD(double i) {
	gyroPID.setPID(gyroPID.getP(), gyroPID.getI(), i);
    }

    public void setEncoderDeadband(double d) {
	leftEncoderPID.setAbsoluteTolerance(d);
	rightEncoderPID.setAbsoluteTolerance(d);
    }

    public void resetEncoderDeadband() {
	leftEncoderPID.setAbsoluteTolerance(ENCODER_DEADBAND_INCHES);
	rightEncoderPID.setAbsoluteTolerance(ENCODER_DEADBAND_INCHES);
    }

    public double getGyroError() {
	return gyroPID.getError();
    }

    public double getLeftPIDError() {
	return leftEncoderPID.getError();
    }

    public double getRightPIDError() {
	return rightEncoderPID.getError();
    }

    public double getLeftRightAdjustment() {
	return (leftEncoderPID.getError() - rightEncoderPID.getError()) * errorDifferenceScalar;
    }

    public void setEncoderToTurningPID() {
	leftEncoderPID.setPID(encoderTurningP, encoderTurningI, encoderTurningD);
	rightEncoderPID.setPID(encoderTurningP, encoderTurningI, encoderTurningD);
    }

    public double getLeftVelocity() {
	return leftEncoder.getRate();
    }

    public double getRightVelocity() {
	return rightEncoder.getRate();
    }

    public void resetEncoderPID() {
	leftEncoderPID.setPID(encoderP, encoderI, encoderD);
	rightEncoderPID.setPID(encoderP, encoderI, encoderD);
    }

}