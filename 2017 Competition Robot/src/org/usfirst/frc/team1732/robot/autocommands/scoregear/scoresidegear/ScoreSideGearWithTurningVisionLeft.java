package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear;

import org.usfirst.frc.team1732.robot.autocommands.drivetohoppersatend.drivetohopperfromsidegearpeg.DriveToHopperFromLeftGearPeg;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreSideGearWithTurningVisionLeft extends CommandGroup {

    public ScoreSideGearWithTurningVisionLeft() {
	addSequential(new ScoreSideGearWithTurningVision(true));
	addSequential(new DriveToHopperFromLeftGearPeg());
    }

}
