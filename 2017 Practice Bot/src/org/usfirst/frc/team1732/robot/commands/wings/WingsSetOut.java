package org.usfirst.frc.team1732.robot.commands.wings;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class WingsSetOut extends InstantCommand {

	public WingsSetOut() {
		super();
		requires(Robot.wings);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		// if (Robot.gearIntake.isUp() && Robot.ballIntake.isPositionDown()) {
		// Robot.gearIntake.setStopperIn();
		Robot.wings.setOut();
		// }
	}

}
