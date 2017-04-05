package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.EncoderPlaceGear;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreSideGearLeftEncoders extends CommandGroup {

	public ScoreSideGearLeftEncoders() {
		addSequential(new InitGearIntake());
		// wait to move
		addSequential(new Wait(Robot.autoWaitTime.getValue()));

		// get into position to score gear
		addSequential(new ScoreSideGearPart1Left());

		// Drive, place, drive back
		addSequential(new EncoderPlaceGear(	ScoreSideGearData.DRIVE_INTO_GEARPEG_SETPOINT,
											ScoreSideGearData.DRIVE_2_DRIVE_BACK_SETPOINT));

	}
}
