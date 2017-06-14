package org.usfirst.frc.team1732.robot.commands.drivetrain.dualpid;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DualPIDPosTest extends Command {

    private double setpoint;

    public DualPIDPosTest(double setpoint) {
	requires(Robot.driveTrain);
	this.setpoint = setpoint;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
	Robot.driveTrain.positionController.setSetpoint(setpoint);
	double left = Robot.driveTrain.velocityController.left.getOutput();
	double right = Robot.driveTrain.velocityController.right.getOutput();
	Robot.driveTrain.driveRaw(left, right);
	SmartDashboard.putBoolean("Left Pos On Target", Robot.driveTrain.positionController.left.onTarget());
	SmartDashboard.putBoolean("Right Pos On Target", Robot.driveTrain.positionController.right.onTarget());
	SmartDashboard.putNumber("Left Pos Error", Robot.driveTrain.positionController.left.getError());
	SmartDashboard.putNumber("Right Pos Error", Robot.driveTrain.positionController.right.getError());
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
