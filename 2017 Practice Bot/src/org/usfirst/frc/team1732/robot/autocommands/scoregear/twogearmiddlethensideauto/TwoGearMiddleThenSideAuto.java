package org.usfirst.frc.team1732.robot.autocommands.scoregear.twogearmiddlethensideauto;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.drivetrain.SetMotorSpeed;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncodersGetSetpointAtRuntime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GrabGear;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.placegear.EncoderVisionPlaceGear;
import org.usfirst.frc.team1732.robot.commands.placegear.VisionPlaceGear;
import org.usfirst.frc.team1732.robot.commands.vision.movement.TurnWithVision;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TwoGearMiddleThenSideAuto extends CommandGroup {

	private double	distanceDrove;
	private double	distanceDroveMinusDistanceBetweenPegs;
	private double	distanceFromWallToGearPegMinusDistanceDrove;
	private double	distanceToPeg;

	private static final double	DISTANCE_BETWEEN_PEGS			= 28;
	private static final double	DISTANCE_FROM_WALL_TO_SIDE_PEG	= 108;

	public TwoGearMiddleThenSideAuto(boolean isLeft) {
		double maxSetpoint = 80;

		addSequential(new InitGearIntake());

		// wait to move
		addSequential(new Wait(Robot.autoWaitTime.getValue()));

		// place first gear
		double firstGearScoreDriveBackDistance = -50;
		addSequential(new VisionPlaceGear(firstGearScoreDriveBackDistance, maxSetpoint, false));
		//		addSequential(new EncoderPlaceGear(	TwoGearMiddleThenSideAutoData.DRIVE_1_SETPOINT,
		//											TwoGearMiddleThenSideAutoData.DRIVE_1_SETPOINT));

		// turn to pickup gear
		double gearPickupAngle = 0;
		if (isLeft) {
			gearPickupAngle = -105;
		} else {
			gearPickupAngle = 105;
		}
		addSequential(new TurnWithEncoders(gearPickupAngle));

		addSequential(new ClearTotalDistance());

		// drive forward to pickup gear
		double gearPickupDriveSpeed = 0.5;
		addSequential(new SetMotorSpeed(gearPickupDriveSpeed));

		// drops gear intake
		boolean gearPickupUseTimeout = false; // will use distance to end command
		double gearPickupMaxDistance = 90;
		addSequential(new GrabGear(gearPickupUseTimeout, gearPickupMaxDistance));

		// stop robot
		double stopSpeed = 0;
		addSequential(new SetMotorSpeed(stopSpeed));

		// turn to face other side of field
		double turnDownfieldAngle = -gearPickupAngle;
		//		double turnDownfieldAngle = 0;
		//		if (isRed) {
		//			turnDownfieldAngle = -90;
		//		} else {
		//			turnDownfieldAngle = 90;
		//		}
		addSequential(new TurnWithEncoders(turnDownfieldAngle));

		//		 drive upfield towards gear peg
		DoubleSupplier driveUpFieldDistance = () -> {
			distanceDrove = (Robot.driveTrain.getTotalLeftDistance() + Robot.driveTrain.getTotalRightDistance()) / 2.0;
			distanceDroveMinusDistanceBetweenPegs = distanceDrove - DISTANCE_BETWEEN_PEGS;
			distanceToPeg = (1.0 / Math.cos(Math.toRadians(30))) * distanceDroveMinusDistanceBetweenPegs;
			distanceFromWallToGearPegMinusDistanceDrove = Math.cos(Math.toRadians(60)) * distanceToPeg;
			return DISTANCE_FROM_WALL_TO_SIDE_PEG - distanceFromWallToGearPegMinusDistanceDrove;
		};
		addSequential(new DriveEncodersGetSetpointAtRuntime(driveUpFieldDistance));

		// face gear peg (should be 30, but stuff is bad)
		double faceGearPegAngle = 0;
		if (isLeft) {
			faceGearPegAngle = -60;
		} else {
			faceGearPegAngle = 60;
		}
		addSequential(new TurnWithEncoders(faceGearPegAngle));

		addSequential(new TurnWithVision(0));

		// place second gear
		DoubleSupplier driveForwardDistance = () -> distanceToPeg;
		DoubleSupplier secondGearScoreDriveBackDistance = () -> -15;
		addSequential(new EncoderVisionPlaceGear(driveForwardDistance, secondGearScoreDriveBackDistance));
	}
}