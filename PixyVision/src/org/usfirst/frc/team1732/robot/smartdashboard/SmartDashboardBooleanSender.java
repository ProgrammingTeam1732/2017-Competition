package org.usfirst.frc.team1732.robot.smartdashboard;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardBooleanSender extends SmartDashboardSender<Boolean> {

	protected SmartDashboardBooleanSender(String key, Supplier<Boolean> supplier) {
		super(key, supplier);
	}

	@Override
	protected void run() {
		setValue(supplierGet());
		SmartDashboard.putBoolean(getKey(), getValue());
	}

}