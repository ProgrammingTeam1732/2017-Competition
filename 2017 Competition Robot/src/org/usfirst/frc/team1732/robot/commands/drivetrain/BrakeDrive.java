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
	System.out.println("start braking");
	Robot.driveTrain.shiftLowGear();
	Robot.driveTrain.driveRaw(0, 0);
    }

    public static final double BRAKE_P = (1 / (13.5 * 12)) * 4;

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
	// double leftV = Robot.driveTrain.getLeftVelocity();
	// double rightV = Robot.driveTrain.getRightVelocity();
	// double averageV = (leftV + rightV) / 2.0;
	// double output = averageV * BRAKE_P;
	// double left = -Math.copySign(output, leftV);
	// double right = -Math.copySign(output, rightV);
	// Robot.driveTrain.driveRaw(left, right);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	return Math.abs(Robot.driveTrain.getLeftVelocity()) < 1 && Math.abs(Robot.driveTrain.getRightVelocity()) < 1;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	System.out.println("end braking");
	Robot.driveTrain.shiftHighGear();
    }

}
