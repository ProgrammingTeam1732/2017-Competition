package org.usfirst.frc.team1732.robot.autocommands.scoresidegear;

import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreSideGearPart1Left extends CommandGroup {

	public ScoreSideGearPart1Left() {
		// drive forward to turn to position
		addSequential(new DriveEncoders(ScoreSideGearData.DRIVE_1_SETPOINT));

		addSequential(new TurnWithGyro(ScoreSideGearData.TURN_1_LEFT_SIDE));

		// wait after turning
		addSequential(new Wait(ScoreSideGearData.WAIT_1_TIME));
	}
}
