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
import org.usfirst.frc.team1732.robot.commands.feeder.FeederSetIn;
import org.usfirst.frc.team1732.robot.commands.feeder.FeederSetStop;
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

	private final Button	climb			= new JoystickButton(buttons, 1);
	private final Trigger	climbNormal		= newNormalButton(climb);
	private final Trigger	climbOverride	= newOverrideButton(climb);

	private final Button	craaw			= new JoystickButton(buttons, 2);
	private final Trigger	craawNormal		= newNormalButton(craaw);
	private final Trigger	craawOverride	= newOverrideButton(craaw);

	private final Button override = new JoystickButton(buttons, 5);

	private final Button	conveyorIn			= new JoystickButton(buttons, 6);
	private final Trigger	conveyorInNormal	= newNormalButton(conveyorIn);
	private final Trigger	conveyorInOverride	= newOverrideButton(conveyorIn);

	private final Button	conveyorOut			= new JoystickButton(buttons, 7);
	private final Trigger	conveyorOutNormal	= newNormalButton(conveyorOut);
	private final Trigger	conveyorOutOverride	= newOverrideButton(conveyorOut);

	private final Button	intakeOut			= new JoystickButton(buttons, 8);
	private final Trigger	intakeOutNormal		= newNormalButton(intakeOut);
	private final Trigger	intakeOutOverride	= newOverrideButton(intakeOut);

	private final Button	intakeIn			= new JoystickButton(buttons, 9);
	private final Trigger	intakeInNormal		= newNormalButton(intakeIn);
	private final Trigger	intakeInOverride	= newOverrideButton(intakeIn);

	private final Trigger intakeStop = new Trigger() {
		@Override
		public boolean get() {
			return !intakeIn.get() && !intakeOut.get();
		}
	};

	private final Trigger flywheelOn = new Trigger() {
		private final Button flywheelSwitch = new JoystickButton(buttons, 10);

		@Override
		public boolean get() {
			return !flywheelSwitch.get();
		}
	};

	private final Trigger	flywheelOnNormal	= newNormalButton(flywheelOn);
	private final Trigger	flywheelOnOverride	= newOverrideButton(flywheelOn);

	private final Button	shoot			= new JoystickButton(buttons, 11);
	private final Trigger	shootNormal		= newNormalButton(shoot);
	private final Trigger	shootOverride	= newOverrideButton(shoot);

	private final Button	shifter					= new JoystickButton(left, 3);
	private final Button	gearStopperOverrideIn	= new JoystickButton(left, 10);
	private final Button	gearStopperOverrideOut	= new JoystickButton(left, 11);

	private final Button	joystickGearPickup	= new JoystickButton(left, 1);
	private final Button	joystickGearScore	= new JoystickButton(right, 1);

	private final Button	buttonGearPickup			= new JoystickButton(buttons, 3);
	private final Trigger	buttonGearPickupNormal		= newNormalButton(buttonGearPickup);
	private final Trigger	buttonGearPickupOverride	= newOverrideButton(buttonGearPickup);

	private final Button	buttonGearScore			= new JoystickButton(buttons, 4);
	private final Trigger	buttonGearScoreNormal	= newNormalButton(buttonGearScore);
	private final Trigger	buttonGearScoreOverride	= newOverrideButton(buttonGearScore);

	private final Trigger gearScore = new Trigger() {
		@Override
		public boolean get() {
			return buttonGearScore.get() || joystickGearScore.get();
		}
	};

	private final Trigger gearPickup = new Trigger() {
		@Override
		public boolean get() {
			return buttonGearPickup.get() || joystickGearPickup.get();
		}
	};

	public OI() {
		gearPickup.whenActive(new GearIntakeSetDownIn());
		gearPickup.whenInactive(new GearIntakeSetUpTimedIn(1));
		gearScore.whenActive(new GearIntakeSetDownOut());
		gearScore.whenInactive(new GearIntakeSetUpStop());

		flywheelOn.whenActive(new EnableFlywheel());
		flywheelOn.whenInactive(new DisableFlywheel());

		shoot.whenPressed(new Shoot());
		shoot.whenReleased(new StopShoot());

		craaw.whenPressed(new ArmSetOutGroup());
		craaw.whenReleased(new ArmSetIn());

		climbNormal.whenActive(new ClimberSetUp());
		climbNormal.whenInactive(new ClimberSetStop());

		climbOverride.whenActive(new ClimberSetDown());
		climbOverride.whenInactive(new ClimberSetStop());

		intakeIn.whenPressed(new IntakeBalls());
		intakeOut.whenPressed(new OutputBalls());
		intakeStop.whenActive(new StopIntakeAndFeeder());

		shifter.whenPressed(new ShiftLow());
		shifter.whenReleased(new ShiftHigh());

		gearStopperOverrideIn.whenPressed(new GearIntakeSetStopperIn());
		gearStopperOverrideOut.whenPressed(new GearIntakeSetStopperOut());

		conveyorInOverride.whenActive(new FeederSetIn());
		conveyorInOverride.whenInactive(new FeederSetStop());

		conveyorOutOverride.whenActive(new FeederSetIn());
		conveyorOutOverride.whenInactive(new FeederSetStop());
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

	private Trigger newNormalButton(Trigger b) {
		return new Trigger() {
			@Override
			public boolean get() {
				return !override.get() && b.get();
			}
		};
	}

	private Trigger newOverrideButton(Trigger b) {
		return new Trigger() {
			@Override
			public boolean get() {
				return override.get() && b.get();
			}
		};
	}

	// Buttons from controller
	// private final Joystick controller = new Joystick(0);
	// private final Button A = new JoystickButton(controller, 2);
	// private final Button B = new JoystickButton(controller, 3);
	// private final Button X = new JoystickButton(controller, 1);
	// private final Button Y = new JoystickButton(controller, 4);
	// private final Button LB = new JoystickButton(controller, 5);
	// private final Button RB = new JoystickButton(controller, 6);
}
