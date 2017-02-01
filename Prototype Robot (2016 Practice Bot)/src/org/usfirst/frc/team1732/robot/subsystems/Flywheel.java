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
	private int				P							= 1;
	private int				I							= 0;
	private int				D							= 0;

	public Flywheel() {
		motor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		motor.disable();
		motor.setPID(P, I, D);
		// motor.reverseSensor(true);
		// motor.getSpeed();
		// motor.setSetpoint(COUNTS_PER_SECOND_TARGET);
		// motor.disable();
		// motor.disableControl();
		// motor.enable();
		// motor.enableControl();
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

	public double getSpeed() {
		return motor.getSpeed();
	}

	public double get() {
		return motor.
	}

}
