package org.usfirst.frc.team1732.robot.smartdashboard;

import java.util.function.Supplier;

public abstract class SmartDashboardSender<T> extends SmartDashboardItem<T> {

	protected SmartDashboardSender(String key, T value, Supplier<T> supplier) {
		super(key, value, supplier);
	}

}
