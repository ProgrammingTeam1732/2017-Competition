package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;
import org.usfirst.frc.team1732.robot.commands.RunFlywheel;

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
	public static final int	COUNTS_PER_SECOND_TARGET	= -18000;
	public static final int	COUNTS_PER_SECOND_ERROR		= COUNTS_PER_SECOND_TARGET / 50;

	private double				P					= Float.MAX_VALUE;
	private double				I					= 0;
	private double				D					= 0;
	private double				setpoint			= COUNTS_PER_SECOND_TARGET;
	public static final double	MAX_OUTPUT_VOLTAGE	= -8;

	public Flywheel() {
		motor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		motor.configNominalOutputVoltage(0, 0);
		motor.configPeakOutputVoltage(0, MAX_OUTPUT_VOLTAGE);
		motor.setPID(P, I, D);
		// motor.setF(F);
		motor.reverseSensor(false);
		motor.enableBrakeMode(false);
		// motor.changeControlMode(TalonControlMode.PercentVbus);
		// motor.changeControlMode(CANTalon.TalonControlMode.Speed);
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new RunFlywheel());
	}

	public static void SetSmartDashboardSpeed(double s) {
		smartDashboardSpeed = s;
	}

	public void driveWithSmartDashboard() {
		motor.set(smartDashboardSpeed);
	}

	public double getPosistion() {
		return motor.getPosition();
	}

	public double getSetpoint() {
		return motor.getSetpoint();
	}

	public void setSetpoint(double s) {
		motor.setSetpoint(s);
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
	}

	//
	public void enableAutoControl() {
		motor.changeControlMode(TalonControlMode.Speed);
		motor.setSetpoint(setpoint);
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

}
