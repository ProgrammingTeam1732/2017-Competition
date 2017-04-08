package org.usfirst.frc.team1732.robot.commands.ballsystem.shooting;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeSetIn;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.motor.GearIntakeSetStop;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetDownNoCheck;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetUpNoCheck;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.stopper.GearIntakeSetStopperIn;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ShuffleBallsWithWait extends CommandGroup {

	public ShuffleBallsWithWait() {
		this(11, 1.36);
	}

	public ShuffleBallsWithWait(int shuffleAmount, double secondsPerShuffle) {
		// initially put stopper down
		addSequential(new GearIntakeSetIn());
		addSequential(new GearIntakeSetDownNoCheck());
		addSequential(new Wait(0.3));
		addSequential(new GearIntakeSetStopperIn());
		addSequential(new Wait(0.1));
		addSequential(new GearIntakeSetUpNoCheck());
		addSequential(new GearIntakeSetStop());

		double WAIT = secondsPerShuffle - 0.3;
		// amount of time to wait before running gear intake in

		class ShuffleBallUnit extends CommandGroup {

			public ShuffleBallUnit() {
				addSequential(new Wait(WAIT));

				// shuffle balls
				addSequential(new GearIntakeSetIn());
				addSequential(new GearIntakeSetDownNoCheck());
				addSequential(new Wait(0.3));
				addSequential(new GearIntakeSetUpNoCheck());
				addSequential(new GearIntakeSetStop());
			}
		}

		for (int i = 0; i < shuffleAmount; i++) {
			addSequential(new ShuffleBallUnit());
		}

	}

}
