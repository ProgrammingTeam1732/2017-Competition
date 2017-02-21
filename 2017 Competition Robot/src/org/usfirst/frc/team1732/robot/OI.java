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
		return -left.getRawAxis(RobotMap.LEFT_JOYSTICK_Y_AXIS);
	}

	public double getRightSpeed() {
		return -right.getRawAxis(RobotMap.RIGHT_JOYSTICK_Y_AXIS);
	}
}
