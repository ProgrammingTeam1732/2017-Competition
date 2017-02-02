package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Flywheel extends Subsystem {

	private final CANTalon		motor			= new CANTalon(RobotMap.FLYWHEEL_MOTER_DEVICE_NUMBER);
	public static final double	FORWARD_SPEED	= 1;
	public static final double	STOP_SPEED		= 0;
	public static final double	REVERSE_SPEED	= 1;
	private double				motorSpeed		= 0;

	public static final int	COUNTS_PER_REVOLUTION		= 1;
	public static final int	COUNTS_PER_SECOND_TARGET	= 18000;
	public static final int	COUNTS_PER_SECOND_ERROR		= COUNTS_PER_SECOND_TARGET / 50;

	private double	P			= 0;
	private double	I			= 0;
	private double	D			= 0;
	private double	setpoint	= COUNTS_PER_SECOND_TARGET;

	public static final double	BANG_BANG_UPPER	= 1;
	public static final double	BANG_BANG_FF	= 0.485;	// 0.4;

	public Flywheel() {
		// motor.setControlMode(CANTalon.TalonControlMode.Speed.value);
		// motor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		// motor.setPID(P, I, D);
		// motor.setSetpoint(setpoint);
		// motor.disableControl();
		motor.reverseSensor(true);
		motor.setInverted(true);
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
		// motor.disableControl();
		motor.set(0);
	}

	public void enable() {
		// motor.enableControl();
		// motor.set(motorSpeed);
	}

	public void setMotorSpeed(double s) {
		motorSpeed = s;
		motor.set(s);
	}

	public double getPosistion() {
		return motor.getPosition();
	}

	public double getSetpoint() {
		return setpoint;
		// return motor.getSetpoint();
	}

	public void setSetpoint(double s) {
		setpoint = s;
		// motor.setSetpoint(setpoint);
	}

	public double getSpeed() {
		return motor.getSpeed();
	}

	public double getOutputVoltage() {
		return motor.getOutputVoltage();
	}

	public double getMotorOutput() {
		return motor.get();
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

}
