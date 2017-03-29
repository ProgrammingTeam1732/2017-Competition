package org.usfirst.frc.team1732.robot.autocommands.scoregear.scoremiddlegear;

import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncoders;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeOutTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetUp;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreMiddleGearEncoders extends CommandGroup {

	public ScoreMiddleGearEncoders() {
		addSequential(new DriveEncoders(75));

		addSequential(new GearIntakeSetDown());
		addParallel(new CommandGroup() {
			{
				addSequential(new GearIntakeOutTime(.5));
				addSequential(new GearIntakeSetUp());
			}
		});
		addSequential(new DriveEncoders(-40));
	}

}