package org.usfirst.frc.team1732.robot.autocommands.scoregear.sidetwogearauto;

import java.util.function.DoubleSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.drivetrain.SetMotorSpeed;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncodersGetSetpointAtRuntime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GearIntakeSetUpTimedIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GrabGear;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.placegear.VisionPlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SideTwoGearAuto extends CommandGroup {

	public SideTwoGearAuto(boolean isLeft) {
		double maxSetpoint = 80;

		addSequential(new InitGearIntake());

		// wait to move
		addSequential(new Wait(Robot.autoWaitTime.getValue()));

		// drive forward
		double driveForward1Distance = 56;
		addSequential(new DriveEncoders(driveForward1Distance));

		// face gear peg
		double faceGearPegAngle1 = 0;
		if (isLeft) {
			faceGearPegAngle1 = 60;
		} else {
			faceGearPegAngle1 = -60;
		}
		addSequential(new TurnWithEncoders(faceGearPegAngle1));

		addSequential(new ClearTotalDistance());

		// places the gear, drives back
		DoubleSupplier leftDriveBackSetpoint1 = () -> -10 - Robot.driveTrain.getTotalLeftDistance();
		DoubleSupplier rightDriveBackSetpoint1 = () -> -10 - Robot.driveTrain.getTotalRightDistance();
		addSequential(new VisionPlaceGear(leftDriveBackSetpoint1, rightDriveBackSetpoint1, maxSetpoint, true));

		// turns back to grab second gear
		double faceWallAngle = 0;
		if (isLeft) {
			faceWallAngle = 120;
		} else {
			faceWallAngle = -120;
		}
		addSequential(new TurnWithEncoders(faceWallAngle));

		addSequential(new ClearTotalDistance());

		// drive forward slowly
		double moveToGearSpeed = 0.5;
		addSequential(new SetMotorSpeed(moveToGearSpeed));

		// drops gear intake
		boolean grabGearUseTimeout = false; // will use distance to end command if not grabbed
		double grabGearMaxDistance = 40;
		addSequential(new GrabGear(grabGearUseTimeout, grabGearMaxDistance));

		// stop motors after grabbing gear
		double stopSpeed = 0;
		addSequential(new SetMotorSpeed(stopSpeed));

		// raises gear intake
		double gearUpTime = 1;
		addParallel(new GearIntakeSetUpTimedIn(gearUpTime));

		// drives back
		DoubleSupplier driveBackToGearPegLeftDistance = () -> -Robot.driveTrain.getTotalLeftDistance();
		DoubleSupplier driveBackToGearPegRightDistance = () -> -Robot.driveTrain.getTotalRightDistance();
		addSequential(new DriveEncodersGetSetpointAtRuntime(driveBackToGearPegLeftDistance,
															driveBackToGearPegRightDistance));

		// turns to face gear peg
		double faceGearPegAngle2 = 0;
		if (isLeft) {
			faceGearPegAngle2 = -120;
		} else {
			faceGearPegAngle2 = 120;
		}
		addSequential(new TurnWithEncoders(faceGearPegAngle2));

		// scores second gear!!!
		double driveBackDistance = -25;
		addSequential(new VisionPlaceGear(driveBackDistance, maxSetpoint, true));
	}
}
