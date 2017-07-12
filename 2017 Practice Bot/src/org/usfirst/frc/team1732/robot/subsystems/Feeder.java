package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardGroup;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The feeder inside the robot
 */
public class Feeder extends Subsystem implements SmartDashboardGroup {

    private final CANTalon motor = new CANTalon(RobotMap.FEEDER_MOTOR_DEVICE_NUMBER);
    public static final double IN_SPEED = -1;
    public static final double STOP_SPEED = 0;
    public static final double OUT_SPEED = 1; // 0.75, 0.5

    public static final String NAME = "Feeder";

    @Override
    public void initDefaultCommand() {
    }

    public void setIn() {
	motor.set(IN_SPEED);
    }

    public void setStop() {
	motor.set(STOP_SPEED);
    }

    public void setOut() {
	motor.set(OUT_SPEED);
    }

    public void setSpeed(double b) {
	motor.set(b);
    }

    @Override
    public void addToSmartDashboard(MySmartDashboard dashboard) {
	String directory = NAME + "/";
	dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Feeder output", motor::get));
    }
}
