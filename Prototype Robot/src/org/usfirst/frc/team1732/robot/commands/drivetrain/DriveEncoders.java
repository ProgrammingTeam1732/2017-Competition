package org.usfirst.frc.team1732.robot.commands.drivetrain;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveEncoders extends Command {

	private final double	leftDistance;
	private final double	rightDistance;

	public DriveEncoders(double distanceInches) {
		this(distanceInches, distanceInches);
	}

	public DriveEncoders(double leftInches, double rightInches) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(driveTrain);
		leftDistance = leftInches;
		rightDistance = rightInches;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("Driving encoders");
		driveTrain.resetEncoders();
		driveTrain.setLeftEncoderSetpoint(leftDistance);
		driveTrain.setRightEncoderSetpoint(rightDistance);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double leftOutput = driveTrain.getLeftPIDOutput();
		double rightOutput = driveTrain.getRightPIDOutput();
		driveTrain.driveRaw(leftOutput, rightOutput);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return driveTrain.encodersOnTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		driveTrain.driveRaw(0, 0);
	}

}
