package org.usfirst.frc.team1732.robot.autocommands.drivetohoppersatend.drivetohopperfrommiddlegearpeg;

import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.gyro.TurnWithGyro;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToHopperFromMiddleGearPegBlue extends CommandGroup {

	public DriveToHopperFromMiddleGearPegBlue() {
		addSequential(new DriveEncoders(DriveToHopperFromMiddleGearPegData.DRIVE_1_DISTANCE));
		addSequential(new TurnWithGyro(DriveToHopperFromMiddleGearPegData.TURN_1_ANGLE_BLUE));
		addSequential(new DriveEncoders(DriveToHopperFromMiddleGearPegData.DRIVE_2_DISTANCE));
		addSequential(new TurnWithGyro(DriveToHopperFromMiddleGearPegData.TURN_2_ANGLE_BLUE));
		addSequential(new DriveEncoders(DriveToHopperFromMiddleGearPegData.DRIVE_3_DISTANCE));
	}
}
