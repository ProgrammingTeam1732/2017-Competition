package org.usfirst.frc.team1732.robot.autocommands.twogearauto;

import org.usfirst.frc.team1732.robot.autocommands.visionplacegear.VisionPlaceGear;
import org.usfirst.frc.team1732.robot.commands.drivetrain.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TwoGearAutoRed extends CommandGroup {

	public TwoGearAutoRed() {
		addSequential(new ClearTotalDistance());

		// places the gear, drives back
		addSequential(new VisionPlaceGear(	TwoGearAutoData.DRIVE_1_LEFT_SETPOINT,
											TwoGearAutoData.DRIVE_1_RIGHT_SETPOINT));

		// turns to face the gear on ground
		addSequential(new TurnWithGyro(TwoGearAutoData.TURN_1_ANGLE_RED));

		// drops gear intake
		// addSequential(new GearIntakeSetDown());
		// addSequenital(new GearIntakeSetIn());

		// drives forward to pickup gear
		addSequential(new DriveEncoders(TwoGearAutoData.DRIVE_2_SETPOINT));

		addSequential(new Wait(TwoGearAutoData.WAIT_1_TIME));

		// raises gear intake
		// addSequential(new GearIntakeSetUp());
		// addSequenital(new GearIntakeSetStop());

		// drives back
		addSequential(new DriveEncoders(TwoGearAutoData.DRIVE_3_SETPOINT));

		// turns to face gear peg
		addSequential(new TurnWithGyro(TwoGearAutoData.TURN_2_ANGLE_RED));

		// scores second gear!!!
		addSequential(new VisionPlaceGear(TwoGearAutoData.DRIVE_4_DRIVE_BACK_SETPOINT));
	}
}
