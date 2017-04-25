package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveEncodersSimpleRamp extends CommandGroup {

    public DriveEncodersSimpleRamp(DoubleSupplier left, DoubleSupplier right) {
	// addSequential(new DriveTime(0.01, 0));
	addSequential(new DriveEncodersSimpleRampBase(left, right));
    }
}