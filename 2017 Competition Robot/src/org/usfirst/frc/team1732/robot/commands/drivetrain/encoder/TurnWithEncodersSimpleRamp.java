package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TurnWithEncodersSimpleRamp extends CommandGroup {

    public TurnWithEncodersSimpleRamp(double angle) {
	addSequential(new DriveTime(0.001, 0));
	addSequential(new TurnWithEncodersSimpleRampBase(angle));
    }

    public TurnWithEncodersSimpleRamp(DoubleSupplier angle) {
	addSequential(new DriveTime(0.001, 0));
	addSequential(new TurnWithEncodersSimpleRampBase(angle));
    }
}
