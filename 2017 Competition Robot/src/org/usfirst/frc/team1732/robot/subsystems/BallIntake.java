package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BallIntake extends Subsystem {

	private final CANTalon		motor		= new CANTalon(RobotMap.BALL_INTAKE_MOTOR_DEVICE_NUMBER);
	private final Solenoid		solenoid	= new Solenoid(RobotMap.BALL_INTAKE_SOLENOID_DEVICE_NUMBER);
	public static final double	IN_SPEED	= 1;
	public static final double	STOP_SPEED	= 0;
	public static final double	OUT_SPEED	= -1;

	public static final boolean	UP		= false;
	public static final boolean	DOWN	= true;

	@Override
	public void initDefaultCommand() {

	}

	public void setSpeedIn() {
		motor.set(IN_SPEED);
	}

	public void setSpeedStop() {
		motor.set(STOP_SPEED);
	}

	public void setSpeedOut() {
		motor.set(OUT_SPEED);
	}

	public void setPosistionUp() {
		solenoid.set(UP);
	}

	public void setPosistionDown() {
		solenoid.set(DOWN);
	}

	public boolean isPosistionDown() {
		return solenoid.get() == DOWN;
	}

	public boolean isPosistionUp() {
		return solenoid.get() == UP;
	}
}
