package org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor;

import static org.usfirst.frc.team1732.robot.Robot.gearIntake;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class GearIntakeInCurrentDistance extends Command {

	private final double setpoint;

	public GearIntakeInCurrentDistance(double stoppoint) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(gearIntake);
		setpoint = stoppoint;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		gearIntake.setIn();
		Robot.driveTrain.resetEncoders();
		Robot.driveTrain.setEncoderSetpoint(setpoint);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return gearIntake.gearIsIn() || Robot.driveTrain.isErrorNegative();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {}

}
