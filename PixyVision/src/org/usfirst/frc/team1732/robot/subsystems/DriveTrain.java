package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;
import org.usfirst.frc.team1732.robot.commands.DriveWithJoysticks;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveTrain extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	private final CANTalon	leftMaster	= new CANTalon(RobotMap.LEFT_MASTER_MOTOR_DEVICE_NUMBER);
	private final CANTalon	left1		= new CANTalon(RobotMap.LEFT_1_MOTOR_DEVICE_NUMBER);
	private final CANTalon	left2		= new CANTalon(RobotMap.LEFT_2_MOTOR_DEVICE_NUMBER);

	private final CANTalon	rightMaster	= new CANTalon(RobotMap.RIGHT_MASTER_MOTOR_DEVICE_NUMBER);
	private final CANTalon	right1		= new CANTalon(RobotMap.RIGHT_1_MOTOR_DEVICE_NUMBER);
	private final CANTalon	right2		= new CANTalon(RobotMap.RIGHT_2_MOTOR_DEVICE_NUMBER);

	private final AnalogGyro	gyro				= new AnalogGyro(1);
	private final PIDController	leftGyroController	= new PIDController(0, 0, 0, gyro, leftMaster);
	private final PIDController	rightGyroController	= new PIDController(0, 0, 0, gyro, rightMaster);

	private final Encoder	leftEncoder		= new Encoder(2, 3);
	private final Encoder	rightEncoder	= new Encoder(0, 1);

	private final PIDController	leftEncoderController	= new PIDController(0.5, 0, 0, leftEncoder, leftMaster);
	private final PIDController	rightEncoderController	= new PIDController(0.5, 0, 0, rightEncoder, rightMaster);
	public final double			ENCODER_DEADBAND_INCHES	= 5;

	public DriveTrain() {
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

		gyro.initGyro();
		gyro.calibrate();
		gyro.setPIDSourceType(PIDSourceType.kDisplacement);
		// gyro.setSensitivity(voltsPerDegreePerSecond);

		leftEncoderController.setAbsoluteTolerance(ENCODER_DEADBAND_INCHES);
		rightEncoderController.setAbsoluteTolerance(ENCODER_DEADBAND_INCHES);
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks());
	}

	public void zeroGyro() {
		// gyro.se
	}

	public void setDriveManual() {
		leftGyroController.disable();
		rightGyroController.disable();
		leftEncoderController.disable();
		rightEncoderController.disable();
	}

	public void setDriveWithEncoders() {
		leftGyroController.disable();
		rightGyroController.disable();
		leftEncoderController.enable();
		rightEncoderController.enable();
	}

	public void setDriveWithGyros() {
		leftGyroController.enable();
		rightGyroController.enable();
		leftEncoderController.disable();
		rightEncoderController.disable();
	}

	public void zeroEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
	}

	public void setEncoderSetpoint(double setpoint) {
		leftEncoderController.setSetpoint(setpoint);
		rightEncoderController.setSetpoint(setpoint);
	}

	public boolean isAtEncoderSetpoint() {
		return leftEncoderController.onTarget() && rightEncoderController.onTarget();
	}

	public void updateSmartDashboard() {
		SmartDashboard.putNumber("Left Encoder counts", leftEncoder.get());
		SmartDashboard.putNumber("Right Encoder counts", leftEncoder.get());
	}

	public void tankDrive(double left, double right) {
		leftMaster.set(left);
		rightMaster.set(right);
	}

}
