package org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeSetIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeSetStop;
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

		double WAIT = 0; // amount of time to wait before running gear intake in
		double IN_TIME = 1; // amount of time to run gear intake in
		// sum of these times is time between shuffles

		class ShuffleBallUnit extends CommandGroup {

			public ShuffleBallUnit() {
				addSequential(new Wait(WAIT));
				addSequential(new GearIntakeSetIn());
				addSequential(new Wait(IN_TIME));

				// shuffle balls
				addSequential(new GearIntakeSetDownNoCheck());
				addSequential(new Wait(0.3));
				addSequential(new GearIntakeSetUpNoCheck());
				if (WAIT != 0)
					addSequential(new GearIntakeSetStop());
			}
		}

		for (int i = 0; i < shuffleAmount; i++) {
			addSequential(new ShuffleBallUnit());
		}

	}

}
