package org.usfirst.frc.team1732.robot.smartdashboard;

import java.util.ArrayList;
import java.util.function.Supplier;

public class MySmartDashboard {

	private final ArrayList<SmartDashboardItem> items = new ArrayList<SmartDashboardItem>();

	public SmartDashboardNumberSender addNumberSender(String key, Supplier<Double> supplier) {
		SmartDashboardNumberSender s = new SmartDashboardNumberSender(key, supplier);
		items.add(s);
		return s;
	}

	public SmartDashboardNumberReciever addNumberReciever(String key, Double value) {
		SmartDashboardNumberReciever s = new SmartDashboardNumberReciever(key, value);
		items.add(s);
		return s;
	}

	public SmartDashboardBooleanSender addBooleanSender(String key, Supplier<Boolean> supplier) {
		SmartDashboardBooleanSender s = new SmartDashboardBooleanSender(key, supplier);
		items.add(s);
		return s;
	}

	public SmartDashboardBooleanReciever addBooleanReciever(String key, Boolean value) {
		SmartDashboardBooleanReciever s = new SmartDashboardBooleanReciever(key, value);
		items.add(s);
		return s;
	}

	public SmartDashboardStringSender addStringSender(String key, Supplier<String> supplier) {
		SmartDashboardStringSender s = new SmartDashboardStringSender(key, supplier);
		items.add(s);
		return s;
	}

	public SmartDashboardStringReciever addStringReciever(String key, String value) {
		SmartDashboardStringReciever s = new SmartDashboardStringReciever(key, value);
		items.add(s);
		return s;
	}

	public void run() {
		for (SmartDashboardItem item : items)
			item.run();
	}

}