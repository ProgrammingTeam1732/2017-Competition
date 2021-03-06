package org.usfirst.frc.team1732.robot.autocommands.scoreballsthengear;

import org.usfirst.frc.team1732.robot.autocommands.visionplacegear.VisionPlaceGear;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveGyro;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreBallsThenGearRed extends CommandGroup {

	public ScoreBallsThenGearRed() {
		// drive forward slightly
		addSequential(new DriveEncoders(ScoreBallsThenGearData.DRIVE_1_SETPOINT));

		// turn into boiler
		addSequential(new DriveGyro(ScoreBallsThenGearData.TURN_1_ANGLE_RED,
									ScoreBallsThenGearData.TURN_1_LEFT_SPEED_RED,
									ScoreBallsThenGearData.TURN_1_RIGHT_SPEED_RED));

		// insert shooting commands
		addSequential(new Wait(ScoreBallsThenGearData.SHOOT_TIME));
		// addSequential(new ShootTime(ScoreBallsThenGearData.SHOOT_TIME));

		// drive backwards slightly to avoid knocking wall
		addSequential(new DriveEncoders(ScoreBallsThenGearData.DRIVE_2_SETPOINT));

		// turn to face peg
		addSequential(new DriveGyro(ScoreBallsThenGearData.TURN_2_ANGLE_RED,
									ScoreBallsThenGearData.TURN_2_LEFT_SPEED_RED,
									ScoreBallsThenGearData.TURN_2_RIGHT_SPEED_RED));

		// drive forward a little bit
		addSequential(new DriveEncoders(ScoreBallsThenGearData.DRIVE_3_SETPOINT));

		// drive back forward 40 inches after placing gear
		addSequential(new VisionPlaceGear(ScoreBallsThenGearData.DRIVE_4_DRIVE_BACK_SETPOINT));
	}
}
