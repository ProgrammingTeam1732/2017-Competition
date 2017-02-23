package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearIntake extends Subsystem {

	private final CANTalon		motor				= new CANTalon(RobotMap.GEAR_INTAKE_MOTOR_DEVICE_NUMBER);
	public static final double	OUT_SPEED			= -.5;
	public static final double	STOP_SPEED			= 0;
	public static final double	IN_SPEED			= 0.6;
	private final Solenoid		gearManipulator		= new Solenoid(	RobotMap.PCM_CAN_ID,
																	RobotMap.GEAR_MANIPULATOR_SOLENOID_NUMBER);
	private final Solenoid		gearManipStorage	= new Solenoid(	RobotMap.PCM_CAN_ID,
																	RobotMap.GEAR_MANIPULATOR_STORAGE_NUMBER);
	public static final boolean	UP					= false;
	public static final boolean	DOWN				= true;
	public static final boolean	IN					= true;
	public static final boolean	OUT					= false;

	@Override
	public void initDefaultCommand() {}

	public void setDown() {
		gearManipulator.set(DOWN);
	}

	public void setUp() {
		gearManipulator.set(UP);
	}

	public void setOut() {
		motor.set(OUT_SPEED);
	}

	public void setStop() {
		motor.set(STOP_SPEED);
	}

	public void setIn() {
		motor.set(IN_SPEED);
	}

	public boolean isDown() {
		return gearManipulator.get() == DOWN;
	}

	public boolean isUp() {
		return gearManipulator.get() == UP;
	}

	public void setStorageIn() {
		gearManipStorage.set(IN);
	}

	public void setStorageOut() {
		gearManipStorage.set(OUT);
	}
}
