package org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot;

import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveGyro;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.flywheel.ShootTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GrabBallsForwardAndShootBlue extends CommandGroup {

	public GrabBallsForwardAndShootBlue() {
		addSequential(new InitGearIntake());
		addSequential(new DriveEncoders(GrabBallsForwardAndShootData.DRIVE_1_SETPOINT));
		addSequential(new TurnWithGyro(GrabBallsForwardAndShootData.TURN_1_ANGLE_BLUE));
		addSequential(new DriveEncoders(GrabBallsForwardAndShootData.DRIVE_3_SETPOINT));
		addSequential(new TurnWithGyro(GrabBallsForwardAndShootData.TURN_2_ANGLE_BLUE));
		addSequential(new DriveEncoders(GrabBallsForwardAndShootData.DRIVE_4_SETPOINT));
		addParallel(new EnableFlywheel());
		addSequential(new DriveGyro(GrabBallsForwardAndShootData.TURN_3_ANGLE_BLUE,
				GrabBallsForwardAndShootData.DRIVE_LEFT_SPEED_BLUE,
				GrabBallsForwardAndShootData.DRIVE_RIGHT_SPEED_BLUE));
		addSequential(new ShootTime(GrabBallsForwardAndShootData.SHOOT_TIME));
	}
}