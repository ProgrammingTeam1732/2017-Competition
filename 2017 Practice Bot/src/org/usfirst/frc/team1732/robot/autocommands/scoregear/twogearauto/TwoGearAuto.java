package org.usfirst.frc.team1732.robot.autocommands.scoregear.twogearauto;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.drivetrain.BrakeDriveNoShift;
import org.usfirst.frc.team1732.robot.commands.drivetrain.SetMotorSpeed;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncodersGetSetpointAtRuntime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.SetEncoderPID;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncodersSimpleRamp;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GearIntakeSetUpTimedIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GrabGear;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.placegear.EncoderPlaceGear;
import org.usfirst.frc.team1732.robot.commands.vision.movement.DitherTurnWithVision;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TwoGearAuto extends CommandGroup {

    private double droveBack;

    public TwoGearAuto(boolean isLeft) {
	addSequential(new InitGearIntake());

	addSequential(new ClearTotalDistance());

	// places the gear, drives back
	DoubleSupplier firstGearScoreDriveBack = () -> {
	    // droveBack = 10 - (Robot.driveTrain.getTotalLeftDistance() +
	    // Robot.driveTrain.getTotalRightDistance()) / 2.0;
	    // System.out.println(droveBack);
	    droveBack = -55;
	    return droveBack;
	};
	DoubleSupplier firstGearDriveForwardDistance = () -> 59;
	addSequential(new EncoderPlaceGear(firstGearDriveForwardDistance, firstGearScoreDriveBack));

	addSequential(new BrakeDriveNoShift());

	// turns to face the gear on ground
	double firstGearPickUpFaceGearAngle = 0;
	if (isLeft) {
	    firstGearPickUpFaceGearAngle = -90;
	} else {
	    firstGearPickUpFaceGearAngle = 90;
	}
	addSequential(new TurnWithEncodersSimpleRamp(firstGearPickUpFaceGearAngle));

	addSequential(new BrakeDriveNoShift());

	// wait to move
	addSequential(new Wait(Robot.autoWaitTime.getValue()));

	addSequential(new ClearTotalDistance());

	// drive forward to pick up gear
	double gearPickupDriveSpeed = 0.5;
	addSequential(new SetMotorSpeed(gearPickupDriveSpeed));

	// drops gear intake
	boolean gearPickupUseTimeout = false; // will use distance to end
					      // command
	// double gearPickupTimeout = 2.5;
	double gearPickupMaxDistance = 70;
	// addSequential(new GrabGear(gearPickupUseTimeout, gearPickupTimeout));
	addSequential(new GrabGear(gearPickupUseTimeout, gearPickupMaxDistance));

	// stop robot
	// double stopSpeed = 0;
	// addSequential(new SetMotorSpeed(stopSpeed));
	addSequential(new BrakeDriveNoShift());

	// raises gear intake
	double raiseGearTime = 1;
	addParallel(new GearIntakeSetUpTimedIn(raiseGearTime));

	// drives back
	DoubleSupplier firstGearPickupReturnToGearPeg = () -> (-Robot.driveTrain.getTotalLeftDistance()
		+ -Robot.driveTrain.getTotalRightDistance()) / 2.0 + 7;
	addSequential(new DriveEncodersGetSetpointAtRuntime(firstGearPickupReturnToGearPeg));

	addSequential(new BrakeDriveNoShift());

	// turns to face gear peg
	double firstGearPickupFaceGearPeg = -firstGearPickUpFaceGearAngle;
	// double firstGearPickupFaceGearPeg = 0;
	// if (isLeft) {
	// firstGearPickupFaceGearPeg = 90;
	// } else {
	// firstGearPickupFaceGearPeg = -90;
	// }
	addSequential(new TurnWithEncodersSimpleRamp(firstGearPickupFaceGearPeg));

	double proportion = 0.5;
	DoubleSupplier driveForwardDistance = () -> (-1.0 * droveBack);

	DoubleSupplier driveForwardFirstHalfDistance = () -> driveForwardDistance.getAsDouble() * proportion;

	addSequential(new DriveEncodersGetSetpointAtRuntime(driveForwardFirstHalfDistance));
	addSequential(new DitherTurnWithVision(0));

	// scores second gear!!!
	DoubleSupplier driveForwardSecondHalfDistance = () -> driveForwardDistance.getAsDouble() * (1.0 - proportion);

	DoubleSupplier secondGearScoreDriveBackDistance = () -> -15;
	addSequential(new SetEncoderPID(0.2, 0, 0));
	addSequential(new EncoderPlaceGear(driveForwardSecondHalfDistance, secondGearScoreDriveBackDistance));
    }
}
