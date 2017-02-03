package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.commands.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.GearIntakeSetForward;
import org.usfirst.frc.team1732.robot.commands.GearIntakeSetReverse;
import org.usfirst.frc.team1732.robot.commands.GearIntakeSetStop;
import org.usfirst.frc.team1732.robot.commands.GearIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.IntakeInDown;
import org.usfirst.frc.team1732.robot.commands.IntakeInDownTimer;
import org.usfirst.frc.team1732.robot.commands.IntakeOutDown;
import org.usfirst.frc.team1732.robot.commands.IntakeOutDownTimer;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private Joystick buttons = new Joystick(RobotMap.BUTTONS_USB);
	private Joystick left = new Joystick(RobotMap.LEFT_JOYSTICK_USB);
	private Joystick right = new Joystick(RobotMap.RIGHT_JOYSTICK_USB);
	//
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
	private final Button gearIntakePosistionSwitch = new JoystickButton(buttons, 9);
	private final Button gearIntakeJoystickForward = new JoystickButton(left, 1);
	private final Button gearIntakeJoystickReverse = new JoystickButton(right, 1);
	private final Button gearIntakeForward = new JoystickButton(buttons, 11);
	private final Button gearIntakeReverse = new JoystickButton(buttons, 10);;
	private final Trigger gearIntakeStop = new Trigger() {
		@Override
		public boolean get() {
			return !(gearIntakeForward.get() || gearIntakeReverse.get());
		}
	};
	//
	// private final Button climber = button9;
	//
	// private final Button otherShooter = button10;

	public OI() {
		// ballIntakeForward.whenPressed(new BallIntakeSetForward());
		// ballIntakeReverse.whenPressed(new BallIntakeSetReverse());
		// ballIntakeStop.whenActive(new BallIntakeSetStop());
		//
		// feederForward.whenPressed(new FeederSetForward());
		// feederReverse.whenPressed(new FeederSetReverse());
		// feederStop.whenActive(new FeederSetStop());
		// //
		// flywheelForward.whenPressed(new FlywheelSetForward());
		// flywheelReverse.whenPressed(new FlywheelSetReverse());
		// flywheelStop.whenActive(new FlywheelSetStop());
		//
//		gearIntakeReverse.whileHeld(new IntakeInDown());
//		gearIntakeForward.whileHeld(new IntakeOutDown());
		gearIntakeJoystickReverse.whileHeld(new IntakeInDown());
		gearIntakeJoystickForward.whileHeld(new IntakeOutDown());
//		gearIntakeReverse.whenReleased(new IntakeInDownTimer());
//		gearIntakeForward.whenReleased(new IntakeOutDownTimer());
		//gearIntakeForward.whenReleased(new GearIntakeSetUp());
		//gearIntakeReverse.whenPressed(new GearIntakeSetReverse());
		//gearIntakeStop.whenActive(new GearIntakeSetStop());
		//gearIntakePosistionSwitch.whenPressed(new GearIntakeSetUp());
		//gearIntakePosistionSwitch.whenReleased(new GearIntakeSetDown());
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
