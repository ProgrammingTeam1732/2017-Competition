package org.usfirst.frc.team1732.robot.commands.climber;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ArmSetOut extends InstantCommand {

	public ArmSetOut() {
		super();
		requires(Robot.arm);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		// if (Robot.gearIntake.isUp() && Robot.ballIntake.isPositionDown()) {
		// Robot.gearIntake.setStopperIn();
		Robot.arm.setOut();
		// }
	}

}
