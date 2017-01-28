package org.usfirst.frc.team1732.robot.smartdashboard;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardStringReciever extends SmartDashboardReciever<String> {

	protected SmartDashboardStringReciever(String key, String value) {
		super(key, value);
	}

	@Override
	protected void run() {
		setValue(SmartDashboard.getString(getKey(), getValue()));
	}

}