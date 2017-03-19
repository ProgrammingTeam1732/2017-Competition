package org.usfirst.frc.team1732.robot.commands.vision;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class FlashLEDsGroup extends CommandGroup {

	public FlashLEDsGroup() {
		
	}

	public void execute() {
		if (!Robot.pixyCamera.isLightOn())
			addSequential(new TurnLightsOn());
		else
			addSequential(new TurnLightsOff());
		addSequential(new Wait(.25));
	}

	public boolean isFinished() {
		return Robot.gearIntake.isUp();
	}

	public void end() {
		addSequential(new TurnLightsOff());
	}

}
