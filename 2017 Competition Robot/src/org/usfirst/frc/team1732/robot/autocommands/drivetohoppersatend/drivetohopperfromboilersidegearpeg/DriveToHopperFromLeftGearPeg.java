package org.usfirst.frc.team1732.robot.autocommands.drivetohoppersatend.drivetohopperfromboilersidegearpeg;

import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToHopperFromLeftGearPeg extends CommandGroup {

	public DriveToHopperFromLeftGearPeg() {
		addSequential(new DriveEncoders(DriveToHopperFromGearPegData.DRIVE_1_DISTANCE));
		addSequential(new TurnWithGyro(DriveToHopperFromGearPegData.TURN_1_ANGLE_LEFT));
		addSequential(new DriveEncoders(DriveToHopperFromGearPegData.DRIVE_2_DISTANCE));
	}
}
