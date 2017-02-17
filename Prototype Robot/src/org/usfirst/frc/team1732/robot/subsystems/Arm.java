package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem{
	private final Solenoid 		solenoid		= new Solenoid(RobotMap.ARM_SOLENOID_DEVICE_NUMBER);
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}
	public void setOut(){
		solenoid.set(true);
	}
	
	public void setIn(){
		solenoid.set(false);
	}

}
