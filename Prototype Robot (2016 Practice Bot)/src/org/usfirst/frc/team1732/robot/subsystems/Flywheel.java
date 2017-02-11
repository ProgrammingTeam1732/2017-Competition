package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Flywheel extends Subsystem {

	private final CANTalon	motor				= new CANTalon(RobotMap.FLYWHEEL_MOTER_DEVICE_NUMBER);
	private static double	smartDashboardSpeed	= 0;

	public static final double STOP_SPEED = 0;
	// public static final double FORWARD_SPEED = 1;
	// public static final double REVERSE_SPEED = 1;

	// public static final int COUNTS_PER_REVOLUTION = 1;
	public static final double	COUNTS_PER_SECOND_TARGET	= -19000;
	public static final double	COUNTS_PER_SECOND_ERROR		= COUNTS_PER_SECOND_TARGET / 50;

	private double				P					= Float.MAX_VALUE;
	private double				I					= 0;
	private double				D					= 0;
	private double				setpoint			= COUNTS_PER_SECOND_TARGET;
	public static final double	MAX_OUTPUT_VOLTAGE	= -12;

	public boolean isAutoControl = false;

	public Flywheel() {
		motor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		motor.setAllowableClosedLoopErr(0);
		motor.configPeakOutputVoltage(0, MAX_OUTPUT_VOLTAGE);
		motor.setPID(P, I, D);
		// motor.setF(F);
		motor.reverseSensor(false);
		motor.enableBrakeMode(false);

		motor.SetVelocityMeasurementPeriod(CANTalon.VelocityMeasurementPeriod.Period_1Ms);
		motor.SetVelocityMeasurementWindow(10);

		// motor.changeControlMode(TalonControlMode.PercentVbus);
		// motor.changeControlMode(CANTalon.TalonControlMode.Speed);
		// motor.set(-1);
		// motor.set(-0.5);
	}

	@Override
	public void initDefaultCommand() {
		// setDefaultCommand(new RunFlywheel());
	}

	/*
	 * public void setCounts(double counts){ COUNTS_PER_SECOND_TARGET = counts;
	 * COUNTS_PER_SECOND_ERROR = COUNTS_PER_SECOND_TARGET / 50; }
	 */
	public void setSmartDashboardSpeed(double s) {
		if (!isAutoControl && s != smartDashboardSpeed) {
			smartDashboardSpeed = s;
			motor.set(s);
		}
	}

	public double getPosistion() {
		return motor.getPosition();
	}

	public double getSetpoint() {
		return motor.getSetpoint();
	}

	public void setSetpoint(double s) {
		if (isAutoControl && s != setpoint) {
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

	public void setPID(double p, double i, double d) {
		P = p;
		I = i;
		D = d;
		motor.setPID(P, I, D);
	}

	public double getP() {
		return motor.getP();
	}

	public double getI() {
		return motor.getI();
	}

	public double getD() {
		return motor.getD();
	}

	public double getF() {
		return motor.getF();
	}

	public void setP(double p) {
		P = p;
		motor.setP(P);
	}

	public void setI(double i) {
		I = i;
		motor.setI(I);
	}

	public void setD(double d) {
		D = d;
		motor.setD(D);
	}

	public void disableAutoControl() {
		motor.changeControlMode(TalonControlMode.PercentVbus);
		motor.set(STOP_SPEED);
		isAutoControl = false;
	}

	//
	public void enableAutoControl() {
		motor.changeControlMode(TalonControlMode.Speed);
		motor.setSetpoint(setpoint);
		isAutoControl = true;
	}

	// semi old methods

	// public void setForward() {
	// motor.set(FORWARD_SPEED);
	// }
	//
	// public void setStop() {
	// motor.set(STOP_SPEED);
	// }
	//
	// public void setReverse() {
	// motor.set(REVERSE_SPEED);
	// }

	public int getCounts() {
		return motor.getEncPosition();
	}

}
