package org.usfirst.frc.team1732.robot.autocommands.scoregear.twogearauto;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.drivetrain.BrakeDrive;
import org.usfirst.frc.team1732.robot.commands.drivetrain.BrakeDriveNoShift;
import org.usfirst.frc.team1732.robot.commands.drivetrain.SetMotorSpeed;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncodersGetSetpointAtRuntime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ResetEncoderPID;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.SetEncoderPID;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncodersSimpleRamp;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GearIntakeSetUpTimedIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GrabGear;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.placegear.EncoderPlaceGear;
import org.usfirst.frc.team1732.robot.commands.vision.movement.DitherTurnWithVision;
import org.usfirst.frc.team1732.robot.commands.vision.movement.TurnWithEncodersUntilCheeseWheel;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TwoGearAuto extends CommandGroup {

    private double droveBack;
    public static final double DEFAULT_DRIVE_BACK_DISTANCE = -40;

    public TwoGearAuto(boolean isLeft) {
	addSequential(new InitGearIntake());

	addSequential(new ClearTotalDistance());

	// places the gear, drives back
	DoubleSupplier firstGearScoreDriveBack = () -> {
	    // droveBack = 10 - (Robot.driveTrain.getTotalLeftDistance() +
	    // Robot.driveTrain.getTotalRightDistance()) / 2.0;
	    // System.out.println(droveBack);
	    droveBack = Robot.twoGearDriveBack.getValue(); // -55
	    return droveBack;
	};
	DoubleSupplier firstGearDriveForwardDistance = () -> 63;
	addSequential(new EncoderPlaceGear(firstGearDriveForwardDistance, firstGearScoreDriveBack));

	addSequential(new BrakeDriveNoShift());

	// turns to face the gear on ground
	double firstGearPickUpFaceGearAngle = 0;
	if (isLeft) {
	    firstGearPickUpFaceGearAngle = -98;
	} else {
	    firstGearPickUpFaceGearAngle = 95;
	}

	// addSequential(new
	// TurnWithEncodersSimpleRamp(firstGearPickUpFaceGearAngle));
	//
	// addSequential(new BrakeDriveNoShift());

	addSequential(new TurnWithEncodersUntilCheeseWheel(firstGearPickUpFaceGearAngle));

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
	double gearPickupMaxDistance = 75;
	// addSequential(new GrabGear(gearPickupUseTimeout, gearPickupTimeout));
	addSequential(new GrabGear(gearPickupUseTimeout, gearPickupMaxDistance));

	// stop robot
	// double stopSpeed = 0;
	// addSequential(new SetMotorSpeed(stopSpeed));
	addSequential(new BrakeDrive());

	// raises gear intake
	double raiseGearTime = 1;
	addParallel(new GearIntakeSetUpTimedIn(raiseGearTime));

	addSequential(new SetEncoderPID(0.02, 0, 0));
	// drives back
	DoubleSupplier firstGearPickupReturnToGearPeg = () -> (Robot.driveTrain.getTotalLeftDistance()
		+ Robot.driveTrain.getTotalRightDistance()) / -2.0;
	addSequential(new DriveEncodersGetSetpointAtRuntime(firstGearPickupReturnToGearPeg));
	addSequential(new ResetEncoderPID());

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
	DoubleSupplier driveForwardSecondHalfDistance = () -> driveForwardDistance.getAsDouble() * (1.0 - proportion)
		- 6;

	DoubleSupplier secondGearScoreDriveBackDistance = () -> -15;
	addSequential(new SetEncoderPID(0.2, 0, 0));
	addSequential(new EncoderPlaceGear(driveForwardSecondHalfDistance, secondGearScoreDriveBackDistance));
    }
}
