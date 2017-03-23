package org.usfirst.frc.team1732.robot.commands.vision;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FlashLEDsCommand extends Command {
	private long lastTime;
	public FlashLEDsCommand() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.pixyCamera);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.pixyCamera.turnOnLights();
		lastTime = System.currentTimeMillis();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (System.currentTimeMillis() - lastTime > 100) {
			if (!Robot.pixyCamera.isLightOn())
				Robot.pixyCamera.turnOnLights();
			else
				Robot.pixyCamera.turnOffLights();
			lastTime = System.currentTimeMillis();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.gearIntake.isUp();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.pixyCamera.turnOffLights();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.pixyCamera.turnOffLights();
		
	}
}
