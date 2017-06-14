package org.usfirst.frc.team1732.robot.commands.drivetrain.brake;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class SetDriveTrainBrakeMode extends InstantCommand {

    private final boolean brake;

    public SetDriveTrainBrakeMode(boolean brake) {
	super();
	// Use requires() here to declare subsystem dependencies
	// eg. requires(chassis);
	this.brake = brake;
    }

    // Called once when the command executes
    @Override
    protected void initialize() {
	System.out.println("Set Drivetrain Brake Mode " + brake);
	Robot.driveTrain.setBrakeMode(brake);
    }

}
