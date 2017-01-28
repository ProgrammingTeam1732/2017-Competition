package org.usfirst.frc.team1732.robot.smartdashboard;

import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class for using sendables
 */
public class SmartDashboardData {

	private final String key;

	public SmartDashboardData(NamedSendable value) {
		this.key = value.getName();
		SmartDashboard.putData(value);
	}

	public SmartDashboardData(String key, Sendable value) {
		this.key = key;
		SmartDashboard.putData(key, value);
	}

	public Sendable getValue() {
		return SmartDashboard.getData(key);
	}

	public String getKey() {
		return key;
	}

}