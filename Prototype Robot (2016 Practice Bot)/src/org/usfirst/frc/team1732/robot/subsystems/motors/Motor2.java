package org.usfirst.frc.team1732.robot.subsystems.motors;

import org.usfirst.frc.team1732.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Motor2 extends Subsystem {
	private final CANTalon		motor	= new CANTalon(RobotMap.MOTOR_TWO_DEVICE_ID);
	public static final String	name	= "Motor " + RobotMap.MOTOR_TWO_DEVICE_ID;

	public Motor2() {
		super(name);
		SmartDashboard.putNumber(name, 0);
	}

	public void stop() {
		motor.set(0);
	}

	public void run() {
		motor.set(SmartDashboard.getNumber(name, 0));
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub

	}
}