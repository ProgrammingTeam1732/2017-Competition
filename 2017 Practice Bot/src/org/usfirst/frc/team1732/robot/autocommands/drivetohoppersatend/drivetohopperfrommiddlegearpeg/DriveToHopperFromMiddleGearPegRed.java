package org.usfirst.frc.team1732.robot.autocommands.drivetohoppersatend.drivetohopperfrommiddlegearpeg;

import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToHopperFromMiddleGearPegRed extends CommandGroup {

	public DriveToHopperFromMiddleGearPegRed() {
		addSequential(new DriveEncoders(DriveToHopperFromMiddleGearPegData.DRIVE_1_DISTANCE));
		addSequential(new TurnWithEncoders(DriveToHopperFromMiddleGearPegData.TURN_1_ANGLE_RED));
		addSequential(new DriveEncoders(DriveToHopperFromMiddleGearPegData.DRIVE_2_DISTANCE));
		addSequential(new TurnWithEncoders(DriveToHopperFromMiddleGearPegData.TURN_2_ANGLE_RED));
		addSequential(new DriveEncoders(DriveToHopperFromMiddleGearPegData.DRIVE_3_DISTANCE));
	}
}
