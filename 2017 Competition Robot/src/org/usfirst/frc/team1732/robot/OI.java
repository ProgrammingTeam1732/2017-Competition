package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.commands.ballandfeeder.IntakeBalls;
import org.usfirst.frc.team1732.robot.commands.ballandfeeder.OutputBalls;
import org.usfirst.frc.team1732.robot.commands.ballandfeeder.StopIntakeAndFeeder;
import org.usfirst.frc.team1732.robot.commands.climber.ArmSetIn;
import org.usfirst.frc.team1732.robot.commands.climber.ArmSetOut;
import org.usfirst.frc.team1732.robot.commands.climber.ClimberSetStop;
import org.usfirst.frc.team1732.robot.commands.climber.ClimberSetUp;
import org.usfirst.frc.team1732.robot.commands.drivetrain.ShiftHigh;
import org.usfirst.frc.team1732.robot.commands.drivetrain.ShiftLow;
import org.usfirst.frc.team1732.robot.commands.flywheel.DisableFlywheel;
import org.usfirst.frc.team1732.robot.commands.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.flywheel.Shoot;
import org.usfirst.frc.team1732.robot.commands.flywheel.StopShoot;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetDownIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetDownOut;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetUpStop;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetUpTimedIn;

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

	private final Button	gearIntakeJoystickInDown	= new JoystickButton(left, 1);
	private final Button	gearIntakeJoystickOutDown	= new JoystickButton(right, 1);

	private final Button	ballIntakeIn	= new JoystickButton(buttons, 0);
	private final Button	ballIntakeOut	= new JoystickButton(buttons, 0);

	/**
	 * This Trigger returns true only if both buttons aren't active This is so
	 * that if both get pressed at the same time and one gets released it won't
	 * stop the ball intake
	 */
	private final Trigger ballIntakeStop = new Trigger() {
		@Override
		public boolean get() {
			return !ballIntakeIn.get() && !ballIntakeOut.get();
		}
	};

	private final Button	flywheelSwitch	= new JoystickButton(buttons, 0);
	private final Button	shootButton		= new JoystickButton(buttons, 0);

	private final Button	armPosistion	= new JoystickButton(buttons, 0);
	private final Button	climber			= new JoystickButton(buttons, 0);

	private final Button shifter = new JoystickButton(buttons, 0);

	/**
	 * Calling the OI constructor will bind the buttons to commands
	 */
	public OI() {
		gearIntakeJoystickOutDown.whenPressed(new GearIntakeSetDownOut());
		gearIntakeJoystickOutDown.whenReleased(new GearIntakeSetUpStop());
		gearIntakeJoystickInDown.whenPressed(new GearIntakeSetDownIn());
		gearIntakeJoystickInDown.whenReleased(new GearIntakeSetUpTimedIn(1));

		flywheelSwitch.whenPressed(new EnableFlywheel());
		flywheelSwitch.whenReleased(new DisableFlywheel());

		shootButton.whenPressed(new Shoot());
		shootButton.whenReleased(new StopShoot());

		armPosistion.whenPressed(new ArmSetOut());
		armPosistion.whenReleased(new ArmSetIn());

		climber.whenPressed(new ClimberSetUp());
		climber.whenReleased(new ClimberSetStop());

		ballIntakeIn.whenPressed(new IntakeBalls());
		ballIntakeOut.whenPressed(new OutputBalls());
		ballIntakeStop.whenActive(new StopIntakeAndFeeder());

		shifter.whenPressed(new ShiftLow());
		shifter.whenReleased(new ShiftHigh());
	}

	public double getLeftSpeed() {
		// return -controller.getRawAxis(1);// for use with game controller
		return -left.getRawAxis(RobotMap.LEFT_JOYSTICK_Y_AXIS);
	}

	public double getRightSpeed() {
		// return -controller.getRawAxis(3);// for use with game controller
		return -right.getRawAxis(RobotMap.RIGHT_JOYSTICK_Y_AXIS);
	}

	// Buttons from controller
	private final Joystick	controller	= new Joystick(0);
	private final Button	a			= new JoystickButton(controller, 2);
	private final Button	b			= new JoystickButton(controller, 3);
	private final Button	x			= new JoystickButton(controller, 1);
	private final Button	y			= new JoystickButton(controller, 4);
	private final Button	lb			= new JoystickButton(controller, 5);
	private final Button	rb			= new JoystickButton(controller, 6);

	// Buttons from last year's driver station:
	private final Button	fingerSwitch	= new JoystickButton(buttons, 12);
	private final Button	intakeIn		= new JoystickButton(buttons, 10);
	private final Button	intakeOut		= new JoystickButton(buttons, 11);
	private final Button	intakeSwitch	= new JoystickButton(buttons, 9);
	private final Button	armHigh			= new JoystickButton(buttons, 4);
	private final Button	armMedium		= new JoystickButton(buttons, 5);
	private final Button	armLow			= new JoystickButton(buttons, 6);
	private final Button	shootFar		= new JoystickButton(buttons, 2);
	private final Button	shootClose		= new JoystickButton(buttons, 3);
	private final Button	shoot			= new JoystickButton(buttons, 1);
	private final Button	manipUp			= new JoystickButton(buttons, 8);
	private final Button	manipDown		= new JoystickButton(buttons, 7);
}
