package org.usfirst.frc.team1732.robot.commands.drivetrain;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DitherTurn extends Command {
	private double angleSetpoint;

	public DitherTurn(double angle, double ditherInterval) {
		requires(Robot.driveTrain);
		requires(Robot.pixyCamera);
		angleSetpoint = angle;
		setTimeout(5);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {

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
