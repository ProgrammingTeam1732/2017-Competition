package org.usfirst.frc.team1732.robot.smartdashboard;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardStringSender extends SmartDashboardSender<String> {

	protected SmartDashboardStringSender(String key, String value, Supplier<String> supplier) {
		super(key, value, supplier);
	}

	@Override
	protected void run() {
		setValue(supplierGet());
		SmartDashboard.putString(getKey(), getValue());
	}

}