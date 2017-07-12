package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardGroup;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Arm used to grab the rope
 */
public class Arm extends Subsystem implements SmartDashboardGroup {
    private final Solenoid solenoid = new Solenoid(RobotMap.PCM_CAN_ID, RobotMap.ARM_SOLENOID_DEVICE_NUMBER);
    public static final boolean OUT = true;
    public static final boolean IN = !OUT;

    public static final String NAME = "Climber Arm";

    @Override
    protected void initDefaultCommand() {
    }

    /**
     * Sets the arm out
     */
    public void setOut() {
	solenoid.set(OUT);
    }

    /**
     * Sets the arm in
     */
    public void setIn() {
	solenoid.set(IN);
    }

    public boolean isOut() {
	return solenoid.get() == OUT;
    }

    public boolean isIn() {
	return solenoid.get() == IN;
    }

    @Override
    public void addToSmartDashboard(MySmartDashboard dashboard) {
	String directory = NAME + "/";
	dashboard.addItem(SmartDashboardItem.newBooleanSender(directory + "Climber Arm is Out", this::isOut));
    }

}
