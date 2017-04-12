package org.usfirst.frc.team1732.robot.commands.vision.movement;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;
import static org.usfirst.frc.team1732.robot.Robot.visionMain;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Use this command to control turning the robot with the camera.
 */
public class TurnWithVision extends Command {

	private final double angleSetpoint;
	private long startTime;
	private double ditherInterval;
	private double ditherLength;
	private long absStartTime;

	public TurnWithVision(double angle, double ditherInterval, double ditherLength) {
		requires(driveTrain);
		requires(Robot.pixyCamera);
		angleSetpoint = angle;
		setTimeout(10);
		this.ditherInterval = ditherInterval;
		this.ditherLength = ditherLength;
		absStartTime = System.currentTimeMillis();
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.pixyCamera.turnOnLights();
		visionMain.setGearSetpoint(angleSetpoint);
		startTime = System.currentTimeMillis();
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
			// double output = 0;
			if (System.currentTimeMillis() - startTime > ditherInterval) {
				if (System.currentTimeMillis() - startTime
						- ditherInterval < ditherLength/*
														 * && Math.abs(output) <
														 * .35
														 */)
					output = Math.copySign(.35, output);
				else
					startTime = System.currentTimeMillis();

			} else
				output = 0;
			// 1 - // Math.abs(angle/angleSetpoint);
			SmartDashboard.putString("Gear Drive", driveTrain.driveRawAbsLimit(output, -output, .178, 1));
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return ((foundOnce && visionMain.isGearPIDOnTarget()) && System.currentTimeMillis() - absStartTime > 100)
				|| isTimedOut();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		driveTrain.driveRaw(0, 0);
		Robot.pixyCamera.turnOffLights();
	}
}
