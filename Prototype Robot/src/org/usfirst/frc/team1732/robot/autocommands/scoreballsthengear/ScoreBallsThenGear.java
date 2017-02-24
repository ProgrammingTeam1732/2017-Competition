package org.usfirst.frc.team1732.robot.autocommands.scoreballsthengear;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autocommands.visionplacegear.VisionPlaceGear;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveGyro;
import org.usfirst.frc.team1732.robot.commands.helpercommands.CommandSwitcher;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreBallsThenGear extends CommandGroup {

	public ScoreBallsThenGear() {
		// drive forward slightly
		addSequential(new DriveEncoders(15, 15));

		// turn into boiler
		double angleTurn1 = 75;
		double leftSpeedTurn1 = 0.7;
		double rightSpeedTurn1 = -0.3;
		DriveGyro ifRedTurn1 = new DriveGyro(angleTurn1, leftSpeedTurn1, rightSpeedTurn1);
		DriveGyro ifBlueTurn1 = new DriveGyro(-angleTurn1, rightSpeedTurn1, leftSpeedTurn1);
		// addSequential(new CommandSwitcher(Robot::isRedAlliance, ifRedTurn1,
		// ifBlueTurn1));
		addSequential(ifBlueTurn1);

		// insert shooting commands
		addSequential(new Wait(5));
		// addSequential(new ShootTime(5));

		// drive backwards slightly to avoid knocking wall
		addSequential(new DriveEncoders(-15, -15));

		// turn to face peg
		double angleTurn2 = 140;
		double leftSpeedTurn2 = 0;
		double rightSpeedTurn2 = -1;
		DriveGyro ifRedTurn2 = new DriveGyro(angleTurn2, leftSpeedTurn2, rightSpeedTurn2);
		DriveGyro ifBlueTurn2 = new DriveGyro(-angleTurn2, rightSpeedTurn2, leftSpeedTurn2);
		addSequential(new CommandSwitcher(Robot::isRedAlliance, ifRedTurn2, ifBlueTurn2));
		// addSequential(new DriveEncoders(-15, -15)); // not sure what this was
		// supposed to do so I commented it out

		// drive back forward 40 inches after placing gear
		addSequential(new VisionPlaceGear(-40));
	}
}
