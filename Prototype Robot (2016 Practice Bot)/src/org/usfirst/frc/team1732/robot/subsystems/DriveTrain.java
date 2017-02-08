package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;
import org.usfirst.frc.team1732.robot.commands.DriveWithJoysticks;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Subsystem;

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

	private final AnalogGyro	gyro			= new AnalogGyro(1);
	private final PIDController	leftController	= new PIDController(0, 0, 0, gyro, leftMaster);
	private final PIDController	rightController	= new PIDController(0, 0, 0, gyro, leftMaster);

	public DriveTrain() {
		leftMaster.setInverted(true);
		left1.changeControlMode(TalonControlMode.Follower);
		left1.set(leftMaster.getDeviceID());
		left2.changeControlMode(TalonControlMode.Follower);
		left2.set(leftMaster.getDeviceID());

		right1.changeControlMode(TalonControlMode.Follower);
		right1.set(rightMaster.getDeviceID());
		right2.changeControlMode(TalonControlMode.Follower);
		right2.set(rightMaster.getDeviceID());

		gyro.initGyro();
		gyro.calibrate();
		gyro.setPIDSourceType(PIDSourceType.kDisplacement);
		// gyro.setSensitivity(voltsPerDegreePerSecond);

	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoysticks());
	}

	public void zeroGyro() {
		// gyro.se
	}

	public void tankDrive(double left, double right) {
		leftMaster.set(left);
		rightMaster.set(right);
	}

}
