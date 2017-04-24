package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardGroup;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class Flywheel extends Subsystem implements SmartDashboardGroup {

    public final CANTalon motor = new CANTalon(RobotMap.FLYWHEEL_MOTOR_DEVICE_NUMBER);

    public static final double STOP_SPEED = 0;
    // public static final double FORWARD_SPEED = 1;
    // public static final double REVERSE_SPEED = 1;

    // public static final int COUNTS_PER_REVOLUTION = 1;
    public static final int COUNTS_PER_SECOND_TARGET = 19000;
    public static final int COUNTS_PER_SECOND_ERROR = 300; // COUNTS_PER_SECOND_TARGET
    // /
    // 50;
    private double setpoint = COUNTS_PER_SECOND_TARGET;

    public static final double MAX_OUTPUT_VOLTAGE = 12;
    @SuppressWarnings("unused")
    private static final double MAX_VELOCITY = 32000;
    @SuppressWarnings("unused")
    private static final double BASE_VOLTAGE = 8;
    private static final double P = Double.MAX_VALUE; // Float.MAX_VALUE;
    private static final double I = 0;
    private static final double D = 0;
    @SuppressWarnings("unused")
    private static final double F = 0.75 * 1023 / COUNTS_PER_SECOND_TARGET;

    private boolean isAutoControlled = false;

    public static final String NAME = "Flywheel";

    public Flywheel() {
	super(NAME);
	motor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
	motor.setAllowableClosedLoopErr(COUNTS_PER_SECOND_ERROR);
	motor.configNominalOutputVoltage(0, 0);
	motor.configPeakOutputVoltage(MAX_OUTPUT_VOLTAGE, 0);
	motor.setPID(P, I, D);
	// motor.setF(F);
	motor.reverseSensor(true);
	motor.enableBrakeMode(false);
	motor.SetVelocityMeasurementPeriod(CANTalon.VelocityMeasurementPeriod.Period_1Ms);
	motor.SetVelocityMeasurementWindow(10);
	this.disableAutoControl();
    }

    @Override
    public void initDefaultCommand() {
    }

    public boolean isAutoControlled() {
	return isAutoControlled;
    }

    public double getPosistion() {
	return motor.getPosition();
    }

    public double getSetpoint() {
	return motor.getSetpoint();
    }

    public void setSetpoint(double s) {
	if (isAutoControlled && s != setpoint) {
	    setpoint = s;
	    motor.setSetpoint(s);
	}
    }

    public double getEncVelocity() {
	return motor.getEncVelocity();
    }

    public double getSpeed() {
	return motor.getSpeed();
    }

    public double getOutputVoltage() {
	return motor.getOutputVoltage();
    }

    public double getMotorOutput() {
	return motor.getOutputVoltage() / motor.getBusVoltage();
    }

    public double getError() {
	return motor.getClosedLoopError();
    }

    public void disableAutoControl() {
	motor.changeControlMode(TalonControlMode.PercentVbus);
	motor.set(STOP_SPEED);
	isAutoControlled = false;
    }

    //
    public void enableAutoControl() {
	motor.changeControlMode(TalonControlMode.Speed);
	motor.setSetpoint(setpoint);
	isAutoControlled = true;
    }

    public int getCounts() {
	return motor.getEncPosition();
    }

    public double getOutput() {
	return motor.getOutputVoltage() / motor.getBusVoltage();
    }

    public static final double percentErrorAllowed = 0.05; // 5%

    public boolean atSetpoint() {
	return Math.abs(motor.getError()) < Math.abs(percentErrorAllowed * motor.getSetpoint());
    }

    public void setSpeed(double s) {
	if (!isAutoControlled) {
	    motor.set(s);
	}
    }

    @Override
    public void addToSmartDashboard(MySmartDashboard dashboard) {
	// TODO Auto-generated method stub
	String directory = NAME + "/";
	dashboard.addItem(SmartDashboardItem.newDoubleReciever(directory + "P", P, motor::setP));
	dashboard.addItem(SmartDashboardItem.newDoubleReciever(directory + "I", I, motor::setI));
	dashboard.addItem(SmartDashboardItem.newDoubleReciever(directory + "D", D, motor::setD));
	dashboard.addItem(SmartDashboardItem.newDoubleReciever(directory + "Setpoint",
		(double) COUNTS_PER_SECOND_TARGET, this::setSetpoint));
	dashboard.addItem(SmartDashboardItem.newDoubleReciever(directory + "Set speed", (double) 0, this::setSpeed));

	dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Error", motor::getClosedLoopError));
	dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Output", this::getOutput));
	dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Speed", motor::getSpeed));
	dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Voltage", motor::getOutputVoltage));
	dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Encoder Velocity", motor::getEncVelocity));
	dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Posistion", motor::getPosition));
	dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Current", motor::getOutputCurrent));

    }

}
