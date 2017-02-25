package org.usfirst.frc.team1732.robot.autocommands.visionplacegear;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncodersGetSetpointAtRuntime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeOutTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.GearIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.vision.DriveWithVision;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class VisionPlaceGear extends CommandGroup {

	public VisionPlaceGear(double driveBackDistance) {
		this(driveBackDistance, driveBackDistance);
	}

	public VisionPlaceGear(double leftDriveBackDistance, double rightDriveBackDistance) {
		this(() -> leftDriveBackDistance, () -> rightDriveBackDistance);
	}

	public VisionPlaceGear(DoubleSupplier driveBackDistance) {
		this(driveBackDistance, driveBackDistance);
	}

	public VisionPlaceGear(DoubleSupplier leftDriveBackDistance, DoubleSupplier rightDriveBackDistance) {
		// drive into gear peg
		addSequential(new DriveWithVision(10));
		addSequential(new Wait(0.1));

		// place gear, drive back at same time

		addSequential(new GearIntakeSetDown());
		addParallel(new CommandGroup() {
			{
				addSequential(new GearIntakeOutTime(1));
				addSequential(new GearIntakeSetUp());
			}
		});
		addSequential(new DriveEncodersGetSetpointAtRuntime(leftDriveBackDistance, rightDriveBackDistance));
	}
}
