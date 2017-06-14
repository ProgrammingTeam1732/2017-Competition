package org.usfirst.frc.team1732.robot.subsystems.drivetrain;

import org.usfirst.frc.team1732.robot.RobotMap;
import org.usfirst.frc.team1732.robot.commands.drivetrain.drive.DriveWithJoysticks;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardGroup;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem implements SmartDashboardGroup {

    public static final String NAME = "Drive Train";

    public static final double WHEEL_DIAMETER = 3.911;
    public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;
    public static final double ROBOT_WIDTH_INCHES = 26;
    public static final double ROBOT_LENGTH_INCHES = 34.5;
    public static final double TURNING_CIRCUMFERENCE = Math.PI * ROBOT_WIDTH_INCHES;
    public static final double EFFECTIVE_TURNING_CIRCUMFERENCE = TURNING_CIRCUMFERENCE * 1;

    public static final double RIGHT_PERCENTAGE_FORWARD = 1;// 0.968;
    public static final double RIGHT_PERCENTAGE_BACKWARD = 1;
    public static final double LEFT_PERCENTAGE_FORWARD = 1;// 0.978;
    public static final double LEFT_PERCENTAGE_BACKWARD = 1;

    public static final int ENCODER_CODES_PER_REV = 1365;
    public static final double INCHES_PER_ENCODER_COUNT = 44 / 5425.4;
    public static final boolean REVERSE_LEFT_ENCODER = true;
    public static final boolean REVERSE_RIGHT_ENCODER = false;

    public static final double ERROR_DIFFERENCE_SCALAR = 0.045; // 0.035

    public static final double PID_MAX_OUTPUT = 1;
    public static final double PID_MIN_OUTPUT = -PID_MAX_OUTPUT;

    public static final double MAX_LEFT_VEL = 150;
    public static final double MAX_RIGHT_VEL = 140;
    public static final double MAX_LEFT_RPM = 0;
    public static final double MAX_RIGHT_RPM = 0;
    public static final double DEFAULT_ACCELERATION = 0;
    public static final double DEFAULT_VELOCITY = 0;

    public static final boolean HIGH_GEAR = false; // false
    public static final boolean LOW_GEAR = !HIGH_GEAR;

    private final Solenoid shifter = new Solenoid(RobotMap.PCM_CAN_ID,
	    RobotMap.DRIVE_TRAIN_SHIFTER_SOLENOID_DEVICE_NUMBER);

    // master is the motor that the other left motors follow
    private final CANTalon leftMaster = new CANTalon(RobotMap.LEFT_MASTER_MOTOR_DEVICE_NUMBER);
    private final CANTalon left1 = new CANTalon(RobotMap.LEFT_1_MOTOR_DEVICE_NUMBER);
    private final CANTalon left2 = new CANTalon(RobotMap.LEFT_2_MOTOR_DEVICE_NUMBER);
    // right motors
    // master is the motor the other right motors follow
    private final CANTalon rightMaster = new CANTalon(RobotMap.RIGHT_MASTER_MOTOR_DEVICE_NUMBER);
    private final CANTalon right1 = new CANTalon(RobotMap.RIGHT_1_MOTOR_DEVICE_NUMBER);
    private final CANTalon right2 = new CANTalon(RobotMap.RIGHT_2_MOTOR_DEVICE_NUMBER);

    public final MyEncoder leftEncoder = new RoborioEncoder(RobotMap.LEFT_ENCODER_CHANNEL_A,
	    RobotMap.LEFT_ENCODER_CHANNEL_B, INCHES_PER_ENCODER_COUNT, REVERSE_LEFT_ENCODER);
    public final MyEncoder rightEncoder = new RoborioEncoder(RobotMap.RIGHT_ENCODER_CHANNEL_A,
	    RobotMap.RIGHT_ENCODER_CHANNEL_B, INCHES_PER_ENCODER_COUNT, REVERSE_RIGHT_ENCODER);

    // public final MyEncoder leftEncoder = new CANTalonEncoder(leftMaster,
    // ENCODER_CODES_PER_REV, WHEEL_CIRCUMFERENCE,
    // REVERSE_LEFT_ENCODER);
    // public final MyEncoder rightEncoder = new CANTalonEncoder(rightMaster,
    // ENCODER_CODES_PER_REV, WHEEL_CIRCUMFERENCE,
    // REVERSE_RIGHT_ENCODER);

    public static final PIDData normalPID = new PIDData(0.03, 0.0004, 0, 0, 20, 3, PID_MAX_OUTPUT, PID_MIN_OUTPUT);
    public static final PIDData turningPID = new PIDData(0.055, 0.001, 0.04, 0, 4, 0.75, PID_MAX_OUTPUT,
	    PID_MIN_OUTPUT);

    public final CombinedController mainController = new CombinedController("Encoder Controller",
	    leftEncoder::getDistance, rightEncoder::getDistance, normalPID);

    public static final PIDData leftVelPIDValues = new PIDData(0.5, 0, 0, 1.0 / MAX_LEFT_VEL, 0, 5, PID_MAX_OUTPUT,
	    PID_MIN_OUTPUT);
    public static final PIDData rightVelPIDValues = leftVelPIDValues.changeF(1.0 / MAX_RIGHT_VEL);

    public final CombinedController velocityController = new CombinedController("Velocity Controller",
	    leftEncoder::getRate, rightEncoder::getRate, leftVelPIDValues, rightVelPIDValues);

    public static final PIDData positionPIDValues = new PIDData(0.1, 0, 0, 0, 0, 3, 144, -144);
    public final CombinedController positionController = new CombinedController("Position Controller",
	    leftEncoder::getDistance, rightEncoder::getDistance, positionPIDValues);

    private final AnalogGyro gyro = new AnalogGyro(RobotMap.GYRO_CHANNEL_NUMBER);

    public static final double GYRO_VOLTS_PER_DEGREE_PER_SECOND = 0.007;

    public static final PIDData normalGyroPID = new PIDData(0.025, 0.00032, 0.1, 0, 25, 5, 0.8, -0.8);
    public final ControllerUnit gyroController = new ControllerUnit("Gyro Controller", gyro::getAngle, normalGyroPID);

    public DriveTrain() {
	super(NAME);
	configureTalons();
	configureMotionMagic(leftMaster);
	configureMotionMagic(rightMaster);
	configureGyro();
	changeToPercentVBus();
	setBrakeMode(true);
	shiftHighGear();
    }

    public void configureTalons() {
	// reverses whole left side
	leftMaster.setInverted(true);
	left1.changeControlMode(TalonControlMode.Follower);
	left1.set(leftMaster.getDeviceID());
	left2.changeControlMode(TalonControlMode.Follower);
	left2.set(leftMaster.getDeviceID());

	// sets right motors to follow right master
	right1.changeControlMode(TalonControlMode.Follower);
	right1.set(rightMaster.getDeviceID());
	right2.changeControlMode(TalonControlMode.Follower);
	right2.set(rightMaster.getDeviceID());
    }

    public void configureMotionMagic(CANTalon talon) {
	talon.setProfile(0);
	talon.configNominalOutputVoltage(+0.0f, -0.0f);
	talon.configPeakOutputVoltage(+12.0f, -12.0f);
	if (talon.equals(leftMaster)) // left and right will have different F
	    talon.setF(0);
	else
	    talon.setF(0);
	talon.setP(0);
	talon.setI(0);
	talon.setD(0);
	talon.setMotionMagicAcceleration(DEFAULT_ACCELERATION);
	talon.setMotionMagicCruiseVelocity(DEFAULT_VELOCITY);
    }

    public void setMotionMagicCruiseVelocity(double v) {
	rightMaster.setMotionMagicCruiseVelocity(v);
	leftMaster.setMotionMagicCruiseVelocity(v);
    }

    public void setMotionMagicAcceleration(double a) {
	rightMaster.setMotionMagicAcceleration(a);
	leftMaster.setMotionMagicAcceleration(a);
    }

    public void changeToMotionMagic() {
	leftMaster.changeControlMode(TalonControlMode.MotionMagic);
	rightMaster.changeControlMode(TalonControlMode.MotionMagic);
    }

    public void changeToPercentVBus() {
	leftMaster.changeControlMode(TalonControlMode.PercentVbus);
	rightMaster.changeControlMode(TalonControlMode.PercentVbus);
    }

    public void setMotionMagicSetpoint(double inches) {
	if (leftMaster.getControlMode().equals(TalonControlMode.MotionMagic)
		&& rightMaster.getControlMode().equals(TalonControlMode.MotionMagic)) {
	    double rotations = inches / WHEEL_CIRCUMFERENCE;
	    rightMaster.set(rotations);
	    leftMaster.set(rotations);
	}
    }

    public void graphMagicMotionData() {
	graph("Left", leftMaster);
	graph("Right", rightMaster);
    }

    private int prints = 0;

    private void graph(String side, CANTalon source) {
	double speed = source.getSpeed();
	double position = source.getPosition();
	double throttle = (source.getOutputVoltage() / source.getBusVoltage()) * 1023;
	double error = source.getClosedLoopError();
	if (prints % 10 == 0) {
	    String s = String.format("%s: Speed: %.3f, Position: %.0f, Throttle: %.2f, Error: %.2f", side, speed,
		    position, throttle, error);
	    prints = 0;
	}
	prints++;
	SmartDashboard.putNumber(side + " RPM", speed);
	SmartDashboard.putNumber(side + " Pos", position);
	SmartDashboard.putNumber(side + " AppliedThrottle", throttle);
	SmartDashboard.putNumber(side + " ClosedLoopError", error);
    }

    public void configureGyro() {
	gyro.setPIDSourceType(PIDSourceType.kDisplacement);
	gyro.setSensitivity(GYRO_VOLTS_PER_DEGREE_PER_SECOND);
    }

    @Override
    public void initDefaultCommand() {
	setDefaultCommand(new DriveWithJoysticks());
    }

    public void driveWithJoysticks(double left, double right) {
	tankDrive(left, right);
	// we could change this to other things if we wanted, like
	// "driveMechanum" or "driveArcade" without having to change code in
	// other places
    }

    private void tankDrive(double left, double right) {
	driveRawWithRamp(left, right);
    }

    public void driveRaw(double left, double right) {
	driveRawLimit(left, right, -1, 1);
    }

    public void driveRawWithRamp(double left, double right) {
	driveRawLimitWithRamp(left, right, -1, 1);
    }

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

    public void driveRawAbsLimit(double left, double right, double lower, double upper) {
	double leftDir = Math.signum(left);
	double rightDir = Math.signum(right);
	left = Math.abs(left) < lower ? leftDir * lower : Math.abs(left) > upper ? leftDir * upper : left;
	right = Math.abs(right) < rightDir * lower ? lower : Math.abs(right) > upper ? rightDir * upper : right;
	prevLeft = left;
	prevRight = right;
	left *= left < 0 ? LEFT_PERCENTAGE_BACKWARD : LEFT_PERCENTAGE_FORWARD;
	right *= right < 0 ? RIGHT_PERCENTAGE_BACKWARD : RIGHT_PERCENTAGE_FORWARD;
	leftMaster.set(left);
	rightMaster.set(right);
    }

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

    public double rampWithEncoders(double prev, double requested, MyEncoder encoder) {
	if (isHighGear() && Math.abs(requested) > MIN_RAMP_LEVEL
		&& Math.abs(encoder.getRate()) < MAX_INCHES_PER_SECOND) {
	    return prev + RAMP_RATE * Math.signum(prev);
	} else {
	    return requested;
	}
    }

    @Override
    public void addToSmartDashboard(MySmartDashboard dashboard) {
	String directory = NAME + "/";

	// Left
	String leftDirectory = directory + "Left/";
	dashboard.addItem(
		SmartDashboardItem.newNumberSender(leftDirectory + "Left Encoder Distance", leftEncoder::getDistance));
	dashboard.addItem(SmartDashboardItem.newNumberSender(leftDirectory + "Left Rate", leftEncoder::getRate));
	dashboard.addItem(SmartDashboardItem.newNumberSender(leftDirectory + "Left Encoder Setpoint",
		mainController.left::getSetpoint));
	dashboard.addItem(
		SmartDashboardItem.newNumberSender(leftDirectory + "Left Error", mainController.left::getError));
	dashboard.addItem(SmartDashboardItem.newBooleanSender(leftDirectory + "At left setpoint?",
		mainController.left::onTarget));
	dashboard.addItem(
		SmartDashboardItem.newNumberSender(leftDirectory + "Left PID Output", mainController.left::getOutput));

	// Right
	String rightDirectory = directory + "Right/";
	dashboard.addItem(SmartDashboardItem.newNumberSender(rightDirectory + "Right Rate", rightEncoder::getRate));
	dashboard.addItem(SmartDashboardItem.newNumberSender(rightDirectory + "Right Encoder Distance",
		rightEncoder::getDistance));
	dashboard.addItem(SmartDashboardItem.newNumberSender(rightDirectory + "Right Encoder Setpoint",
		mainController.right::getSetpoint));
	dashboard.addItem(
		SmartDashboardItem.newNumberSender(rightDirectory + "Right Error", mainController.right::getError));
	dashboard.addItem(SmartDashboardItem.newBooleanSender(rightDirectory + "At right setpoint?",
		mainController.right::onTarget));
	dashboard.addItem(SmartDashboardItem.newNumberSender(rightDirectory + "Right PID Output",
		mainController.right::getOutput));

	String gyroDirectory = directory + "Gyro/";

	// Gyro
	dashboard.addItem(SmartDashboardItem.newNumberSender(gyroDirectory + "Gyro Degrees", gyro::getAngle));
	dashboard.addItem(
		SmartDashboardItem.newNumberSender(gyroDirectory + "Gyro Setpoint", gyroController::getSetpoint));
	dashboard.addItem(SmartDashboardItem.newNumberSender(gyroDirectory + "Gyro Error", gyroController::getError));
	dashboard.addItem(
		SmartDashboardItem.newBooleanSender(gyroDirectory + "At gyro setpoint?", gyroController::onTarget));
	dashboard.addItem(
		SmartDashboardItem.newNumberSender(gyroDirectory + "Gyro PID Output", gyroController::getOutput));
	// SmartDashboard.putData("Gyro PID", gyroPID);
    }

    public void setBrakeMode(boolean brake) {
	rightMaster.enableBrakeMode(brake);
	right1.enableBrakeMode(brake);
	right2.enableBrakeMode(brake);
	leftMaster.enableBrakeMode(brake);
	left1.enableBrakeMode(brake);
	left2.enableBrakeMode(brake);
    }

    public void clearTotalDistance() {
	leftEncoder.clearTotalDistance();
	rightEncoder.clearTotalDistance();
    }

    public void resetEncoders() {
	leftEncoder.reset();
	rightEncoder.reset();
    }

    public void resetGyro() {
	gyro.reset();
    }

    public double getAngle() {
	return gyro.getAngle();
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

    public double getLeftRightAdjustment(ControllerUnit left, ControllerUnit right) {
	return (left.getError() - right.getError()) * ERROR_DIFFERENCE_SCALAR;
    }

    /*
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
