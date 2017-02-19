package org.usfirst.frc.team1732.robot.commands.drivetrain;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnWithGyro extends Command {

	private final double setpoint;

	public TurnWithGyro(double degreesSetpoint) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
		setpoint = degreesSetpoint;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		System.out.println("Running turn : " + Robot.isRedAlliance());
		Robot.driveTrain.resetGyro();
		// Robot.driveTrain.resetGyroPID();
		Robot.driveTrain.clearGyroIntgral();
		Robot.driveTrain.setGyroSetpoint(setpoint);
		System.out.println(setpoint);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double output = Robot.driveTrain.getGyroPIDOutput();
		Robot.driveTrain.driveRaw(output, -output);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.driveTrain.gyroOnTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveTrain.driveRaw(0, 0);
	}
}
