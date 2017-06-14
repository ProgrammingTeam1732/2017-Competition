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
	Robot.driveTrain.setLeftPosPIDSetpoint(setpoint);
	Robot.driveTrain.setRightPosPIDSetpoint(setpoint);
	double left = Robot.driveTrain.getLeftVelPIDOutput();
	double right = Robot.driveTrain.getRightVelPIDOutput();
	Robot.driveTrain.driveRaw(left, right);
	SmartDashboard.putBoolean("Left Pos On Target", Robot.driveTrain.leftPosOnTarget());
	SmartDashboard.putBoolean("Right Pos On Target", Robot.driveTrain.rightPosOnTarget());
	SmartDashboard.putNumber("Left Pos Error", Robot.driveTrain.getLeftPosPIDError());
	SmartDashboard.putNumber("Right Pos Error", Robot.driveTrain.getRightPosPIDError());
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
