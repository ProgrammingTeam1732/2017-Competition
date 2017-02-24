package org.usfirst.frc.team1732.robot.autocommands.drivetohoppersatend;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.helpercommands.CommandSwitcher;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DriveToHopperFromBoiler extends CommandGroup {

	public DriveToHopperFromBoiler() {
		// backup slightly
		addSequential(new DriveEncoders(-25));

		// turn towards hoppers
		double angle = -135;
		TurnWithGyro ifRed = new TurnWithGyro(angle);
		TurnWithGyro ifBlue = new TurnWithGyro(-angle);
		addSequential(new CommandSwitcher(Robot::isRedAlliance, ifRed, ifBlue));

		// drive towards hoppers
		addSequential(new DriveEncoders(60));
	}
}
