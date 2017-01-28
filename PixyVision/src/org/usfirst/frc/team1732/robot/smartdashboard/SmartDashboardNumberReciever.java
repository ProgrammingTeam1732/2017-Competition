package org.usfirst.frc.team1732.robot.smartdashboard;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardNumberReciever extends SmartDashboardReciever<Double> {

	protected SmartDashboardNumberReciever(String key, Double value) {
		super(key, value);
	}

	@Override
	protected void run() {
		setValue(SmartDashboard.getNumber(getKey(), getValue()));
	}

}