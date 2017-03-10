package org.usfirst.frc.team1732.robot.autocommands;

import java.util.function.BooleanSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.GrabBallsBackwardAndShootBlue;
import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.GrabBallsBackwardAndShootRed;
import org.usfirst.frc.team1732.robot.autocommands.scoreballsthengear.ScoreBallsThenGearBlue;
import org.usfirst.frc.team1732.robot.autocommands.scoreballsthengear.ScoreBallsThenGearRed;
import org.usfirst.frc.team1732.robot.autocommands.scoregearthenballs.ScoreGearThenBallsBlue;
import org.usfirst.frc.team1732.robot.autocommands.scoregearthenballs.ScoreGearThenBallsRed;
import org.usfirst.frc.team1732.robot.autocommands.scoremiddlegear.ScoreMiddleGear;
import org.usfirst.frc.team1732.robot.autocommands.scoresidegear.ScoreSideGearLeft;
import org.usfirst.frc.team1732.robot.autocommands.scoresidegear.ScoreSideGearRight;
import org.usfirst.frc.team1732.robot.autocommands.sidetwogearauto.SideTwoGearAutoLeft;
import org.usfirst.frc.team1732.robot.autocommands.sidetwogearauto.SideTwoGearAutoRight;
import org.usfirst.frc.team1732.robot.autocommands.twogearauto.TwoGearAutoLeft;
import org.usfirst.frc.team1732.robot.autocommands.twogearauto.TwoGearAutoRight;
import org.usfirst.frc.team1732.robot.commands.drivetrain.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardGroup;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooser implements SmartDashboardGroup {

	/**
	 * Class to help with choosing autos Just add autos to here, if there are
	 * separate red and blue add the red first then the blue
	 */
	private static enum AutoModes {
		ScoreMiddleGear(new ScoreMiddleGear()),
		ScoreRightSideGear(new ScoreSideGearRight()),
		ScoreLeftSideGear(new ScoreSideGearLeft()),
		ScoreGearThenBalls(new ScoreGearThenBallsRed(), new ScoreGearThenBallsBlue()),
		ScoreBallsThenGear(new ScoreBallsThenGearRed(), new ScoreBallsThenGearBlue()),
		GrabBallsBackwardThenShoot(new GrabBallsBackwardAndShootRed(), new GrabBallsBackwardAndShootBlue()),
		TwoGearAutoLeft(new TwoGearAutoLeft()),
		TwoGearAutoRight(new TwoGearAutoRight()),
		SideTwoGearAutoLeft(new SideTwoGearAutoLeft()),
		SideTwoGearAutoRight(new SideTwoGearAutoRight()),
		// DriveStraight90Inches(new DriveEncoders(90)),
		// Turn180Degrees(new TurnWithGyro(180)),
		DriveTime(new DriveTime(5, 0.3)),
		DriveTimeBackwards(new DriveTime(5, -0.3)),
		ResetEncoders(new ClearTotalDistance());

		private final BooleanSupplier	isRedAlliance;
		private final Command			ifRed;
		private final Command			ifBlue;

		AutoModes(Command ifRed, Command ifBlue) {
			isRedAlliance = Robot::isRedAlliance;
			this.ifRed = ifRed;
			this.ifBlue = ifBlue;
		}

		AutoModes(Command command) {
			isRedAlliance = () -> true;
			this.ifRed = command;
			this.ifBlue = command;
		}

		private Command getSelected() {
			if (isRedAlliance.getAsBoolean()) {
				return ifRed;
			} else {
				return ifBlue;
			}
		}
	}

	private final SendableChooser<AutoModes> autoChooser = new SendableChooser<>();

	public AutoChooser() {
		autoChooser.addDefault(AutoModes.ScoreMiddleGear.name(), AutoModes.ScoreMiddleGear);
		AutoModes[] autoModes = AutoModes.values();
		for (int i = 1; i < autoModes.length; i++) {
			autoChooser.addObject(i + ": " + autoModes[i].name(), autoModes[i]);
		}
		SmartDashboard.putData("AutonomousChooser", autoChooser);
	}

	public Command getSelected() {
		return autoChooser.getSelected().getSelected();
	}

	@Override
	public void addToSmartDashboard(MySmartDashboard dashboard) {
		// TODO Auto-generated method stub

	}

}