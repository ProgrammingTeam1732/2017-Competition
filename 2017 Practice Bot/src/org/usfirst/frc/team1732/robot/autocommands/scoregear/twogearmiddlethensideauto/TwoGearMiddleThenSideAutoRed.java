package org.usfirst.frc.team1732.robot.autocommands.scoregear.twogearmiddlethensideauto;

import org.usfirst.frc.team1732.robot.commands.drivetrain.SetMotorSpeed;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.EncoderPlaceGear;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.GrabGear;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.vision.VisionPlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TwoGearMiddleThenSideAutoRed extends CommandGroup {

	public TwoGearMiddleThenSideAutoRed() {
		addSequential(new InitGearIntake());

		addSequential(new ClearTotalDistance());

		addSequential(new EncoderPlaceGear(TwoGearMiddleThenSideAutoData.DRIVE_1_SETPOINT,
				TwoGearMiddleThenSideAutoData.DRIVE_1_SETPOINT));

		addSequential(new TurnWithEncoders(TwoGearMiddleThenSideAutoData.TURN_1_ANGLE_RED));

		addParallel(new SetMotorSpeed(TwoGearMiddleThenSideAutoData.DRIVE_2_SPEED,
				TwoGearMiddleThenSideAutoData.DRIVE_2_SPEED));
		addSequential(new GrabGear(true, TwoGearMiddleThenSideAutoData.GRAB_GEAR_TIMEOUT));

		addSequential(new SetMotorSpeed(TwoGearMiddleThenSideAutoData.DRIVE_2_STOP,
				TwoGearMiddleThenSideAutoData.DRIVE_2_STOP));

		addSequential(new TurnWithEncoders(TwoGearMiddleThenSideAutoData.TURN_2_ANGLE_RED));

		addSequential(new DriveEncoders(TwoGearMiddleThenSideAutoData.DRIVE_3_SETPOINT));

		addSequential(new TurnWithEncoders(TwoGearMiddleThenSideAutoData.TURN_3_ANGLE_RED));

		addSequential(new VisionPlaceGear(TwoGearMiddleThenSideAutoData.VISION_DRIVEBACK,
				TwoGearMiddleThenSideAutoData.VISION_MAX, true));
	}
}
