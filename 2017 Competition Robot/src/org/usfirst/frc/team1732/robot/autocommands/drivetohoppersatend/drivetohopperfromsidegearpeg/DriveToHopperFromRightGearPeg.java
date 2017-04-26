package org.usfirst.frc.team1732.robot.autocommands.drivetohoppersatend.drivetohopperfromsidegearpeg;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveToHopperFromRightGearPeg extends CommandGroup {

	public DriveToHopperFromRightGearPeg() {
		addSequential(new DriveToHopperFromSideGearPeg(false));
	}
}
