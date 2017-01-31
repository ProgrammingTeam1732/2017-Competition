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

	private final CANTalon		motor						= new CANTalon(RobotMap.FLYWHEEL_MOTER_DEVICE_NUMBER);
	public static final double	FORWARD_SPEED				= 1;
	public static final double	STOP_SPEED					= 0;
	public static final double	REVERSE_SPEED				= 1;
	public static final int		COUNTS_PER_REVOLUTION		= 1;
	public static final int		COUNTS_PER_SECOND_TARGET	= 500;
	public static final int		COUNTS_PER_SECOND_ERROR		= COUNTS_PER_SECOND_TARGET / 50;

	public Flywheel() {
		motor.changeControlMode(TalonControlMode.Speed);
		motor.configEncoderCodesPerRev(COUNTS_PER_REVOLUTION);
		motor.setAllowableClosedLoopErr(COUNTS_PER_SECOND_ERROR);
		motor.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		motor.disable();
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
}
