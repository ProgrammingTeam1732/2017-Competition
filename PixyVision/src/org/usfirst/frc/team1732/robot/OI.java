package org.usfirst.frc.team1732.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private final Joystick controller = new Joystick(0);

	public OI() {

	}

	public double getLeftSpeed() {
		return controller.getRawAxis(RobotMap.LEFT_JOYSTICK_AXIS);
	}

	public double getRightSpeed() {
		return controller.getRawAxis(RobotMap.RIGHT_JOYSTICK_AXIS);
	}
}
