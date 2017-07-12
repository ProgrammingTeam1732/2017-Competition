package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardGroup;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Class that runs the ball intake
 */
public class BallIntake extends Subsystem implements SmartDashboardGroup {

    private final CANTalon motor = new CANTalon(RobotMap.BALL_INTAKE_MOTOR_DEVICE_NUMBER);
    private final Solenoid solenoid = new Solenoid(RobotMap.PCM_CAN_ID, RobotMap.BALL_INTAKE_SOLENOID_DEVICE_NUMBER);
    public static final double IN_SPEED = -1; // -1 // -1
    public static final double STOP_SPEED = 0;
    public static final double OUT_SPEED = -IN_SPEED; // 1

    public static final boolean UP = true; // true
    public static final boolean DOWN = !UP;

    public static final String NAME = "Ball Intake";

    @Override
    public void initDefaultCommand() {
    }

    /**
     * Sets the ball intake to spin in
     */
    public void setSpeedIn() {
	motor.set(IN_SPEED);
    }

    /**
     * Sets the ball intake to stop
     */
    public void setSpeedStop() {
	motor.set(STOP_SPEED);
    }

    /**
     * Sets the ball intake to spin out
     */
    public void setSpeedOut() {
	motor.set(OUT_SPEED);
    }

    /**
     * Lifts the ball intake
     */
    public void setPositionUp() {
	solenoid.set(UP);
    }

    /**
     * Lowers the ball intake
     */
    public void setPositionDown() {
	solenoid.set(DOWN);
    }

    /**
     * @return if the intake is down
     */
    public boolean isPositionDown() {
	// System.out.println("Is ball intake down " + (solenoid.get() ==
	// DOWN));
	return solenoid.get() == DOWN;
    }

    /**
     * @return if the intake is up
     */
    public boolean isPositionUp() {
	return solenoid.get() == UP;
    }

    @Override
    public void addToSmartDashboard(MySmartDashboard dashboard) {
	String directory = NAME + "/";
	dashboard.addItem(SmartDashboardItem.newBooleanSender(directory + "Ball Intake is up?", this::isPositionUp));
	dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Ball Intake Output", motor::get));
    }
}
