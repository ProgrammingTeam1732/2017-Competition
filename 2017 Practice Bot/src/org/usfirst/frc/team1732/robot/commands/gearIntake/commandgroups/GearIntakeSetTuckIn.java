package org.usfirst.frc.team1732.robot.commands.gearIntake.commandgroups;

import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetDown;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.position.GearIntakeSetUp;
import org.usfirst.frc.team1732.robot.commands.gearIntake.base.stopper.GearIntakeSetStopperIn;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearIntakeSetTuckIn extends CommandGroup {

    public GearIntakeSetTuckIn() {
	addSequential(new GearIntakeSetDown());
	addSequential(new Wait(0.2));
	addSequential(new GearIntakeSetUp());
	addSequential(new GearIntakeSetStopperIn());
    }
}
