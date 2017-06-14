package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.commands.drivetrain.BrakeDrive;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TurnWithEncodersWithBraking extends CommandGroup {

    public TurnWithEncodersWithBraking(double setpoint) {
	this(() -> setpoint);
    }

    public TurnWithEncodersWithBraking(DoubleSupplier setpoint) {
	addSequential(new TurnWithEncoders(setpoint));
	addSequential(new BrakeDrive());
    }

}
