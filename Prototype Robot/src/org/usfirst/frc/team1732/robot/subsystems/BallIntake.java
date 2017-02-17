package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class BallIntake extends Subsystem {

	private final CANTalon		motor			= new CANTalon(RobotMap.BALL_INTAKE_MOTER_DEVICE_NUMBER);
	private final Solenoid 		solenoid		= new Solenoid(RobotMap.BALL_INTAKE_SOLENOID_DEVICE_NUMBER);
	public static final double	FORWARD_SPEED	= 1;
	public static final double	STOP_SPEED		= 0;
	public static final double	REVERSE_SPEED	= 1;

	@Override
	public void initDefaultCommand() {

	}

	public void setIn() {
		motor.set(FORWARD_SPEED);
	}

	public void setStop() {
		motor.set(STOP_SPEED);
	}

	public void setOut() {
		motor.set(REVERSE_SPEED);
	}
	
	public void setDown(){
		solenoid.set(true);
	}
	
	public void setUp(){
		solenoid.set(false);
	}
}
