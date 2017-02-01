package org.usfirst.frc.team1732.robot.smartdashboard;

import java.util.ArrayList;

public class MySmartDashboard {

	private final ArrayList<SmartDashboardItem<?>> items = new ArrayList<SmartDashboardItem<?>>();

	public <T> SmartDashboardItem<T> addItem(SmartDashboardItem<T> item) {
		items.add(item);
		return item;
	}

	public void init() {
		for (SmartDashboardItem<?> item : items)
			item.init();
	}

	public void run() {
		for (SmartDashboardItem<?> item : items)
			item.run();
	}

}