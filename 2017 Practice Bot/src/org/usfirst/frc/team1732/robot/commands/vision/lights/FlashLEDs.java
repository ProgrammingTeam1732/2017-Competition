package org.usfirst.frc.team1732.robot.commands.vision.lights;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FlashLEDs extends Command {
	private long lastTime;

	public FlashLEDs() {
		requires(Robot.pixyCamera);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.pixyCamera.turnOnLights();
		lastTime = System.currentTimeMillis();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (System.currentTimeMillis() - lastTime > 100) {
			if (Robot.pixyCamera.isLightOff())
				Robot.pixyCamera.turnOnLights();
			else
				Robot.pixyCamera.turnOffLights();
			lastTime = System.currentTimeMillis();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.gearIntake.isUp();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.pixyCamera.turnOffLights();
	}

}
