package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

public class PixyCamera extends Subsystem {

	// private final CANTalon lightRelay = new Relay(RobotMap.RELAY_CHANNEL);
	private final CANTalon lightController = new CANTalon(RobotMap.LIGHT_MOTOR_DEVICE_NUMBER);

	public static final double	MAX_VOLTAGE	= 12;
	public static final double	MIN_VOLTAGE	= 0;

	public static final double	HORIZONTAL_FIELD_OF_VIEW	= 68;
	public static final double	VERTICAL_FIELD_OF_VIEW		= 47;

	public static final int	IMAGE_WIDTH		= 320;
	public static final int	IMAGE_HEIGHT	= 200;

	public PixyCamera() {
		// lightRelay.setDirection(Direction.kBoth);
		lightController.changeControlMode(TalonControlMode.Voltage);
		lightController.set(0);
	}

	@Override
	public void initDefaultCommand() {}

	public void turnOnLights() {
		lightController.set(limit(12));
	}

	public void turnOffLights() {
		lightController.set(limit(0));
	}

	public void setLightVoltage(double d) {
		lightController.set(limit(d));
	}

	public double limit(double d) {
		return d > MAX_VOLTAGE ? MAX_VOLTAGE : d < MIN_VOLTAGE ? MIN_VOLTAGE : d;
	}

	public void setLightsOn(boolean on) {
		if (on)
			turnOnLights();
		else
			turnOffLights();
	}

}
