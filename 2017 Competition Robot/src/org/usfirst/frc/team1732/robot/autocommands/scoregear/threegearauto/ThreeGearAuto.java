package org.usfirst.frc.team1732.robot.autocommands.scoregear.threegearauto;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.drivetrain.SetMotorSpeed;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncodersGetSetpointAtRuntime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GearIntakeSetUpTimedIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GrabGear;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.placegear.VisionPlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ThreeGearAuto extends CommandGroup {

	public ThreeGearAuto() {
		boolean isRed = Robot.isRedAlliance.getValue();
		double maxSetpoint = 80;
		addSequential(new InitGearIntake());

		// wait to move
		addSequential(new Wait(Robot.autoWaitTime.getValue()));

		// SCORE FIRST GEAR, PICKUP SECOND GEAR

		addSequential(new ClearTotalDistance());

		// places the gear, drives back
		DoubleSupplier firstGearScoreDriveBackLeft = () -> 60 - Robot.driveTrain.getTotalLeftDistance();
		DoubleSupplier firstGearScoreDriveBackRight = () -> 60 - Robot.driveTrain.getTotalRightDistance();
		addSequential(new VisionPlaceGear(	firstGearScoreDriveBackLeft, firstGearScoreDriveBackRight, maxSetpoint,
											true));

		// turns to face the gear on ground
		double firstGearPickupFaceGearAngle = 0;
		if (isRed) {
			firstGearPickupFaceGearAngle = -90;
		} else {
			firstGearPickupFaceGearAngle = 90;
		}
		addSequential(new TurnWithEncoders(firstGearPickupFaceGearAngle));

		addSequential(new ClearTotalDistance());

		// drive forward to pick up gear
		double gearPickupDriveSpeed = 0.5;
		addSequential(new SetMotorSpeed(gearPickupDriveSpeed));

		// drops gear intake
		boolean gearPickupUseTimeout = true; // will use time to end command
		double gearPickupTimeout = 2.5;
		addSequential(new GrabGear(gearPickupUseTimeout, gearPickupTimeout));

		// stop robot
		double stopSpeed = 0;
		addSequential(new SetMotorSpeed(stopSpeed));

		// raises gear intake
		double raiseGearTime = 1;
		addParallel(new GearIntakeSetUpTimedIn(raiseGearTime));

		// drives back
		DoubleSupplier firstGearPickupReturnToGearPegLeft = () -> -Robot.driveTrain.getTotalLeftDistance();
		DoubleSupplier firstGearPickupReturnToGearPegRight = () -> -Robot.driveTrain.getTotalRightDistance();
		addSequential(new DriveEncodersGetSetpointAtRuntime(firstGearPickupReturnToGearPegLeft,
															firstGearPickupReturnToGearPegRight));

		// turns to face gear peg
		double firstGearPickupFaceGearPeg = 0;
		if (isRed) {
			firstGearPickupFaceGearPeg = 90;
		} else {
			firstGearPickupFaceGearPeg = -90;
		}
		addSequential(new TurnWithEncoders(firstGearPickupFaceGearPeg));

		// SCORE SECOND GEAR, PICKUP THIRD GEAR

		addSequential(new ClearTotalDistance());

		// places the gear, drives back
		DoubleSupplier secondGearScoreDriveBackLeft = () -> 60 - Robot.driveTrain.getTotalLeftDistance();
		DoubleSupplier secondGearScoreDriveBackRight = () -> 60 - Robot.driveTrain.getTotalRightDistance();
		addSequential(new VisionPlaceGear(	secondGearScoreDriveBackLeft, secondGearScoreDriveBackRight, maxSetpoint,
											true));

		// turns to face the gear on ground
		double secondGearPickupFaceGearAngle = 0;
		if (isRed) {
			secondGearPickupFaceGearAngle = 90;
		} else {
			secondGearPickupFaceGearAngle = -90;
		}
		addSequential(new TurnWithEncoders(secondGearPickupFaceGearAngle));

		addSequential(new ClearTotalDistance());

		// drive forward to pick up gear
		addSequential(new SetMotorSpeed(gearPickupDriveSpeed));

		// drops gear intake
		addSequential(new GrabGear(gearPickupUseTimeout, gearPickupTimeout));

		// stop robot
		addSequential(new SetMotorSpeed(stopSpeed));

		// raises gear intake
		addParallel(new GearIntakeSetUpTimedIn(raiseGearTime));

		// drives back
		DoubleSupplier secondGearPickupReturnToGearPegLeft = () -> -Robot.driveTrain.getTotalLeftDistance();
		DoubleSupplier secondGearPickupReturnToGearPegRight = () -> -Robot.driveTrain.getTotalRightDistance();
		addSequential(new DriveEncodersGetSetpointAtRuntime(secondGearPickupReturnToGearPegLeft,
															secondGearPickupReturnToGearPegRight));

		// turns to face gear peg
		double secondGearPickupFaceGearPeg = 0;
		if (isRed) {
			secondGearPickupFaceGearPeg = -90;
		} else {
			secondGearPickupFaceGearPeg = 90;
		}
		addSequential(new TurnWithEncoders(secondGearPickupFaceGearPeg));

		// PLACE THIRD GEAR

		double thirdGearDriveBack = -25;
		addSequential(new VisionPlaceGear(thirdGearDriveBack, maxSetpoint, true));
	}
}
