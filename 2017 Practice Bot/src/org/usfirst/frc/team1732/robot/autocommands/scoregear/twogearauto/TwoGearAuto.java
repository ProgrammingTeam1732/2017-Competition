package org.usfirst.frc.team1732.robot.autocommands.scoregear.twogearauto;

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
import org.usfirst.frc.team1732.robot.commands.vision.VisionPlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TwoGearAuto extends CommandGroup {

	public TwoGearAuto(boolean isLeft) {
		double maxSetpoint = 80;

		addSequential(new InitGearIntake());

		// wait to move
		addSequential(new Wait(Robot.autoWaitTime.getValue()));

		addSequential(new ClearTotalDistance());

		// places the gear, drives back
		DoubleSupplier firstGearScoreDriveBackLeft = () -> 60 - Robot.driveTrain.getTotalLeftDistance();
		DoubleSupplier firstGearScoreDriveBackRight = () -> 60 - Robot.driveTrain.getTotalRightDistance();
		addSequential(new VisionPlaceGear(	firstGearScoreDriveBackLeft, firstGearScoreDriveBackRight, maxSetpoint,
											true));

		// turns to face the gear on ground
		double firstGearPickUpFaceGearAngle = 0;
		if (isLeft) {
			firstGearPickUpFaceGearAngle = -162;
		} else {
			firstGearPickUpFaceGearAngle = 162;
		}
		addSequential(new TurnWithEncoders(firstGearPickUpFaceGearAngle));

		addSequential(new ClearTotalDistance());

		// drive forward to pick up gear
		double gearPickupDriveSpeed = 0.5;
		addSequential(new SetMotorSpeed(gearPickupDriveSpeed));

		// drops gear intake
		boolean gearPickupUseTimeout = false; // will use distance to end command
		//		double gearPickupTimeout = 2.5;
		double gearPickupMaxDistance = 30;
		//		addSequential(new GrabGear(gearPickupUseTimeout, gearPickupTimeout));
		addSequential(new GrabGear(gearPickupUseTimeout, gearPickupMaxDistance));

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
		// turns to face gear peg
		double firstGearPickupFaceGearPeg = -firstGearPickUpFaceGearAngle;
		//		double firstGearPickupFaceGearPeg = 0;
		//		if (isLeft) {
		//			firstGearPickupFaceGearPeg = 90;
		//		} else {
		//			firstGearPickupFaceGearPeg = -90;
		//		}
		addSequential(new TurnWithEncoders(firstGearPickupFaceGearPeg));

		// scores second gear!!!
		double thirdGearDriveBack = -25;
		addSequential(new VisionPlaceGear(thirdGearDriveBack, maxSetpoint, true));
	}
}
