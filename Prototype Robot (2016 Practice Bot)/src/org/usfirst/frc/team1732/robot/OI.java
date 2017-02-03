package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.commands.FlywheelBangBang;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private final Joystick	left	= new Joystick(RobotMap.LEFT_JOYSTICK_USB);
	private final Joystick	right	= new Joystick(RobotMap.RIGHT_JOYSTICK_USB);
	private final Joystick	buttons	= new Joystick(RobotMap.BUTTONS_USB);

	private final Button enableShooter = new JoystickButton(buttons, 2);

	public OI() {
		enableShooter.whileHeld(new FlywheelBangBang());
	}

	public double getLeftSpeed() {
		return left.getRawAxis(RobotMap.LEFT_JOYSTICK_Y_AXIS);
	}

	public double getRightSpeed() {
		return right.getRawAxis(RobotMap.RIGHT_JOYSTICK_Y_AXIS);
	}
}
