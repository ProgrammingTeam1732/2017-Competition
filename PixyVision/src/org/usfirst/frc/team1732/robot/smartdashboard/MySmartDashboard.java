package org.usfirst.frc.team1732.robot.smartdashboard;

import java.util.ArrayList;
import java.util.function.Supplier;

public class MySmartDashboard {

	private final ArrayList<SmartDashboardItem> items = new ArrayList<SmartDashboardItem>();

	public SmartDashboardItem<Double> addDoubleSender(String key, Supplier<Double> supplier) {
		SmartDashboardItem<Double> s = SmartDashboardItem.newDoubleSender(key, supplier);
		items.add(s);
		return s;
	}

	public SmartDashboardItem<Double> addDoubleReciever(String key, Double value) {
		SmartDashboardItem<Double> s = SmartDashboardItem.newDoubleReciever(key, value);
		items.add(s);
		return s;
	}

	public SmartDashboardItem<Boolean> addBooleanSender(String key, Supplier<Boolean> supplier) {
		SmartDashboardItem<Boolean> s = SmartDashboardItem.newBooleanSender(key, supplier);
		items.add(s);
		return s;
	}

	public SmartDashboardItem<Boolean> addBooleanReciever(String key, Boolean value) {
		SmartDashboardItem<Boolean> s = SmartDashboardItem.newBooleanReciever(key, value);
		items.add(s);
		return s;
	}

	public SmartDashboardItem<String> addStringSender(String key, Supplier<String> supplier) {
		SmartDashboardItem<String> s = SmartDashboardItem.newStringSender(key, supplier);
		items.add(s);
		return s;
	}

	public SmartDashboardItem<String> addStringReciever(String key, String value) {
		SmartDashboardItem<String> s = SmartDashboardItem.newStringReciever(key, value);
		items.add(s);
		return s;
	}

	public void run() {
		for (SmartDashboardItem item : items)
			item.run();
	}

}