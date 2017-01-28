package org.usfirst.frc.team1732.robot.smartdashboard;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardBooleanSender extends SmartDashboardSender<Boolean> {

	protected SmartDashboardBooleanSender(String key, Boolean value, Supplier<Boolean> supplier) {
		super(key, value, supplier);
	}

	@Override
	protected void run() {
		setValue(supplierGet());
		SmartDashboard.putBoolean(getKey(), getValue());
	}

}