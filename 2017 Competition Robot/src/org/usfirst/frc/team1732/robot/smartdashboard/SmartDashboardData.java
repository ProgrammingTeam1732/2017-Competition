package org.usfirst.frc.team1732.robot.smartdashboard;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class for using sendables
 */
public class SmartDashboardData<T extends Sendable> {

	private final String key;

	public SmartDashboardData(String key, T value) {
		this.key = key;
		SmartDashboard.putData(key, value);
	}

	@SuppressWarnings("unchecked")
	public T getValue() {
		return (T) SmartDashboard.getData(key);
	}

	public String getKey() {
		return key;
	}

}