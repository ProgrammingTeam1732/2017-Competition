package org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.helpercommands.CommandSwitcher;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class GrabBallsBackwardAndShoot extends CommandGroup {

	public GrabBallsBackwardAndShoot() {
		// drive backwards
		addSequential(new DriveEncoders(-110));

		// turn into hopper
		double angle = 45;
		TurnWithGyro ifRedTurn1 = new TurnWithGyro(angle);
		TurnWithGyro ifBlueTurn1 = new TurnWithGyro(-angle);
		addSequential(new CommandSwitcher(Robot::isRedAlliance, ifRedTurn1, ifBlueTurn1));

		// drive forward slightly
		addSequential(new DriveEncoders(15));

		// wait for balls to fall in
		addSequential(new Wait(2));

		// drive forward into boiler
		addSequential(new DriveEncoders(100));

		// turn into boiler
		// addSequential(new TurnWithGyro(90));
		// addSequential(new DriveEncoders(25));

		// shoot commands
		// addSequential(new ShootTime(5));

		// drive towards hoppers
		// addSequential(new DriveToHopperFromBoiler());
	}
}
