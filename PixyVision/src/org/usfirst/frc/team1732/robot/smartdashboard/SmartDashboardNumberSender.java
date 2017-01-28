package org.usfirst.frc.team1732.robot.smartdashboard;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardNumberSender extends SmartDashboardSender<Double> {

	protected SmartDashboardNumberSender(String key, Double value, Supplier<Double> supplier) {
		super(key, value, supplier);
	}

	@Override
	protected void run() {
		setValue(supplierGet());
		SmartDashboard.putNumber(getKey(), getValue());
	}

}