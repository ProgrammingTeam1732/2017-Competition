package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.commands.ballandfeeder.IntakeBalls;
import org.usfirst.frc.team1732.robot.commands.ballandfeeder.OutputBalls;
import org.usfirst.frc.team1732.robot.commands.ballandfeeder.StopIntakeAndFeeder;
import org.usfirst.frc.team1732.robot.commands.climber.ArmSetIn;
import org.usfirst.frc.team1732.robot.commands.climber.ArmSetOutGroup;
import org.usfirst.frc.team1732.robot.commands.climber.ClimberSetDown;
import org.usfirst.frc.team1732.robot.commands.climber.ClimberSetStop;
import org.usfirst.frc.team1732.robot.commands.climber.ClimberSetUp;
import org.usfirst.frc.team1732.robot.commands.drivetrain.ShiftHigh;
import org.usfirst.frc.team1732.robot.commands.drivetrain.ShiftLow;
import org.usfirst.frc.team1732.robot.commands.flywheel.DisableFlywheel;
import org.usfirst.frc.team1732.robot.commands.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.flywheel.Shoot;
import org.usfirst.frc.team1732.robot.commands.flywheel.StopShoot;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetStopperIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetStopperOut;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GearIntakeSetDownIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GearIntakeSetDownOut;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GearIntakeSetUpStop;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GearIntakeSetUpTimedIn;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	private Joystick	buttons	= new Joystick(RobotMap.BUTTONS_USB);
	private Joystick	left	= new Joystick(RobotMap.LEFT_JOYSTICK_USB);
	private Joystick	right	= new Joystick(RobotMap.RIGHT_JOYSTICK_USB);

	// Buttons from last year's driver station:
	// private final Button fingerSwitch = new JoystickButton(buttons, 12);
	// private final Button intakeIn = new JoystickButton(buttons, 10);
	// private final Button intakeOut = new JoystickButton(buttons, 11);
	// private final Button intakeSwitch = new JoystickButton(buttons, 9);
	// private final Button armHigh = new JoystickButton(buttons, 4);
	// private final Button armMedium = new JoystickButton(buttons, 5);
	// private final Button armLow = new JoystickButton(buttons, 6);
	// private final Button shootFar = new JoystickButton(buttons, 2);
	// private final Button shootClose = new JoystickButton(buttons, 3);
	// private final Button shoot = new JoystickButton(buttons, 1);
	// private final Button manipUp = new JoystickButton(buttons, 8);
	// private final Button manipDown = new JoystickButton(buttons, 7);

	// Buttons for logitech controller
	// private final Joystick buttons = new Joystick(RobotMap.BUTTONS_USB);
	// private final Button Y = new JoystickButton(buttons, 4);
	// private final Button LB = new JoystickButton(buttons, 5);
	// private final Button RB = new JoystickButton(buttons, 6);
	// private final Button A = new JoystickButton(buttons, 2);
	// private final Button B = new JoystickButton(buttons, 3);
	// private final Button X = new JoystickButton(buttons, 1);
	// private final Button RT = new JoystickButton(buttons, 8);
	// private final Button LT = new JoystickButton(buttons, 7);
	// private final Button Start = new JoystickButton(buttons, 10);

	// Buttons For This Year's Driver Station
	private final Button	climbButton		= new JoystickButton(buttons, 1);
	private final Trigger	climbNormal		= makeNormalButton(climbButton);
	private final Trigger	climbOverride	= makeOverrideButton(climbButton);

	private final Button	craaw			= new JoystickButton(buttons, 2);
	private final Button	gearPickup		= new JoystickButton(buttons, 3);
	private final Button	gearScore		= new JoystickButton(buttons, 4);
	private final Button	override		= new JoystickButton(buttons, 5);
	private final Button	conveyorUp		= new JoystickButton(buttons, 6);
	private final Button	conveyorDown	= new JoystickButton(buttons, 7);
	private final Button	intakeOut		= new JoystickButton(buttons, 8);
	private final Button	intakeIn		= new JoystickButton(buttons, 9);
	private final Button	flywheelOn		= new JoystickButton(buttons, 10);
	private final Button	shoot			= new JoystickButton(buttons, 11);

	private final Button	gearIntakeJoystickInDown	= new JoystickButton(left, 1);
	private final Button	gearIntakeJoystickOutDown	= new JoystickButton(right, 1);

	// private final Button ballIntakeIn = intakeIn; // new
	// JoystickButton(buttons,
	// 0);
	// private final Button ballIntakeOut = intakeOut; // new
	// JoystickButton(buttons,
	// 0);

	/**
	 * This Trigger returns true only if both buttons aren't active This is so
	 * that if both get pressed at the same time and one gets released it won't
	 * stop the ball intake
	 */
	private final Trigger ballIntakeStop = new Trigger() {
		@Override
		public boolean get() {
			return !intakeIn.get() && !intakeOut.get();
		}
	};

	// private final Button flywheelSwitch = new JoystickButton(buttons, 0);
	// private final Button shootButton = new JoystickButton(buttons, 0);

	// private final Button armPosistion = fingerSwitch; // new
	// JoystickButton(buttons,
	// 0);
	// private final Button climber = shoot; // new
	// JoystickButton(buttons,
	// 1);
	// private final Button climberReverse = armHigh;

	private final Button shifter = new JoystickButton(left, 3);

	private final Button	gearStopperOverrideIn	= new JoystickButton(left, 10);
	private final Button	gearStopperOverrideOut	= new JoystickButton(left, 11);
	// private final Button GearStop = intakeSwitch;

	// private final Button GearManipulatorOut = A;
	// private final Button GearManipulatorIn = B;
	//
	// private final Button GearManipulatorStopOut = Y;
	// private final Button GearManipulatorStopIn = X;

	/**
	 * Calling the OI constructor will bind the buttons to commands
	 */
	public OI() {
		gearIntakeJoystickOutDown.whenPressed(new GearIntakeSetDownOut());
		gearIntakeJoystickOutDown.whenReleased(new GearIntakeSetUpStop());
		gearIntakeJoystickInDown.whenPressed(new GearIntakeSetDownIn());
		gearIntakeJoystickInDown.whenReleased(new GearIntakeSetUpTimedIn(1));

		gearPickup.whenPressed(new GearIntakeSetDownIn());
		gearPickup.whenReleased(new GearIntakeSetUpTimedIn(1));
		gearScore.whenPressed(new GearIntakeSetDownOut());
		gearScore.whenReleased(new GearIntakeSetUpStop());

		gearStopperOverrideIn.whenPressed(new GearIntakeSetStopperIn());
		gearStopperOverrideOut.whenPressed(new GearIntakeSetStopperOut());

		// GearManipulatorOut.whenPressed(new GearIntakeSetDownOut());
		// GearManipulatorOut.whenReleased(new GearIntakeSetUpStop());
		// GearManipulatorIn.whenPressed(new GearIntakeSetDownIn());
		// GearManipulatorIn.whenReleased(new GearIntakeSetUpTimedIn(1));
		//
		// GearManipulatorStopOut.whenPressed(new GearIntakeSetStopperOut());
		// GearManipulatorStopIn.whenPressed(new GearIntakeSetStopperIn());

		// override.whenPressed(new ClimberSetDown());
		// override.whenReleased(new ClimberSetStop());
		flywheelOn.whenPressed(new DisableFlywheel());
		flywheelOn.whenReleased(new EnableFlywheel());

		shoot.whenPressed(new Shoot());
		shoot.whenReleased(new StopShoot());

		craaw.whenPressed(new ArmSetOutGroup());
		craaw.whenReleased(new ArmSetIn());

		climbNormal.whenActive(new ClimberSetUp());
		climbNormal.whenInactive(new ClimberSetStop());

		climbOverride.whenActive(new ClimberSetDown());
		climbOverride.whenInactive(new ClimberSetStop());

		intakeIn.whenPressed(new IntakeBalls()); // FIXME: These are reversed
													// just to save time
		intakeOut.whenPressed(new OutputBalls());
		ballIntakeStop.whenActive(new StopIntakeAndFeeder());

		shifter.whenPressed(new ShiftLow());
		shifter.whenReleased(new ShiftHigh());

	}

	public double getLeftSpeed() {
		// return -controller.getRawAxis(1);// for use with game controller
		return -left.getRawAxis(RobotMap.LEFT_JOYSTICK_Y_AXIS);
		// return -buttons.getRawAxis(1);
	}

	public double getRightSpeed() {
		// return -controller.getRawAxis(3);// for use with game controller
		return -right.getRawAxis(RobotMap.RIGHT_JOYSTICK_Y_AXIS);
		// return -buttons.getRawAxis(3);
	}

	// Buttons from controller
	// private final Joystick controller = new Joystick(0);
	// private final Button A = new JoystickButton(controller, 2);
	// private final Button B = new JoystickButton(controller, 3);
	// private final Button X = new JoystickButton(controller, 1);
	// private final Button Y = new JoystickButton(controller, 4);
	// private final Button LB = new JoystickButton(controller, 5);
	// private final Button RB = new JoystickButton(controller, 6);

	private Trigger makeNormalButton(Button b) {
		return new Trigger() {
			@Override
			public boolean get() {
				return !override.get() && b.get();
			}
		};
	}

	private Trigger makeOverrideButton(Button b) {
		return new Trigger() {
			@Override
			public boolean get() {
				return override.get() && b.get();
			}
		};
	}
}
