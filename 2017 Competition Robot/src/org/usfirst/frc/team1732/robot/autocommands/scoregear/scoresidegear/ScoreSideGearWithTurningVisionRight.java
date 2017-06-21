package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear;

import org.usfirst.frc.team1732.robot.autocommands.drivetohoppersatend.drivetohopperfromsidegearpeg.DriveToHopperFromRightGearPeg;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreSideGearWithTurningVisionRight extends CommandGroup {

    public ScoreSideGearWithTurningVisionRight() {
	addSequential(new ScoreSideGearWithTurningVision(false));
	addSequential(new DriveToHopperFromRightGearPeg());
    }

}
