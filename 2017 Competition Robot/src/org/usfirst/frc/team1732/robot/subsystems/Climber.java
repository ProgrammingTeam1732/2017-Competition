package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {

	private final CANTalon		motor		= new CANTalon(RobotMap.CLIMBER_MOTOR_DEVICE_NUMBER);
	public static final double	UP_SPEED	= 0.5;
	public static final double	STOP_SPEED	= 0;

	@Override
	public void initDefaultCommand() {}

	public void setUp() {
		motor.set(UP_SPEED);
	}

	public void setStop() {
		motor.set(STOP_SPEED);
	}
}
