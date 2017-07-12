package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.commands.ballsystem.ballandfeeder.IntakeBalls;
import org.usfirst.frc.team1732.robot.commands.ballsystem.ballandfeeder.OutputBalls;
import org.usfirst.frc.team1732.robot.commands.ballsystem.ballandfeeder.StopIntakeAndFeeder;
import org.usfirst.frc.team1732.robot.commands.ballsystem.feeder.FeederSetIn;
import org.usfirst.frc.team1732.robot.commands.ballsystem.feeder.FeederSetOut;
import org.usfirst.frc.team1732.robot.commands.ballsystem.feeder.FeederSetStop;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.DisableFlywheel;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.ballsystem.shooting.ShootWithShuffle;
import org.usfirst.frc.team1732.robot.commands.ballsystem.shooting.StopShoot;
import org.usfirst.frc.team1732.robot.commands.climber.ArmSetIn;
import org.usfirst.frc.team1732.robot.commands.climber.ArmSetOut;
import org.usfirst.frc.team1732.robot.commands.climber.ArmSetOutGroup;
import org.usfirst.frc.team1732.robot.commands.climber.ClimberSetDown;
import org.usfirst.frc.team1732.robot.commands.climber.ClimberSetStop;
import org.usfirst.frc.team1732.robot.commands.climber.ClimberSetUp;
import org.usfirst.frc.team1732.robot.commands.drivetrain.ShiftHigh;
import org.usfirst.frc.team1732.robot.commands.drivetrain.ShiftLow;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.stopper.GearIntakeSetStopperIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.stopper.GearIntakeSetStopperOut;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GearIntakeSetDownIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GearIntakeSetDownOut;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GearIntakeSetUpStop;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.TeleopGearIntakeSetUpTimeIn;
import org.usfirst.frc.team1732.robot.commands.wings.WingsSetIn;
import org.usfirst.frc.team1732.robot.commands.wings.WingsSetOut;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    private Joystick buttons = new Joystick(RobotMap.BUTTONS_USB);
    private Joystick left = new Joystick(RobotMap.LEFT_JOYSTICK_USB);
    private Joystick right = new Joystick(RobotMap.RIGHT_JOYSTICK_USB);

    private final Button climb = new JoystickButton(buttons, 1);
    private final Trigger climbNormal = newNormalButton(climb);
    private final Trigger climbOverride = newOverrideButton(climb);

    private final Button arm = new JoystickButton(buttons, 2);
    private final Trigger armNormal = newNormalButton(arm);
    private final Trigger armOverride = newOverrideButton(arm);
    // public static boolean isArmRunning = false;
    private Command ArmSetOutGroupLocal = new ArmSetOutGroup();
    private final Trigger armStopperOut = new Trigger() {

	@Override
	public boolean get() {
	    return Robot.gearIntake.isStopperOut() && armNormal.get(); // &&
	    // !isArmRunning;
	}

    };
    private final Trigger armStopperIn = new Trigger() {

	@Override
	public boolean get() {
	    return Robot.gearIntake.isStopperIn() && armNormal.get() && !ArmSetOutGroupLocal.isRunning(); // &&
	    // !isArmRunning;
	}

    };

    private final Button override = new JoystickButton(buttons, 5);

    private final Button feederIn = new JoystickButton(buttons, 7);
    // private final Trigger feederInNormal = newNormalButton(feederIn);
    // private final Trigger feederInOverride = newOverrideButton(feederIn);

    private final Button feederOut = new JoystickButton(buttons, 6);
    // private final Trigger feederOutNormal = newNormalButton(feederOut);
    // private final Trigger feederOutOverride = newOverrideButton(feederOut);

    private final Button ballIntakeOutButton = new JoystickButton(buttons, 8);
    // private final Trigger ballIntakeOutButtonNormal =
    // newNormalButton(ballIntakeOutButton);
    // private final Trigger ballIntakeOutButtonOverride =
    // newOverrideButton(ballIntakeOutButton);

    private final Button ballIntakeInButton = new JoystickButton(buttons, 9);
    // private final Trigger ballIntakeInButtonNormal =
    // newNormalButton(ballIntakeInButton);
    // private final Trigger ballIntakeInButtonOverride =
    // newOverrideButton(ballIntakeInButton);

    private final Button ballIntakeController = new JoystickButton(right, 3);

    private final Trigger ballIntakeIn = new Trigger() {
	@Override
	public boolean get() {
	    return ballIntakeInButton.get() || ballIntakeController.get();
	}
    };

    private final Trigger ballIntakeStop = new Trigger() {
	@Override
	public boolean get() {
	    return !ballIntakeIn.get() && !ballIntakeOutButton.get();
	}
    };

    private final Trigger flywheelOn = new Trigger() {
	private final Button flywheelSwitch = new JoystickButton(buttons, 10);

	@Override
	public boolean get() {
	    return !flywheelSwitch.get();
	}
    };

    // private final Trigger flywheelOnNormal = newNormalButton(flywheelOn);
    // private final Trigger flywheelOnOverride = newOverrideButton(flywheelOn);

    private final Button shoot = new JoystickButton(buttons, 11);
    // private final Trigger shootNormal = newNormalButton(shoot);
    // private final Trigger shootOverride = newOverrideButton(shoot);

    private final Button shifterLeft = new JoystickButton(left, 3);

    private final Button gearStopperOverrideIn = new JoystickButton(left, 10);
    private final Button gearStopperOverrideOut = new JoystickButton(left, 11);

    private final Button joystickGearPickup = new JoystickButton(left, 1);
    private final Button joystickGearScore = new JoystickButton(right, 1);

    private final Button buttonGearPickup = new JoystickButton(buttons, 3);
    // private final Trigger buttonGearPickupNormal =
    // newNormalButton(buttonGearPickup);
    // private final Trigger buttonGearPickupOverride =
    // newOverrideButton(buttonGearPickup);

    private final Button buttonGearScore = new JoystickButton(buttons, 4);
    // private final Trigger buttonGearScoreNormal =
    // newNormalButton(buttonGearScore);
    // private final Trigger buttonGearScoreOverride =
    // newOverrideButton(buttonGearScore);

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

    private final Button wings = new JoystickButton(right, 2);

    public OI() {
	gearPickup.whenActive(new GearIntakeSetDownIn());
	gearPickup.whenInactive(new TeleopGearIntakeSetUpTimeIn(1));
	gearScore.whenActive(new GearIntakeSetDownOut());
	gearScore.whenInactive(new GearIntakeSetUpStop());

	flywheelOn.whenActive(new EnableFlywheel());
	flywheelOn.whenInactive(new DisableFlywheel());

	shoot.whenPressed(new ShootWithShuffle());
	shoot.whenReleased(new StopShoot());

	// only normal mode
	armStopperIn.whenActive(new ArmSetOut());
	armStopperOut.whenActive(ArmSetOutGroupLocal);
	// only override mode
	armOverride.whenActive(ArmSetOutGroupLocal);
	// any mode
	arm.whenInactive(new ArmSetIn());

	climbNormal.whenActive(new ClimberSetUp());
	climbNormal.whenInactive(new ClimberSetStop());

	climbOverride.whenActive(new ClimberSetDown());
	climbOverride.whenInactive(new ClimberSetStop());

	ballIntakeIn.whenActive(new IntakeBalls());
	ballIntakeOutButton.whenPressed(new OutputBalls());
	ballIntakeStop.whenActive(new StopIntakeAndFeeder());

	shifterLeft.whenPressed(new ShiftLow());
	shifterLeft.whenReleased(new ShiftHigh());

	gearStopperOverrideIn.whenPressed(new GearIntakeSetStopperIn());
	gearStopperOverrideOut.whenPressed(new GearIntakeSetStopperOut());

	feederIn.whenActive(new FeederSetIn());
	feederIn.whenInactive(new FeederSetStop());

	feederOut.whenActive(new FeederSetOut());
	feederOut.whenInactive(new FeederSetStop());

	wings.whenPressed(new WingsSetOut());
	wings.whenReleased(new WingsSetIn());
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

    public boolean overridePressed() {
	return override.get();
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
