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

	public VisionPlaceGear(double driveBackDistance, double maxSetpoint) {
		this(driveBackDistance, driveBackDistance, maxSetpoint);
	}

	public VisionPlaceGear(double leftDriveBackDistance, double rightDriveBackDistance, double maxSetpoint) {
		this(() -> leftDriveBackDistance, () -> rightDriveBackDistance, maxSetpoint);
	}

	public VisionPlaceGear(DoubleSupplier driveBackDistance, double maxSetpoint) {
		this(driveBackDistance, driveBackDistance, maxSetpoint);
	}

	public VisionPlaceGear(DoubleSupplier leftDriveBackDistance, DoubleSupplier rightDriveBackDistance,
			double maxSetpoint) {
		// drive into gear peg
		// addSequential(new TurnWithVision(0));
		addSequential(new DriveWithVision(17.5, maxSetpoint));
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
