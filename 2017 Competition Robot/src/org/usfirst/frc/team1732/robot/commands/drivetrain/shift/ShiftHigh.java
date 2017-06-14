package org.usfirst.frc.team1732.robot.commands.drivetrain.shift;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ShiftHigh extends InstantCommand {

    public ShiftHigh() {
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
	System.out.println("Drivetrain Shifted High");
	Robot.driveTrain.shiftHighGear();
    }
}
