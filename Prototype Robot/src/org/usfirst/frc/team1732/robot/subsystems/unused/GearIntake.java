package org.usfirst.frc.team1732.robot.subsystems.unused;

import org.usfirst.frc.team1732.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearIntake extends Subsystem {

	private final CANTalon motor = new CANTalon(RobotMap.GEAR_INTAKE_MOTER_DEVICE_NUMBER);
	public static final double FORWARD_SPEED = 1;
	public static final double STOP_SPEED = 0;
	public static final double REVERSE_SPEED = 1;
	private final Solenoid gearManipulator = new Solenoid(RobotMap.GEAR_MANIPULATOR_SOLENOID_NUMBER);

	@Override
	public void initDefaultCommand() {

	}

	public void setDown() {
		gearManipulator.set(true);
	}

	public void setUp() {
		gearManipulator.set(false);
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
