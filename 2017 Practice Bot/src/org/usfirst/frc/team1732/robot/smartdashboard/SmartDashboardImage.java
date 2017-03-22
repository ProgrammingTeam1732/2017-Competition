package org.usfirst.frc.team1732.robot.smartdashboard;

import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.tables.ITable;

public class SmartDashboardImage implements NamedSendable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String m_name = "Image";
	private ITable m_table;

	@Override
	public void initTable(ITable subtable) {
		m_table = subtable;
	}

	@Override
	public ITable getTable() {
		return m_table;
	}

	@Override
	public String getSmartDashboardType() {
		return "Image";
	}

	@Override
	public String getName() {
		return m_name;
	}

}
