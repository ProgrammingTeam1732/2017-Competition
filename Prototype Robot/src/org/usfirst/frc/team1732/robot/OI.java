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

	private final Button	gearIntakePositionIn		= new JoystickButton(buttons, 10);
	private final Button	gearIntakePositionOut		= new JoystickButton(buttons, 11);
	private final Button	gearIntakeJoystickInDown	= new JoystickButton(left, 1);
	private final Button	gearIntakeJoystickOutDown	= new JoystickButton(right, 1);

	public OI() {
		// joysticks
		gearIntakeJoystickOutDown.whenPressed(new GearIntakeSetDownOut());
		gearIntakeJoystickOutDown.whenReleased(new GearIntakeSetUpStop());
		gearIntakeJoystickInDown.whenPressed(new GearIntakeSetDownIn());
		// gearIntakeJoystickInDown.whenPressed(new CommandGroup() {
		// {
		// addSequential(new GrabGear(0, false));
		// addSequential(new GearIntakeSetUpTimedIn(1));
		// }
		// });
		gearIntakeJoystickInDown.whenReleased(new GearIntakeSetUpTimedIn(1));

		// button panel
		gearIntakePositionOut.whenPressed(new GearIntakeSetDownOut());
		gearIntakePositionOut.whenReleased(new GearIntakeSetUpStop());
		gearIntakePositionIn.whenPressed(new GearIntakeSetDownIn());
		gearIntakePositionIn.whenReleased(new GearIntakeSetUpTimedIn(1));

	}

	public double getLeftSpeed() {
		return -left.getRawAxis(RobotMap.LEFT_JOYSTICK_Y_AXIS);
	}

	public double getRightSpeed() {
		return -right.getRawAxis(RobotMap.RIGHT_JOYSTICK_Y_AXIS);
	}
}
