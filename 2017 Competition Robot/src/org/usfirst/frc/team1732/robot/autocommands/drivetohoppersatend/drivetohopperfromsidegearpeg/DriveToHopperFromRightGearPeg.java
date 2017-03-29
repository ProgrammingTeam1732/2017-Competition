package org.usfirst.frc.team1732.robot.autocommands.drivetohoppersatend.drivetohopperfromsidegearpeg;

import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToHopperFromRightGearPeg extends CommandGroup {

	public DriveToHopperFromRightGearPeg() {
		addSequential(new DriveEncoders(DriveToHopperFromSideGearPegData.DRIVE_1_DISTANCE));
		addSequential(new TurnWithEncoders(DriveToHopperFromSideGearPegData.TURN_1_ANGLE_RIGHT));
		addSequential(new DriveEncoders(DriveToHopperFromSideGearPegData.DRIVE_2_DISTANCE));
	}
}
