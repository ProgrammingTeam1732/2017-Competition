package org.usfirst.frc.team1732.robot.autocommands.scoremiddlegear;

import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeOutTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.GearIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups.InitGearIntake;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreMiddleGear extends CommandGroup {

	public ScoreMiddleGear() {
		addSequential(new InitGearIntake());
		addSequential(new DriveEncoders(64, 0));

		addSequential(new GearIntakeSetDown());
		addParallel(new CommandGroup() {
			{
				addSequential(new GearIntakeOutTime(.5));
				addSequential(new GearIntakeSetUp());
			}
		});
		addSequential(new DriveEncoders(-40, 0));

	}

}