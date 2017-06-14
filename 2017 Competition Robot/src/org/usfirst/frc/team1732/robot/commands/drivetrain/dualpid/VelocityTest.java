package org.usfirst.frc.team1732.robot.commands.drivetrain.dualpid;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VelocityTest extends Command {

    private double setpoint;

    public VelocityTest(double velocity) {
	setpoint = velocity;
	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
	Robot.driveTrain.setLeftVelPIDSetpoint(setpoint);
	Robot.driveTrain.setRightVelPIDSetpoint(setpoint);
	setTimeout(5);
	System.out.println("Started test");
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
	double left = Robot.driveTrain.getLeftVelPIDOutput();
	double right = Robot.driveTrain.getRightVelPIDOutput();
	Robot.driveTrain.driveRaw(left, right);
	SmartDashboard.putBoolean("Left Vel On Target", Robot.driveTrain.leftVelOnTarget());
	SmartDashboard.putBoolean("Right Vel On Target", Robot.driveTrain.rightVelOnTarget());
	SmartDashboard.putNumber("Left Vel Error", Robot.driveTrain.getLeftVelPIDError());
	SmartDashboard.putNumber("Right Vel Error", Robot.driveTrain.getRightVelPIDError());
	SmartDashboard.putNumber("Left Vel Output", left);
	SmartDashboard.putNumber("Right Vel Output", right);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	return isTimedOut();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	System.out.println("Ended test");
    }

}
