package org.usfirst.frc.team1732.robot.autocommands.scoregear.twogearmiddlethensideauto;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.drivetrain.SetMotorSpeed;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GrabGear;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.vision.VisionPlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TwoGearMiddleThenSideAuto extends CommandGroup {

	public TwoGearMiddleThenSideAuto() {
		boolean isRed = Robot.isRedAlliance.getValue();
		double maxSetpoint = 80;

		addSequential(new InitGearIntake());

		// wait to move
		addSequential(new Wait(Robot.autoWaitTime.getValue()));

		addSequential(new ClearTotalDistance());

		// place first gear
		double firstGearScoreDriveBackDistance = -50;
		addSequential(new VisionPlaceGear(firstGearScoreDriveBackDistance, maxSetpoint, false));
		//		addSequential(new EncoderPlaceGear(	TwoGearMiddleThenSideAutoData.DRIVE_1_SETPOINT,
		//											TwoGearMiddleThenSideAutoData.DRIVE_1_SETPOINT));

		// turn to pickup gear
		double gearPickupAngle = 0;
		if (isRed) {
			gearPickupAngle = 90;
		} else {
			gearPickupAngle = -90;
		}
		addSequential(new TurnWithEncoders(gearPickupAngle));

		// drive forward to pickup gear
		double gearPickupDriveSpeed = 0.5;
		addSequential(new SetMotorSpeed(gearPickupDriveSpeed));

		// drops gear intake
		boolean gearPickupUseTimeout = false; // will use distance to end command
		double gearPickupMaxDistance = 40;
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

		// drive upfield towards gear peg
		double driveUpFieldDistance = 75;
		addSequential(new DriveEncoders(driveUpFieldDistance));

		// face gear peg
		double faceGearPegAngle = 0;
		if (isRed) {
			faceGearPegAngle = -60;
		} else {
			faceGearPegAngle = 60;
		}
		addSequential(new TurnWithEncoders(faceGearPegAngle));

		// place second gear
		double secondGearScoreDriveBackDistance = -15;
		addSequential(new VisionPlaceGear(secondGearScoreDriveBackDistance, maxSetpoint, true));
	}
}
