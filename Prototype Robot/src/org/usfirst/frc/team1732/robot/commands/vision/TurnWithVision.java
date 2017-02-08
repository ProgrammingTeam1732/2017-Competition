package org.usfirst.frc.team1732.robot.commands.vision;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;
import static org.usfirst.frc.team1732.robot.Robot.visionMain;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Use this command to control turning the robot with the camera.
 */
public class TurnWithVision extends Command {

	private final double angleSetpoint;

	public TurnWithVision(double angle) {
		requires(driveTrain);
		angleSetpoint = angle;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		visionMain.visionPID.setSetpoint(angleSetpoint);
	}

	private boolean foundOnce = false;

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double angle = visionMain.getAngleToGearPeg();
		if (visionMain.canSeeGearPeg()) {
			foundOnce = true;
			double output = visionMain.visionPID.get();
			driveTrain.driveRaw(-output, output);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return visionMain.visionPID.onTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		driveTrain.driveRaw(0, 0);
	}
}
