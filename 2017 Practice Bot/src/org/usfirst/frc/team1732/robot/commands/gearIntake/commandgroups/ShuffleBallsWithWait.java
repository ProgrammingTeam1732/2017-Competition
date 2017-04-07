package org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeInTime;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeSetIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetDownNoCheck;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetUpNoCheck;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.stopper.GearIntakeSetStopperIn;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShuffleBallsWithWait extends CommandGroup {

	public ShuffleBallsWithWait() {
		this(15);
	}

	public ShuffleBallsWithWait(int shuffleAmount) {
		// initially put stopper down
		addSequential(new GearIntakeSetIn());

		addSequential(new GearIntakeSetDownNoCheck());
		addSequential(new Wait(0.3));
		addSequential(new GearIntakeSetStopperIn());
		addSequential(new Wait(0.1));
		addSequential(new GearIntakeSetUpNoCheck());

		double IN_TIME = 1;
		double WAIT = 0;

		class ShuffleBallUnit extends CommandGroup {

			public ShuffleBallUnit() {
				addSequential(new GearIntakeInTime(IN_TIME));
				addSequential(new Wait(WAIT));
				addSequential(new ShuffleBalls());
			}
		}

		for (int i = 0; i < shuffleAmount; i++) {
			addSequential(new ShuffleBallUnit());
		}

	}

}
