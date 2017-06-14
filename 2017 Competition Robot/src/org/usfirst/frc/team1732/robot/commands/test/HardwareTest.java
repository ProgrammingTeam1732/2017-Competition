package org.usfirst.frc.team1732.robot.commands.test;

import org.usfirst.frc.team1732.robot.commands.climber.ArmSetIn;
import org.usfirst.frc.team1732.robot.commands.climber.ArmSetOut;
import org.usfirst.frc.team1732.robot.commands.drivetrain.SetMotorSpeed;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Print;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class HardwareTest extends CommandGroup {

	public HardwareTest() {
		double waitTime = 0.5;
		// doesn't work as intended
		// addTest("Flywheel Forward", new FlywheelForward(), 0.5);
		// addTest("Flywheel Stop", new FlywheelStop(), 0.5);
		// addTest("Flywheel Reverse", new FlywheelReverse(), 0.5);
		// addTest("Flywheel Stop", new FlywheelStop(), 0.5);

		addTest("Run DriveTrain Forward", new SetMotorSpeed(0.5, 0.5), waitTime);
		addTest("Run DriveTrain Stop", new SetMotorSpeed(0, 0), waitTime);
		addTest("Run DriveTrain Reverse", new SetMotorSpeed(-0.5, -0.5), waitTime);
		addTest("Run DriveTrain Stop", new SetMotorSpeed(0, 0), waitTime);

		addTest("Setting Craw Out", new ArmSetOut(), waitTime);
		addTest("Setting Craw In", new ArmSetIn(), waitTime);

		// addTest("")
	}

	private void addTest(String toPrint, Command test, double wait) {
		addSequential(new Print(toPrint));
		addSequential(test);
		addSequential(new Wait(wait));
	}
}
