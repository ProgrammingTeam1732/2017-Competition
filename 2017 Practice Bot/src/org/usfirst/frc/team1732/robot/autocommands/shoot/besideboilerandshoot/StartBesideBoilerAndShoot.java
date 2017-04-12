package org.usfirst.frc.team1732.robot.autocommands.shoot.besideboilerandshoot;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autocommands.drivetohoppersatend.drivetohopperfromboiler.DriveToHopperFromBoiler;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.ballsystem.shooting.ShootTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartBesideBoilerAndShoot extends CommandGroup {

	public StartBesideBoilerAndShoot() {
		//		boolean isRed = Robot.isRedAlliance.getValue();

		addSequential(new EnableFlywheel());

		//		// face boiler
		//		double faceBoilerTime = 2;
		//		double faceBoilerLeftSpeed = 0;
		//		double faceBoilerRightSpeed = 0;
		//		if (isRed) {
		//			faceBoilerLeftSpeed = 0.7;
		//			faceBoilerRightSpeed = 0.1;
		//		} else {
		//			faceBoilerLeftSpeed = 0.1;
		//			faceBoilerRightSpeed = 0.7;
		//		}
		//		addSequential(new DriveTime(faceBoilerTime, faceBoilerLeftSpeed, faceBoilerRightSpeed));

		//		// Creep Forward while shooting
		//		double creepTime = shootTime;
		//		double creepSpeed = 0.3;
		//		addParallel(new DriveTime(creepTime, creepSpeed));

		// wait for flywheel to ramp up
		addSequential(new Wait(2));

		//		double smartDashboardWait = Robot.autoWaitTime.getValue();
		double shootTime = 10.5;//smartDashboardWait > 3 ? smartDashboardWait - 3 : smartDashboardWait;

		// shoot balls
		addSequential(new ShootTime(shootTime));
		addSequential(new DriveToHopperFromBoiler());
		addSequential(new InitGearIntake());
	}

	@Override
	public void interrupted() {
		end();
	}

	@Override
	public void end() {
		Robot.feeder.setStop(); // stop feeder
		Robot.gearIntake.setUp(); // make sure gearIntake is up after shuffling
		Robot.flywheel.disableAutoControl(); // turn off flywheel
	}
}
