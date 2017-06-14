package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;

import org.usfirst.frc.team1732.robot.subsystems.drivetrain.PIDData;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class SetEncoderPID extends InstantCommand {

    private PIDData values;

    public SetEncoderPID(PIDData values) {
	super();
	this.values = values;
    }

    // Called once when the command executes
    @Override
    protected void initialize() {
	System.out.println("Set PID Values- " + values);
	driveTrain.mainController.setPIDValues(values);
    }

}
