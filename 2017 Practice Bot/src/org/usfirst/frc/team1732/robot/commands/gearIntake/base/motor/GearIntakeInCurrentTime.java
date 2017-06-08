package org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor;

import static org.usfirst.frc.team1732.robot.Robot.gearIntake;

import edu.wpi.first.wpilibj.command.Command;

public class GearIntakeInCurrentTime extends Command {

	private double	minTime;
	private long	start;

	/**
	 * 
	 * @param minTime
	 *            minimum time in seconds
	 * @param timeout
	 *            max time in seconds
	 * @param useTimeOut
	 *            whether or not to even use a timeout
	 */

	public GearIntakeInCurrentTime(double minTime, double timeout, boolean useTimeOut) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(gearIntake);
		this.minTime = minTime;
		start = System.currentTimeMillis();
		if (useTimeOut)
			setTimeout(timeout);
	}

	public GearIntakeInCurrentTime(double timeout, boolean useTimeOut) {
		this(0, timeout, useTimeOut);
	}

	public GearIntakeInCurrentTime(boolean useTimeOut) {
		this(0, false);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		gearIntake.setIn();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return gearIntake.gearIsIn() || (isTimedOut() && (System.currentTimeMillis() - start) / 1000.0 > minTime);
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {}

}
