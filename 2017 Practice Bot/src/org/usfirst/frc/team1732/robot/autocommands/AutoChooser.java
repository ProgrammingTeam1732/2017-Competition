package org.usfirst.frc.team1732.robot.autocommands;

import java.util.function.BooleanSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.GrabBallsForwardAndShootBlue;
import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.GrabBallsForwardAndShootRed;
import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.GrabBallsKeylineAndShootBlue;
import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.GrabBallsKeylineAndShootRed;
import org.usfirst.frc.team1732.robot.autocommands.scoreballsthengear.ScoreBallsThenGearBlue;
import org.usfirst.frc.team1732.robot.autocommands.scoreballsthengear.ScoreBallsThenGearRed;
import org.usfirst.frc.team1732.robot.autocommands.scorecenterpegthenballs.ScoreCenterGearThenBallsBlue;
import org.usfirst.frc.team1732.robot.autocommands.scorecenterpegthenballs.ScoreCenterGearThenBallsRed;
import org.usfirst.frc.team1732.robot.autocommands.scoremiddlegear.ScoreMiddleGear;
import org.usfirst.frc.team1732.robot.autocommands.scoresidegear.ScoreSideGearLeft;
import org.usfirst.frc.team1732.robot.autocommands.scoresidegear.ScoreSideGearRight;
import org.usfirst.frc.team1732.robot.autocommands.scoresidegearthenballs.ScoreGearThenBallsBlue;
import org.usfirst.frc.team1732.robot.autocommands.scoresidegearthenballs.ScoreGearThenBallsRed;
import org.usfirst.frc.team1732.robot.autocommands.sidetwogearauto.SideTwoGearAutoLeft;
import org.usfirst.frc.team1732.robot.autocommands.sidetwogearauto.SideTwoGearAutoRight;
import org.usfirst.frc.team1732.robot.autocommands.twogearauto.TwoGearAutoLeft;
import org.usfirst.frc.team1732.robot.autocommands.twogearauto.TwoGearAutoRight;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncodersWithBraking;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncodersWithBraking;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardGroup;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;

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
		// GrabBallsBackwardThenShoot(new GrabBallsBackwardAndShootRed(), new
		// GrabBallsBackwardAndShootBlue()),
		GrabBallsForwardThenShoot(new GrabBallsForwardAndShootRed(), new GrabBallsForwardAndShootBlue()),
		TwoGearAutoLeft(new TwoGearAutoLeft()),
		TwoGearAutoRight(new TwoGearAutoRight()),
		SideTwoGearAutoLeft(new SideTwoGearAutoLeft()),
		SideTwoGearAutoRight(new SideTwoGearAutoRight()),
		GrabBallsForwardAndShoot(new GrabBallsForwardAndShootRed(), new GrabBallsForwardAndShootBlue()),
		GrabBallsKeylineAndShoot(new GrabBallsKeylineAndShootRed(), new GrabBallsKeylineAndShootBlue()),
		DriveEncodersBrake(new DriveEncodersWithBraking(110, 20)),
		TurnWithEncodersWithBraking90(new TurnWithEncodersWithBraking(90)),
		TurnWithEncodersWithBraking180(new TurnWithEncodersWithBraking(180)),
		TurnWithEncodersWithBraking45(new TurnWithEncodersWithBraking(45)),
		ScoreCenterGearAndBalls(new ScoreCenterGearThenBallsRed(), new ScoreCenterGearThenBallsBlue());

		// Turn180Degrees(new TurnWithGyro(180)),
		// Turn90Degrees(new TurnWithGyro(90)),
		// TurnWithEncoders180(new TurnWithEncoders(-180)),
		// TurnWithEncoders90(new TurnWithEncoders(-90)),
		// TurnWithEncoders45(new TurnWithEncoders(-45)),
		// DriveTime(new DriveTime(5, 0.3)),
		// DriveTimeBackwards(new DriveTime(5, -0.3)),
		// ResetEncoders(new ClearTotalDistance()),
		// DriveEncodersFar(new DriveEncoders(97.5)),
		// DriveEncodersShort(new DriveEncoders(40));

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
		autoChooser.addDefault(	AutoModes.ScoreMiddleGear.ordinal() + ": " + AutoModes.ScoreMiddleGear.name(),
								AutoModes.ScoreMiddleGear);
		AutoModes[] autoModes = AutoModes.values();
		for (int i = 1; i < autoModes.length; i++) {
			autoChooser.addObject(autoModes[i].ordinal() + ": " + autoModes[i].name(), autoModes[i]);
		}
		SmartDashboard.putData("AutonomousChooser", autoChooser);
	}

	public Command getSelected() {
		int value = 0;
		if (chosenauto != null)
			value = chosenauto.getValue().intValue();
		if (value < 0 || value >= AutoModes.values().length)
			value = 0;
		return AutoModes.values()[chosenauto.getValue().intValue()].getSelected();
		// return autoChooser.getSelected().getSelected();
	}

	private SmartDashboardItem<Double> chosenauto;

	@Override
	public void addToSmartDashboard(MySmartDashboard dashboard) {
		chosenauto = dashboard.addItem(SmartDashboardItem.newDoubleReciever("Auto Number", 0.0));
	}

}