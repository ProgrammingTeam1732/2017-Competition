package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.commands.DisableFlywheel;
import org.usfirst.frc.team1732.robot.commands.EnableFlywheel;

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

	private final Button enableShooter = new JoystickButton(buttons, 12);

	// makes it easier to switch around buttons and functions

	// private final Button ballIntakeForward = button1;
	// private final Button ballIntakeReverse = button2;
	// private final Trigger ballIntakeStop = new Trigger() {
	// @Override
	// public boolean get() {
	// return !(ballIntakeForward.get() || ballIntakeReverse.get());
	// }
	// };
	//
	// private final Button feederForward = button3;
	// private final Button feederReverse = button4;
	// private final Trigger feederStop = new Trigger() {
	// @Override
	// public boolean get() {
	// return !(feederForward.get() || feederReverse.get());
	// }
	// };
	//
	// private final Button flywheelForward = button5;
	// private final Button flywheelReverse = button6;
	// private final Trigger flywheelStop = new Trigger() {
	// @Override
	// public boolean get() {
	// return !(flywheelForward.get() || flywheelReverse.get());
	// }
	// };
	//
	// private final Button gearIntakeForward = button7;
	// private final Button gearIntakeReverse = button8;
	// private final Trigger gearIntakeStop = new Trigger() {
	// @Override
	// public boolean get() {
	// return !(gearIntakeForward.get() || gearIntakeReverse.get());
	// }
	// };

	// private final Button climber = button9;
	//
	// private final Button otherShooter = button10;

	public OI() {
		enableShooter.whenPressed(new EnableFlywheel());
		enableShooter.whenReleased(new DisableFlywheel());

		// ballIntakeForward.whenPressed(new BallIntakeSetForward());
		// ballIntakeReverse.whenPressed(new BallIntakeSetReverse());
		// ballIntakeStop.whenActive(new BallIntakeSetStop());
		//
		// feederForward.whenPressed(new FeederSetForward());
		// feederReverse.whenPressed(new FeederSetReverse());
		// feederStop.whenActive(new FeederSetStop());
		//
		// flywheelForward.whenPressed(new FlywheelSetForward());
		// flywheelReverse.whenPressed(new FlywheelSetReverse());
		// flywheelStop.whenActive(new FlywheelSetStop());
		//
		// gearIntakeForward.whenPressed(new GearIntakeSetForward());
		// gearIntakeReverse.whenPressed(new GearIntakeSetReverse());
		// gearIntakeStop.whenActive(new GearIntakeSetStop());
		//
		// climber.whenPressed(new ClimberSetUp());
		// climber.whenReleased(new ClimberSetStop());
		//
		// otherShooter.whenPressed(new OtherShooterShoot());
		// otherShooter.whenReleased(new OtherShooterReset());
	}

	public double getLeftSpeed() {
		return left.getRawAxis(RobotMap.LEFT_JOYSTICK_Y_AXIS);
	}

	public double getRightSpeed() {
		return right.getRawAxis(RobotMap.RIGHT_JOYSTICK_Y_AXIS);
	}
}
