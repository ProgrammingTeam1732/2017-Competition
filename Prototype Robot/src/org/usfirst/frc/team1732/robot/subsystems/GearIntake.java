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

	private final CANTalon		motor				= new CANTalon(RobotMap.GEAR_INTAKE_MOTOR_DEVICE_NUMBER);
	public static final double	OUT_SPEED			= -.5;
	public static final double	STOP_SPEED			= 0;
	public static final double	IN_SPEED			= 0.6;
	private final Solenoid		gearManipulator		= new Solenoid(	RobotMap.PCM_CAN_ID,
																	RobotMap.GEAR_MANIPULATOR_SOLENOID_NUMBER);
	private final Solenoid		gearManipStorage	= new Solenoid(	RobotMap.PCM_CAN_ID,
																	RobotMap.GEAR_MANIPULATOR_STORAGE_NUMBER);
	public static final boolean	UP					= false;
	public static final boolean	DOWN				= true;
	public static final boolean	IN					= true;
	public static final boolean	OUT					= false;

	public static final String NAME = "Gear Intake";

	@Override
	public void initDefaultCommand() {}

	public void setDown() {
		gearManipulator.set(DOWN);
	}

	public void setUp() {
		gearManipulator.set(UP);
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

	public boolean isDown() {
		return gearManipulator.get() == DOWN;
	}

	public boolean isUp() {
		return gearManipulator.get() == UP;
	}

	public void setStorageIn() {
		gearManipStorage.set(IN);
	}

	public void setStorageOut() {
		gearManipStorage.set(OUT);
	}

	public boolean isStorageIn() {
		return gearManipStorage.get() == IN;
	}

	@Override
	public void addToSmartDashboard(MySmartDashboard dashboard) {
		String directory = NAME + "/";
		dashboard.addItem(SmartDashboardItem.newBooleanSender(directory + "Gear manipulator is up?", this::isUp));
		dashboard.addItem(SmartDashboardItem.newBooleanSender(directory + "Gear storage is in?", this::isStorageIn));
		dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Gear rollers output", motor::get));
		dashboard.addItem(SmartDashboardItem.newNumberSender(directory + "Gear motor current", this::getMotorCurrent));
	}

	public double getMotorCurrent() {
		return motor.getOutputCurrent();
	}

	public static final double GEAR_IN_CURRENT_CUTOFF = 12;

	public boolean gearIsIn() {
		return getMotorCurrent() > GEAR_IN_CURRENT_CUTOFF;
	}
}
