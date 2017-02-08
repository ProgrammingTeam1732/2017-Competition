package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardGroup;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Flywheel extends Subsystem implements SmartDashboardGroup {

	public final CANTalon motor = new CANTalon(RobotMap.FLYWHEEL_MOTER_DEVICE_NUMBER);

	public static final double STOP_SPEED = 0;
	// public static final double FORWARD_SPEED = 1;
	// public static final double REVERSE_SPEED = 1;

	// public static final int COUNTS_PER_REVOLUTION = 1;
	public static final int	COUNTS_PER_SECOND_TARGET	= -18000;
	public static final int	COUNTS_PER_SECOND_ERROR		= COUNTS_PER_SECOND_TARGET / 50;

	private static final double	P					= Float.MAX_VALUE;
	private static final double	I					= 0;
	private static final double	D					= 0;
	private double				setpoint			= COUNTS_PER_SECOND_TARGET;
	public static final double	MAX_OUTPUT_VOLTAGE	= -8;

	public static final String NAME = "Flywheel";

	public Flywheel() {
		super(NAME);
		motor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		motor.configNominalOutputVoltage(0, 0);
		motor.configPeakOutputVoltage(0, MAX_OUTPUT_VOLTAGE);
		motor.setPID(P, I, D);
		motor.reverseSensor(false);
		motor.enableBrakeMode(false);
		motor.changeControlMode(TalonControlMode.PercentVbus);
	}

	@Override
	public void initDefaultCommand() {}

	public double getOutput() {
		return motor.getOutputVoltage() / motor.getBusVoltage();
	}

	public void disableAutoControl() {
		motor.changeControlMode(TalonControlMode.PercentVbus);
		motor.set(STOP_SPEED);
	}

	//
	public void enableAutoControl() {
		motor.changeControlMode(TalonControlMode.Speed);
		motor.setSetpoint(setpoint);
	}

	@Override
	public void addToSmartDashboard(MySmartDashboard dashboard) {
		// TODO Auto-generated method stub
		String directory = NAME + "/";
		dashboard.addItem(SmartDashboardItem.newDoubleReciever(directory + "P", P, motor::setP));
		dashboard.addItem(SmartDashboardItem.newDoubleReciever(directory + "I", P, motor::setI));
		dashboard.addItem(SmartDashboardItem.newDoubleReciever(directory + "D", P, motor::setD));
		dashboard.addItem(SmartDashboardItem.newDoubleReciever(directory + "Setpoint", P, motor::setSetpoint));

		dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Error", motor::getClosedLoopError));
		dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Output", this::getOutput));
		dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Speed", motor::getSpeed));
		dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Voltage", motor::getOutputVoltage));
		dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Encoder Velocity", motor::getEncVelocity));
		dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Posistion", motor::getPosition));
	}

}
