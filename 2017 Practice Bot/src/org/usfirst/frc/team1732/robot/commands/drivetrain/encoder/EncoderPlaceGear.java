package org.usfirst.frc.team1732.robot.commands.drivetrain.encoder;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeOutTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class EncoderPlaceGear extends CommandGroup {

	public EncoderPlaceGear(double driveForwardDistance, double driveBackDistance) {
		this(driveForwardDistance, driveBackDistance, driveBackDistance);
	}

	public EncoderPlaceGear(double driveForwardDistance, double leftDriveBackDistance, double rightDriveBackDistance) {
		this(() -> driveForwardDistance, () -> leftDriveBackDistance, () -> rightDriveBackDistance);
	}

	public EncoderPlaceGear(DoubleSupplier driveForwardDistance, DoubleSupplier driveBackDistance) {
		this(driveForwardDistance, driveBackDistance, driveBackDistance);
	}

	public EncoderPlaceGear(DoubleSupplier driveForwardDistance, DoubleSupplier leftDriveBackDistance,
			DoubleSupplier rightDriveBackDistance) {
		// drive into gear peg
		// addSequential(new TurnWithVision(0));
		addSequential(new DriveEncodersWithBraking(driveForwardDistance));
		addSequential(new Wait(0.1));

		// place gear, drive back at same time

		addSequential(new GearIntakeSetDown());
		addParallel(new CommandGroup() {
			{
				addSequential(new GearIntakeOutTime(.5));
				addSequential(new GearIntakeSetUp());
			}
		});

		addSequential(new DriveEncodersGetSetpointAtRuntime(leftDriveBackDistance, rightDriveBackDistance));
	}
}
