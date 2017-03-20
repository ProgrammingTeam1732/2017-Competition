package org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot;

import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveGyro;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.flywheel.ShootTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GrabBallsKeylineAndShootBlue extends CommandGroup {

	public GrabBallsKeylineAndShootBlue() {
		addSequential(new InitGearIntake());
		addSequential(new DriveEncoders(GrabBallsKeylineAndShootData.DRIVE_1_SETPOINT));
		addSequential(new DriveTime(1, .1, .4));
		addSequential(new DriveTime(.5, .3, .3));
		//addSequential(new DriveTime(GrabBallsKeylineAndShootData.DRIVE_3_SETPOINT, .4));
		addSequential(new Wait(1));
		addSequential(new DriveTime(.7, -.6, -.6));
		addSequential(new TurnWithEncoders(GrabBallsKeylineAndShootData.TURN_2_ANGLE_BLUE));
		addSequential(new DriveEncoders(GrabBallsKeylineAndShootData.DRIVE_4_SETPOINT));
		addParallel(new EnableFlywheel());
		addSequential(new DriveTime(GrabBallsKeylineAndShootData.TURN_3_TIME_BLUE,
				GrabBallsKeylineAndShootData.DRIVE_LEFT_SPEED_BLUE,
				GrabBallsKeylineAndShootData.DRIVE_RIGHT_SPEED_BLUE));
		addSequential(new DriveTime(.2, 1, .2));
		addParallel(new DriveTime(GrabBallsKeylineAndShootData.SHOOT_TIME, .2, .2));
		addSequential(new ShootTime(GrabBallsKeylineAndShootData.SHOOT_TIME));
	}
}