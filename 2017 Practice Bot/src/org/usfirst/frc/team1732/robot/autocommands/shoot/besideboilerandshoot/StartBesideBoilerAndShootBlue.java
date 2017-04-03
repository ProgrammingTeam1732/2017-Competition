package org.usfirst.frc.team1732.robot.autocommands.shoot.besideboilerandshoot;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autocommands.drivetohoppersatend.drivetohopperfromboiler.DriveToHopperFromBoilerBlue;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.ShootTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartBesideBoilerAndShootBlue extends CommandGroup {

	public StartBesideBoilerAndShootBlue() {
		addSequential(new EnableFlywheel());
		// turn into boiler
		addSequential(new DriveTime(StartBesideBoilerAndShootData.DRIVE_INTO_BOILER_TIME,
									StartBesideBoilerAndShootData.DRIVE_INTO_BOILER_LEFT_SPEED_BLUE,
									StartBesideBoilerAndShootData.DRIVE_INTO_BOILER_RIGHT_SPEED_BLUE));
		// shoot balls
		addSequential(new ShootTime(StartBesideBoilerAndShootData.SHOOT_TIME));
		// wait to move
		addSequential(new Wait(Robot.autoWaitTime::getValue));
		addSequential(new DriveToHopperFromBoilerBlue());
	}
}
