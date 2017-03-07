package org.usfirst.frc.team1732.robot.autocommands.visionplacegear;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncodersGetSetpointAtRuntime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeOutTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.vision.DriveWithVision;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class VisionPlaceGear extends CommandGroup {

	public VisionPlaceGear(double driveBackDistance, double fallbackDistance, boolean useFallbackDistance) {
		this(driveBackDistance, driveBackDistance, fallbackDistance, useFallbackDistance);
	}

	public VisionPlaceGear(double leftDriveBackDistance, double rightDriveBackDistance, double fallbackDistance,
			boolean useFallbackDistance) {
		this(() -> leftDriveBackDistance, () -> rightDriveBackDistance, fallbackDistance, useFallbackDistance);
	}

	public VisionPlaceGear(DoubleSupplier driveBackDistance, double fallbackDistance, boolean useFallbackDistance) {
		this(driveBackDistance, driveBackDistance, fallbackDistance, useFallbackDistance);
	}

	public VisionPlaceGear(DoubleSupplier leftDriveBackDistance, DoubleSupplier rightDriveBackDistance,
			double fallbackDistance, boolean useFallbackDistance) {
		// drive into gear peg
		addSequential(new DriveWithVision(17.5, fallbackDistance, useFallbackDistance));
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
