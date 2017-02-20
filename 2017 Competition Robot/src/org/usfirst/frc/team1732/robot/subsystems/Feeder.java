package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The feeder inside the robot
 */
public class Feeder extends Subsystem {

	private final CANTalon		motor		= new CANTalon(RobotMap.FEEDER_MOTOR_DEVICE_NUMBER);
	public static final double	IN_SPEED	= 1;
	public static final double	STOP_SPEED	= 0;
	public static final double	OUT_SPEED	= -1;

	@Override
	public void initDefaultCommand() {}

	public void setIn() {
		motor.set(IN_SPEED);
	}

	public void setStop() {
		motor.set(STOP_SPEED);
	}

	public void setOut() {
		motor.set(OUT_SPEED);
	}
}
