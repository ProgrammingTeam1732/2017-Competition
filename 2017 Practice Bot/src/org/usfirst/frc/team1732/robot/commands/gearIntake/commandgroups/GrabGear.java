package org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeInCurrentDistance;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeInCurrentTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetDown;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GrabGear extends CommandGroup {

	/**
	 * @param useTimeout
	 *            Whether the stopPoint is time or distance, <br>
	 *            If stopPoint is time, useTimeout should be true, <br>
	 *            If stopPoint is distance, useTimeout should be false
	 * @param stopPoint
	 *            The command will end if no gear is found before this point is
	 *            reached.<br>
	 *            If distance, the command will end after it has gone past the
	 *            positive setpoint If time, the command will end after that
	 *            much time has passed
	 */

	public GrabGear(boolean useTimeout, double stopPoint) {
		this(useTimeout, stopPoint, 0);
	}

	public GrabGear(boolean useTimeout, double stopPoint, double minStopPoint) {
		addSequential(new GearIntakeSetDown());
		if (useTimeout) {
			addSequential(new GearIntakeInCurrentTime(minStopPoint, stopPoint, true));
		} else {
			addSequential(new GearIntakeInCurrentDistance(minStopPoint, stopPoint));

		}
		addParallel(new GearIntakeSetUpTimedIn(.3));
		// addSequential(new GearIntakeSetUp());
		// addSequential(new Wait(2));
		// addSequential(new GearIntakeSetStop());
	}
}
