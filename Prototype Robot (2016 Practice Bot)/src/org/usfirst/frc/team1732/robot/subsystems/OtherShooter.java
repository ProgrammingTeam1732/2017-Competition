package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class OtherShooter extends Subsystem {

	private final CANTalon motor = new CANTalon(RobotMap.OTHER_SHOOTER_MOTER_DEVICE_NUMBER);

	@Override
	public void initDefaultCommand() {

	}

	public void shoot() {

	}

	public void reset() {

	}
}
