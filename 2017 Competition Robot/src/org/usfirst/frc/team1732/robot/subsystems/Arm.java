package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Arm used to grab the rope
 */
public class Arm extends Subsystem {
	private final Solenoid		solenoid	= new Solenoid(RobotMap.ARM_SOLENOID_DEVICE_NUMBER);
	public static final boolean	OUT			= true;
	public static final boolean	IN			= !OUT;

	@Override
	protected void initDefaultCommand() {}

	/**
	 * Sets the arm out
	 */
	public void setOut() {
		solenoid.set(OUT);
	}

	/**
	 * Sets the arm in
	 */
	public void setIn() {
		solenoid.set(IN);
	}

}
