package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TurnWithEncoders extends CommandGroup {

    public TurnWithEncoders(double angle) {
	addSequential(new DriveTime(0.001, 0));
	addSequential(new TurnWithEncodersBase(angle));
    }

    public TurnWithEncoders(DoubleSupplier angle) {
	addSequential(new DriveTime(0.001, 0));
	addSequential(new TurnWithEncodersBase(angle));
    }
}
