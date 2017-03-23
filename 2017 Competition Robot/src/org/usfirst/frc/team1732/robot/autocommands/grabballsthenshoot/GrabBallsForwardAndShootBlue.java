package org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot;

import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncoders;
import org.usfirst.frc.team1732.robot.commands.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.flywheel.ShootTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GrabBallsForwardAndShootBlue extends CommandGroup {

	public GrabBallsForwardAndShootBlue() {
		addSequential(new InitGearIntake());
		addSequential(new DriveEncoders(GrabBallsForwardAndShootData.DRIVE_1_SETPOINT));

		addSequential(new TurnWithEncoders(GrabBallsForwardAndShootData.TURN_1_ANGLE_BLUE));

		addSequential(new DriveTime(GrabBallsForwardAndShootData.DRIVE_2_TIME,
									GrabBallsForwardAndShootData.DRIVE_2_SPEED));

		addSequential(new Wait(GrabBallsForwardAndShootData.WAIT_1_TIME));

		addSequential(new DriveTime(GrabBallsForwardAndShootData.DRIVE_3_TIME,
									GrabBallsForwardAndShootData.DRIVE_3_SPEED));

		addSequential(new TurnWithEncoders(GrabBallsForwardAndShootData.TURN_2_ANGLE_BLUE));

		addSequential(new DriveEncoders(GrabBallsForwardAndShootData.DRIVE_4_SETPOINT));
		addParallel(new EnableFlywheel());
		addSequential(new DriveTime(GrabBallsForwardAndShootData.TURN_3_TIME_BLUE,
									GrabBallsForwardAndShootData.DRIVE_LEFT_SPEED_BLUE,
									GrabBallsForwardAndShootData.DRIVE_RIGHT_SPEED_BLUE));
		addSequential(new ShootTime(GrabBallsForwardAndShootData.SHOOT_TIME));
	}
}