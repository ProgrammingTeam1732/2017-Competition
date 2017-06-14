package org.usfirst.frc.team1732.robot.commands.drivetrain.drive;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class DriveGyro extends Command {

    private static int id = 0;

    private final double left;
    private final double right;
    private final double setpoint;

    public DriveGyro(double angleSetpoint, double leftSpeed, double rightSpeed) {
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	requires(driveTrain);
	setpoint = angleSetpoint;
	left = leftSpeed;
	right = rightSpeed;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
	System.out.println("Start DriveGyro " + id);
	driveTrain.resetGyro();
	driveTrain.gyroController.setSetpoint(setpoint);
	driveTrain.driveRaw(left, right);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	return driveTrain.gyroController.onTarget() || Math.abs(driveTrain.getAngle()) > Math.abs(setpoint);
	// the second condition is so that if this overshoots the setpoint it
	// will stop
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	System.out.println("End DriveGyro " + id);
	id++;
	driveTrain.driveRaw(0, 0);
    }
}
