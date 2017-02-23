package org.usfirst.frc.team1732.robot.autocommands.scoresidegear;

import java.util.function.BooleanSupplier;

import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.helpercommands.CommandSwitcher;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreSideGearPart1 extends CommandGroup {

	public ScoreSideGearPart1(BooleanSupplier isLeft) {
		// drive forward to turn to position
		addSequential(new DriveEncoders(55, 55));

		// turn to face gear peg
		double angle = 60;
		TurnWithGyro ifLeftSide = new TurnWithGyro(angle);
		TurnWithGyro ifRightSide = new TurnWithGyro(angle);
		addSequential(new CommandSwitcher(isLeft, ifLeftSide, ifRightSide));

		// wait after turning
		addSequential(new Wait(0.2));
	}
}
