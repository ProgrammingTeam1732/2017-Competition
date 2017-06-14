package org.usfirst.frc.team1732.robot.commands.drivetrain.drive;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;
import static org.usfirst.frc.team1732.robot.Robot.oi;

import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoysticks extends Command {

    private static int id = 0;

    public DriveWithJoysticks() {
	super("Drive With Joysticks");
	requires(driveTrain);
    }

    @Override
    protected void initialize() {
	System.out.println("Start DriveWithJoysticks" + id);
	driveTrain.changeToPercentVBus();
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
	driveTrain.driveWithJoysticks(oi.getLeftSpeed(), oi.getRightSpeed());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	return false;
    }

    @Override
    protected void end() {
	System.out.println("End DriveWithJoysticks" + id);
	id++;
    }
}
