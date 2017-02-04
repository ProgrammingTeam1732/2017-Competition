package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;

import com.ctre.CANTalon;

public class LightRing {
	private double brightness;
	private final CANTalon motor = new CANTalon(RobotMap.LIGHT_RING_MOTOR_NUMBER);
	public void setBrightness(double brightness){
		this.brightness = brightness;
		motor.set(brightness);
	}
	public double getBrightness(){
		return brightness;
	}
	
}
