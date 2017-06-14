package org.usfirst.frc.team1732.robot.commands.vision.movement;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TurnWithEncodersUntilGearPeg extends CommandGroup {

    public TurnWithEncodersUntilGearPeg(double angle) {
	this(() -> angle);
    }

    public TurnWithEncodersUntilGearPeg(DoubleSupplier angle) {
	addSequential(new DriveTime(0.001, 0));
	addSequential(new TurnWithEncodersUntilGearPegBase(angle));
    }
}
