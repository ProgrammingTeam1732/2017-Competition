package org.usfirst.frc.team1732.robot.smartdashboard;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardBooleanReciever extends SmartDashboardReciever<Boolean> {

	protected SmartDashboardBooleanReciever(String key, Boolean value) {
		super(key, value);
	}

	@Override
	protected void run() {
		setValue(SmartDashboard.getBoolean(getKey(), getValue()));
	}

}