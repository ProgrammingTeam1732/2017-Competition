package org.usfirst.frc.team1732.robot.autocommands.scoreballsthengear;

import org.usfirst.frc.team1732.robot.autocommands.visionplacegear.VisionPlaceGear;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveGyro;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreBallsThenGearBlue extends CommandGroup {

	public ScoreBallsThenGearBlue() {
		// drive forward slightly
		addSequential(new DriveEncoders(ScoreBallsThenGearData.DRIVE_1_SETPOINT));

		// turn into boiler
		addSequential(new DriveGyro(ScoreBallsThenGearData.TURN_1_ANGLE_BLUE,
									ScoreBallsThenGearData.TURN_1_LEFT_SPEED_BLUE,
									ScoreBallsThenGearData.TURN_1_RIGHT_SPEED_BLUE));

		// insert shooting commands
		addSequential(new Wait(ScoreBallsThenGearData.SHOOT_TIME));
		// addSequential(new ShootTime(ScoreBallsThenGearData.SHOOT_TIME));

		// drive backwards slightly to avoid knocking wall
		addSequential(new DriveEncoders(ScoreBallsThenGearData.DRIVE_2_SETPOINT));

		// turn to face peg
		addSequential(new DriveGyro(ScoreBallsThenGearData.TURN_2_ANGLE_BLUE,
									ScoreBallsThenGearData.TURN_2_LEFT_SPEED_BLUE,
									ScoreBallsThenGearData.TURN_2_RIGHT_SPEED_BLUE));

		// addSequential(new
		// DriveEncoders(ScoreBallsThenGearData.DRIVE_3_SETPOINT));
		// not sure what above line was supposed to do so I commented it out

		// drive back forward 40 inches after placing gear
		addSequential(new VisionPlaceGear(ScoreBallsThenGearData.DRIVE_4_DRIVE_BACK_SETPOINT));

		// drive to hoppers
		// addSequential(new DriveToHopperFromLeftGearPeg());
	}
}
