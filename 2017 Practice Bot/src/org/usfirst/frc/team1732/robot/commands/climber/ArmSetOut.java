package org.usfirst.frc.team1732.robot.commands.climber;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ArmSetOut extends InstantCommand {

	public ArmSetOut() {
		super();
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.arm);
		requires(Robot.gearIntake);
		requires(Robot.ballIntake);
	}

	// Called once when the command executes
	@Override
	protected void initialize() {
		// FIXME: this needs to happen to not break bounding box?
		// Robot.gearIntake.setIn();
		// Robot.ballIntake.setPosistionDown();
		Robot.arm.setOut();
	}

}
