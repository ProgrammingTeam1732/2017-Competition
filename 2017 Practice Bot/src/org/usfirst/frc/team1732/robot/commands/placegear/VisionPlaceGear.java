package org.usfirst.frc.team1732.robot.commands.placegear;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.commands.drivetrain.BrakeDrive;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncodersGetSetpointAtRuntime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeOutTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.vision.lights.TurnLightsOff;
import org.usfirst.frc.team1732.robot.commands.vision.lights.TurnLightsOn;
import org.usfirst.frc.team1732.robot.commands.vision.movement.DriveWithVision;
import org.usfirst.frc.team1732.robot.commands.vision.movement.DriveWithVisionStraight;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class VisionPlaceGear extends CommandGroup {

	public VisionPlaceGear(double driveBackDistance, double maxSetpoint, boolean turn) {
		this(driveBackDistance, driveBackDistance, maxSetpoint, turn);
	}

	public VisionPlaceGear(double leftDriveBackDistance, double rightDriveBackDistance, double maxSetpoint,
			boolean turn) {
		this(() -> leftDriveBackDistance, () -> rightDriveBackDistance, maxSetpoint, turn);
	}

	public VisionPlaceGear(DoubleSupplier driveBackDistance, double maxSetpoint, boolean turn) {
		this(driveBackDistance, driveBackDistance, maxSetpoint, turn);
	}

	public VisionPlaceGear(DoubleSupplier leftDriveBackDistance, DoubleSupplier rightDriveBackDistance,
			double maxSetpoint, boolean turn) {
		// drive into gear peg
		// addSequential(new TurnWithVision(0));
		addSequential(new TurnLightsOn());
		if (turn)
			addSequential(new DriveWithVision(15, maxSetpoint));
		else
			addSequential(new DriveWithVisionStraight(15, maxSetpoint));
		addSequential(new BrakeDrive());
		addSequential(new Wait(0.1));
		addSequential(new TurnLightsOff());

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
