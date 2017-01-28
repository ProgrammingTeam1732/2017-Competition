package org.usfirst.frc.team1732.robot.smartdashboard;

public abstract class SmartDashboardReciever<T> extends SmartDashboardItem<T> {

	protected SmartDashboardReciever(String key, T value) {
		super(key, value, null);
	}

}
