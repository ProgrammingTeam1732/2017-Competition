package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardGroup;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Class that runs the motor to climb the rope
 */
public class Climber extends Subsystem implements SmartDashboardGroup {

    private final CANTalon motor1 = new CANTalon(RobotMap.CLIMBER_MOTOR_DEVICE_NUMBER_1);
    private final CANTalon motor2 = new CANTalon(RobotMap.CLIMBER_MOTOR_DEVICE_NUMBER_2);

    public static final double UP_SPEED = -1;
    public static final double STOP_SPEED = 0;
    public static final double DOWN_SPEED = 0.5;

    public static final String NAME = "Climber";

    public Climber() {
	motor2.changeControlMode(TalonControlMode.Follower);
	motor2.set(motor1.getDeviceID());
    }

    @Override
    public void initDefaultCommand() {
    }

    /**
     * Runs the motor to climb up
     */
    public void setUp() {
	motor1.set(UP_SPEED);
    }

    public void setDown() {
	motor1.set(DOWN_SPEED);
    }

    /**
     * Stops the motor
     */
    public void setStop() {
	motor1.set(STOP_SPEED);
    }

    @Override
    public void addToSmartDashboard(MySmartDashboard dashboard) {
	String directory = NAME + "/";
	dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Climber Output", motor1::get));
    }
}
