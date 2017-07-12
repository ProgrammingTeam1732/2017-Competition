package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardGroup;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearIntake extends Subsystem implements SmartDashboardGroup {

    private final CANTalon motor = new CANTalon(RobotMap.GEAR_INTAKE_MOTOR_DEVICE_NUMBER);
    public static final double OUT_SPEED = .6; // 0.5
    public static final double STOP_SPEED = 0;
    public static final double IN_SPEED = -0.7; // -0.6
    public static final double HOLD_SPEED = -0.15; // -0.6
    private final Solenoid gearPosition = new Solenoid(RobotMap.PCM_CAN_ID, RobotMap.GEAR_POSITION_SOLENOID_NUMBER);
    private final Solenoid gearStopper = new Solenoid(RobotMap.PCM_CAN_ID, RobotMap.GEAR_STOPPER_SOLENOID_NUMBER);
    // posistion
    public static final boolean UP = false;
    public static final boolean DOWN = !UP;
    // stopper
    public static final boolean IN = false; // false
    public static final boolean OUT = !IN;

    public static final String NAME = "Gear Intake";

    @Override
    public void initDefaultCommand() {
    }

    public void setDown() {
	gearPosition.set(DOWN);
    }

    public void setUp() {
	gearPosition.set(UP);
    }

    public void setOut() {
	motor.set(OUT_SPEED);
    }

    public void setStop() {
	motor.set(STOP_SPEED);
    }

    public void setIn() {
	motor.set(IN_SPEED);
    }

    public void setHold() {
	motor.set(HOLD_SPEED);
    }

    public boolean isDown() {
	return gearPosition.get() == DOWN;
    }

    public boolean isUp() {
	// System.out.println("Is gear up " + (gearPosition.get() == UP));
	return gearPosition.get() == UP;
    }

    public void setStopperIn() {
	gearStopper.set(IN);
    }

    public void setStopperOut() {
	gearStopper.set(OUT);
    }

    public boolean isStopperIn() {
	return gearStopper.get() == IN;
    }

    public boolean isStopperOut() {
	// System.out.println("is stopper out: " + (gearStopper.get() == OUT));
	return gearStopper.get() == OUT;
    }

    @Override
    public void addToSmartDashboard(MySmartDashboard dashboard) {
	String directory = NAME + "/";
	dashboard.addItem(SmartDashboardItem.newBooleanSender(directory + "Gear manipulator is up?", this::isUp));
	dashboard.addItem(SmartDashboardItem.newBooleanSender(directory + "Gear stopper is in?", this::isStopperIn));
	dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Gear rollers output", motor::get));
	dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Gear motor current", this::getMotorCurrent));
	dashboard.addItem(SmartDashboardItem.newBooleanSender(directory + "Gear is in", this::gearIsIn));
	dashboard.addItem(SmartDashboardItem.newBooleanSender(directory + "Gear is held", this::gearIsHeld));
    }

    public double getMotorCurrent() {
	return motor.getOutputCurrent();
    }

    public static final double GEAR_IN_CURRENT_CUTOFF = 12; // 14
    public static final double GEAR_IN_HOLD_CURRENT_CUTOFF = 0; // 14

    public boolean gearIsIn() {
	return getMotorCurrent() > GEAR_IN_CURRENT_CUTOFF;
    }

    public boolean gearIsHeld() {
	return getMotorCurrent() > GEAR_IN_HOLD_CURRENT_CUTOFF;
    }
}
