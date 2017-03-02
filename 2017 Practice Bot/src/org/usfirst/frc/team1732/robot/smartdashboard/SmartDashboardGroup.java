package org.usfirst.frc.team1732.robot.smartdashboard;

/**
 * Interface that classes can implement to indicate they can receive a dashboard
 * and will add items to that dashboard
 */
public interface SmartDashboardGroup {

	/**
	 * Adds items from the class to the smart dashboard
	 * 
	 * @param dashboard
	 *            dashboard that items are added to
	 */
	void addToSmartDashboard(MySmartDashboard dashboard);

}