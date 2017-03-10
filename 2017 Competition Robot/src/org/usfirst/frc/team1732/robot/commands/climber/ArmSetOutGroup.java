package org.usfirst.frc.team1732.robot.commands.climber;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ArmSetOutGroup extends CommandGroup {

	public ArmSetOutGroup() {

		// if (Robot.gearIntake.isStopperOut()) {
		// addSequential(new GearIntakeSetStopperIn());
		// }
		// addSequential(new BallIntakeSetDown());
		// if (Robot.gearIntake.isUp() && Robot.ballIntake.isPositionDown()) {
		// Robot.gearIntake.setStopperIn();
		addSequential(new ArmSetOut());
		// }
	}

	@Override
	public void interrupted() {
		super.interrupted();
		System.out.println("interrupted");
	}
}
