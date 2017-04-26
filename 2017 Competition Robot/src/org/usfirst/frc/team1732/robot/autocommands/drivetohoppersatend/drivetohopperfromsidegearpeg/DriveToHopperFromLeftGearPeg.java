package org.usfirst.frc.team1732.robot.autocommands.drivetohoppersatend.drivetohopperfromsidegearpeg;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToHopperFromLeftGearPeg extends CommandGroup {

	public DriveToHopperFromLeftGearPeg() {
		addSequential(new DriveToHopperFromSideGearPeg(true));
	}
}
