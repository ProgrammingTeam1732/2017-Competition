package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.base;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.placegear.EncoderPlaceGear;
import org.usfirst.frc.team1732.robot.commands.placegear.VisionPlaceGear;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreSideGear extends CommandGroup {

    public ScoreSideGear(boolean useVision, boolean isLeft) {
	addSequential(new InitGearIntake());
	// wait to move
	addSequential(new Wait(Robot.autoWaitTime.getValue()));

	// get into position to score gear
	addSequential(new ScoreSideGearPart1(isLeft));

	// score gear, drive back 25 inches
	double driveBackDistance = -25;
	if (useVision) {
	    double maxSetpoint = 80;
	    addSequential(new VisionPlaceGear(driveBackDistance, maxSetpoint, true));
	} else {
	    double driveForwardDistance = 15;
	    addSequential(new EncoderPlaceGear(driveForwardDistance, driveBackDistance));
	}
	// drive to hoppers
	// addSequential(new DriveToHopperFromLeftGearPeg());
    }

}
