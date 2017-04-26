package org.usfirst.frc.team1732.robot.commands.vision.movement;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;
import static org.usfirst.frc.team1732.robot.Robot.visionMain;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Use this command to control turning the robot with the camera.
 */
public class TurnWithVision extends Command {

	private final double angleSetpoint;

	public TurnWithVision(double angle) {
		requires(driveTrain);
		requires(Robot.pixyCamera);
		angleSetpoint = angle;
		setTimeout(5);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.pixyCamera.turnOnLights();
		visionMain.setGearSetpoint(angleSetpoint);
	}

	private boolean foundOnce = false;

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		visionMain.run();
		// double angle = visionMain.getAngleToGearPeg();
		if (visionMain.canSeeGearPeg()) {
			foundOnce = true;
			double output = visionMain.getGearPIDOutput();
			// 1 - // Math.abs(angle/angleSetpoint);
			driveTrain.driveRaw(output, -output);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return (foundOnce && visionMain.isGearPIDOnTarget()) || isTimedOut();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		driveTrain.driveRaw(0, 0);
		Robot.pixyCamera.turnOffLights();
	}
}