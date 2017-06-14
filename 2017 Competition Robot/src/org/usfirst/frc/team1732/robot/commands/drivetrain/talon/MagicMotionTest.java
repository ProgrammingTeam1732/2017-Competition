package org.usfirst.frc.team1732.robot.commands.drivetrain.talon;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.subsystems.drivetrain.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class MagicMotionTest extends Command {

    private static boolean forward = true;

    public MagicMotionTest(double setpoint) {
	requires(Robot.driveTrain);
	this.setpoint = forward ? setpoint : -setpoint;
    }

    private double setpoint;

    private double acceleration = DriveTrain.DEFAULT_ACCELERATION;
    private double velocity = DriveTrain.DEFAULT_VELOCITY;

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
	Robot.driveTrain.changeToMotionMagic();
	Robot.driveTrain.setMotionMagicAcceleration(acceleration);
	Robot.driveTrain.setMotionMagicCruiseVelocity(velocity);
	Robot.driveTrain.setMotionMagicSetpoint(setpoint);
	Robot.driveTrain.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
	Robot.driveTrain.graphMagicMotionData();
    }

    @Override
    protected boolean isFinished() {
	return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	forward = !forward;
	Robot.driveTrain.changeToPercentVBus();
    }

}
