package org.usfirst.frc.team1732.robot.commands.vision;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestVisionMain extends Command {

	public TestVisionMain() {
		super("Test Vision");
		requires(Robot.pixyCamera);
	}

	@Override
	protected void initialize() {
		Robot.pixyCamera.turnOnLights();
		System.out.println("Lights initializing");
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.visionMain.run();
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	@Override
	protected void end() {
		Robot.pixyCamera.turnOffLights();
		System.out.println("Lights ending off");
	}

	@Override
	protected void interrupted() {
		end();
	}

}
