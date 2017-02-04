package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearIntake extends Subsystem {

	private final CANTalon motor = new CANTalon(RobotMap.GEAR_INTAKE_MOTER_DEVICE_NUMBER);
	public static final double FORWARD_SPEED = -.75;
	public static final double STOP_SPEED = 0;
	public static final double REVERSE_SPEED = -FORWARD_SPEED;
	private final Solenoid gearManipulator = new Solenoid(RobotMap.GEAR_MANIPULATOR_SOLENOID_NUMBER);

	public static final boolean UP = false;
	public static final boolean DOWN = true;

	@Override
	public void initDefaultCommand() {

	}

	public void setDown() {
		System.out.println("Set DOWN");
		gearManipulator.set(DOWN);
	}

	public void setUp() {
		System.out.println("Set UP");
		gearManipulator.set(UP);
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
