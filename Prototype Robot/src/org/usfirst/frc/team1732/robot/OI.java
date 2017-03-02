package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetDownIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetDownOut;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetUpStop;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetUpTimedIn;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private Joystick	buttons	= new Joystick(RobotMap.BUTTONS_USB);
	private Joystick	left	= new Joystick(RobotMap.LEFT_JOYSTICK_USB);
	private Joystick	right	= new Joystick(RobotMap.RIGHT_JOYSTICK_USB);
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
	private final Button	gearIntakePositionIn	= new JoystickButton(buttons, 10);
	private final Button 	gearIntakePositionOut   = new JoystickButton(buttons, 11);
	private final Button	gearIntakeJoystickInDown	= new JoystickButton(left, 1);
	private final Button	gearIntakeJoystickOutDown	= new JoystickButton(right, 1);
	//private final Button	autoPlace					= new JoystickButton(buttons, 10);
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
		// gearIntakeReverse.whileHeld(new IntakeInDown());
		// gearIntakeForward.whileHeld(new IntakeOutDown());
		gearIntakeJoystickOutDown.whenPressed(new GearIntakeSetDownOut());
		gearIntakeJoystickOutDown.whenReleased(new GearIntakeSetUpStop());
		gearIntakeJoystickInDown.whenPressed(new GearIntakeSetDownIn());
		gearIntakeJoystickInDown.whenReleased(new GearIntakeSetUpTimedIn(1));
		gearIntakePositionOut.whenPressed(new GearIntakeSetDownOut());
		gearIntakePositionOut.whenReleased(new GearIntakeSetUpStop());
		gearIntakePositionIn.whenPressed(new GearIntakeSetDownIn());
		gearIntakePositionIn.whenReleased(new GearIntakeSetUpTimedIn(1));
		// gearIntakeReverse.whenReleased(new IntakeInDownTimer());
		// gearIntakeForward.whenReleased(new IntakeOutDownTimer());
		// gearIntakeForward.whenReleased(new GearIntakeSetUp());
		// gearIntakeReverse.whenPressed(new GearIntakeSetReverse());
		// gearIntakeStop.whenActive(new GearIntakeSetStop());
		// gearIntakePosistionSwitch.whenPressed(new GearIntakeSetUp());
		// gearIntakePosistionSwitch.whenReleased(new GearIntakeSetDown());
		//
		// climber.whenPressed(new ClimberSetUp());
		// climber.whenReleased(new ClimberSetStop());
		//
		// otherShooter.whenPressed(new OtherShooterShoot());
		// otherShooter.whenReleased(new OtherShooterReset());
	}

	public double getLeftSpeed() {
		return -left.getRawAxis(RobotMap.LEFT_JOYSTICK_Y_AXIS);
	}

	public double getRightSpeed() {
		return -right.getRawAxis(RobotMap.RIGHT_JOYSTICK_Y_AXIS);
	}
}
