package org.usfirst.frc.team1732.robot.commands.drivetrain;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BrakeDrive extends Command {

	public BrakeDrive() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.driveTrain.shiftLowGear();
	}

	public static final double BRAKE_P = (-1 / (13.5 * 12)) * 4;

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double leftV = Robot.driveTrain.getLeftVelocity();
		double rightV = Robot.driveTrain.getRightVelocity();
		Robot.driveTrain.driveRaw(leftV * BRAKE_P, rightV * BRAKE_P);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Math.abs(Robot.driveTrain.getLeftVelocity()) < 5 && Math.abs(Robot.driveTrain.getRightVelocity()) < 5;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveTrain.shiftHighGear();
	}

}
