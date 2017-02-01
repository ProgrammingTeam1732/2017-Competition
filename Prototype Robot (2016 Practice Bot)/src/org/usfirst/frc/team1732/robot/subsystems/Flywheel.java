package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Flywheel extends Subsystem {

	private final CANTalon		motor			= new CANTalon(RobotMap.FLYWHEEL_MOTER_DEVICE_NUMBER);
	public static final double	FORWARD_SPEED	= 1;
	public static final double	STOP_SPEED		= 0;
	public static final double	REVERSE_SPEED	= 1;

	public static final int	COUNTS_PER_REVOLUTION		= 1;
	public static final int	COUNTS_PER_SECOND_TARGET	= 500;
	public static final int	COUNTS_PER_SECOND_ERROR		= COUNTS_PER_SECOND_TARGET / 50;

	private double	P			= 1;
	private double	I			= 0;
	private double	D			= 0;
	private double	setpoint	= COUNTS_PER_SECOND_TARGET;

	public Flywheel() {
		motor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		motor.setPID(P, I, D);
		motor.setSetpoint(setpoint);
		motor.disable();
		motor.startLiveWindowMode();
		// SmartDashboard.putData(new NamedSendable() {
		//
		// @Override
		// public void initTable(ITable subtable) {
		// motor.initTable(subtable);
		// }
		//
		// @Override
		// public ITable getTable() {
		// return motor.getTable();
		// }
		//
		// @Override
		// public String getSmartDashboardType() {
		// return motor.getSmartDashboardType();
		// }
		//
		// @Override
		// public String getName() {
		// return "Flywheel motor: " + motor.getDeviceID();
		// }
		//
		// });
		// motor.reverseSensor(true);
	}

	@Override
	public void initDefaultCommand() {

	}

	public void setForward() {
		motor.set(FORWARD_SPEED);
	}

	public void setStop() {
		motor.set(STOP_SPEED);
	}

	public void setReverse() {
		motor.set(REVERSE_SPEED);
	}

	public void disable() {
		motor.disable();
	}

	public void enable() {
		motor.enable();
	}

	public double getPosistion() {
		return motor.getPosition();
	}

	public double getSetpoint() {
		return motor.getSetpoint();
	}

	public void setSetpoint(double s) {
		setpoint = s;
		motor.setSetpoint(setpoint);
	}

	public double getSpeed() {
		return motor.getSpeed();
	}

	public double getOutputVoltage() {
		return motor.getOutputVoltage();
	}

	public void setPID(double p, double i, double d) {
		P = p;
		I = i;
		D = d;
		motor.setPID(P, I, D);
	}

}
