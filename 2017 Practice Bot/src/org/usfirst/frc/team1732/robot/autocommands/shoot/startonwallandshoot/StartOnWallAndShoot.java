package org.usfirst.frc.team1732.robot.autocommands.shoot.startonwallandshoot;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autocommands.drivetohoppersatend.drivetohopperfromboiler.DriveToHopperFromBoiler;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.ballsystem.shooting.ShootTime;
import org.usfirst.frc.team1732.robot.commands.ballsystem.shooting.ShuffleBallsWithWait;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class StartOnWallAndShoot extends CommandGroup {

    public StartOnWallAndShoot() {
	boolean isRed = Robot.isRedAlliance.getValue();

	addSequential(new InitGearIntake());

	// wait to move
	addSequential(new Wait(Robot.autoWaitTime::getValue));

	// turn on flywheel
	addSequential(new EnableFlywheel());

	// drive to boiler
	double distanceToBoiler = Robot.startOnWallAndShootDistance.getValue();
	addSequential(new DriveEncoders(distanceToBoiler));

	// face boiler
	double faceBoilerTime = 2;
	double faceBoilerLeftSpeed = 0;
	double faceBoilerRightSpeed = 0;
	if (isRed) {
	    faceBoilerLeftSpeed = 0.7;
	    faceBoilerRightSpeed = 0.1;
	} else {
	    faceBoilerLeftSpeed = 0.1;
	    faceBoilerRightSpeed = 0.7;
	}
	addSequential(new DriveTime(faceBoilerTime, faceBoilerLeftSpeed, faceBoilerRightSpeed));

	double shootTime = 15;
	// Creep Forward while shooting
	double creepTime = shootTime;
	double creepSpeed = 0.3;
	addParallel(new DriveTime(creepTime, creepSpeed));

	// Shuffle balls while shooting, wait until against boiler to shuffle
	addParallel(new CommandGroup() {
	    {
		addSequential(new Wait(2));
		addSequential(new ShuffleBallsWithWait(), shootTime);
	    }
	});

	// shoot balls
	addSequential(new ShootTime(shootTime));
	addSequential(new DriveToHopperFromBoiler());
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
