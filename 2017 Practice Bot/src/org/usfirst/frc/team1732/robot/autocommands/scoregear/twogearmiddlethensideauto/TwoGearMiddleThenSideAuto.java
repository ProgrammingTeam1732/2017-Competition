package org.usfirst.frc.team1732.robot.autocommands.scoregear.twogearmiddlethensideauto;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.drivetrain.SetMotorSpeed;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncodersGetSetpointAtRuntime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.SetEncoderPID;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GrabGear;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.placegear.EncoderPlaceGear;
import org.usfirst.frc.team1732.robot.commands.placegear.EncoderVisionPlaceGear;
import org.usfirst.frc.team1732.robot.commands.vision.movement.DitherTurnWithVision;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class TwoGearMiddleThenSideAuto extends CommandGroup {

	private double	firstLeftDistance;
	private double	firstRightDistance;
	private double	secondLeftDistance;
	private double	secondRightDistance;
	private double	distanceToPeg;

	// robot is 15 to middle
	private static final double	ROBOT_WIDTH						= 34.5;
	private static final double	HALF_ROBOT_WIDTH				= ROBOT_WIDTH / 2.0;
	private static final double	DISTANCE_BETWEEN_PEGS			= 28;
	private static final double	DISTANCE_FROM_WALL_TO_SIDE_PEG	= 127;

	public TwoGearMiddleThenSideAuto(boolean isLeft) {
		addSequential(new InitGearIntake());

		// wait to move
		addSequential(new Wait(Robot.autoWaitTime.getValue()));
		addSequential(new ClearTotalDistance());

		// place first gear
		double firstGearDriveForwardDistance = 59;
		double firstGearDriveBackDistance = -50;
		addSequential(new EncoderPlaceGear(firstGearDriveForwardDistance, firstGearDriveBackDistance));

		addSequential(new InstantCommand() {
			@Override
			protected void initialize() {
				firstLeftDistance = Robot.driveTrain.getTotalLeftDistance();
				firstRightDistance = Robot.driveTrain.getTotalRightDistance();
			}
		});

		// turn to pickup gear
		double gearPickupAngle = 0;
		if (isLeft) {
			gearPickupAngle = -125;
		} else {
			gearPickupAngle = 125;
		}
		addSequential(new TurnWithEncoders(gearPickupAngle));

		addSequential(new ClearTotalDistance());

		// drive forward to pickup gear
		double gearPickupDriveSpeed = 0.5;
		addSequential(new SetMotorSpeed(gearPickupDriveSpeed));

		// drops gear intake
		boolean gearPickupUseTimeout = false; // will use distance to end command
		double gearPickupMaxDistance = 70;
		double gearPickupMinDistance = 30;
		addSequential(new GrabGear(gearPickupUseTimeout, gearPickupMaxDistance, gearPickupMinDistance));

		// stop robot
		//		addSequential(new BrakeDrive());
		double stopSpeed = 0;
		addSequential(new SetMotorSpeed(stopSpeed));

		addSequential(new InstantCommand() {
			@Override
			protected void initialize() {
				secondLeftDistance = Robot.driveTrain.getTotalLeftDistance();
				secondRightDistance = Robot.driveTrain.getTotalRightDistance();
			}
		});

		// turn to face other side of field
		double turnDownfieldAngle = 0;
		if (isLeft) {
			turnDownfieldAngle = 120;
		} else {
			turnDownfieldAngle = -120;
		}
		addSequential(new TurnWithEncoders(turnDownfieldAngle));

		//		 drive upfield towards gear peg

		DoubleSupplier driveUpFieldDistance = () -> {
			double distanceFromSidePegToRobot = DISTANCE_FROM_WALL_TO_SIDE_PEG
					- ((firstLeftDistance + firstRightDistance) / 2.0) - HALF_ROBOT_WIDTH - 4;

			double distanceDrove = (secondLeftDistance + secondRightDistance) / 2.0;
			double distanceDroveMinusDistanceBetweenPegs = distanceDrove - DISTANCE_BETWEEN_PEGS;

			distanceToPeg = (1.0 / Math.cos(Math.toRadians(30))) * distanceDroveMinusDistanceBetweenPegs;

			double triangleLeg = Math.cos(Math.toRadians(60)) * distanceToPeg;

			return distanceFromSidePegToRobot - triangleLeg;
		};
		addSequential(new DriveEncodersGetSetpointAtRuntime(driveUpFieldDistance));

		double faceGearPegAngle = 0;
		if (isLeft) {
			faceGearPegAngle = 63;
		} else {
			faceGearPegAngle = -63;
		}
		addSequential(new TurnWithEncoders(faceGearPegAngle));

		addSequential(new DitherTurnWithVision(0));

		// place second gear
		DoubleSupplier driveForwardDistance = () -> distanceToPeg - HALF_ROBOT_WIDTH - 10;
		DoubleSupplier secondGearScoreDriveBackDistance = () -> -15;
		addSequential(new SetEncoderPID(0.1, 0, 0));
		addSequential(new EncoderVisionPlaceGear(driveForwardDistance, secondGearScoreDriveBackDistance));
	}
}
