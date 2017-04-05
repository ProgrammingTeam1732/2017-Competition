package org.usfirst.frc.team1732.robot.autocommands;

import java.util.function.BooleanSupplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.keylinehoppershoot.KeylineHopperShootBlue;
import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.keylinehoppershoot.KeylineHopperShootRed;
import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.straighthoppershoot.StraightHopperShootBlue;
import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.straighthoppershoot.StraightHopperShootRed;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoremiddlegear.ScoreMiddleGear;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.ScoreSideGearLeft;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.ScoreSideGearRight;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.sidetwogearauto.SideTwoGearAutoLeft;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.sidetwogearauto.SideTwoGearAutoRight;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.twogearauto.TwoGearAutoLeft;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.twogearauto.TwoGearAutoRight;
import org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoreballsthensidegear.ScoreBallsThenGearSideRed;
import org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoreballsthensidegear.ScoreBallsThenSideGearBlue;
import org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoremiddlegearthenballs.ScoreMiddleGearThenBallsEncodersBlue;
import org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoremiddlegearthenballs.ScoreMiddleGearThenBallsEncodersRed;
import org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoresidegearthenballs.ScoreSideGearThenBallsBlue;
import org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoresidegearthenballs.ScoreSideGearThenBallsRed;
import org.usfirst.frc.team1732.robot.autocommands.shoot.besideboilerandshoot.StartBesideBoilerAndShootBlue;
import org.usfirst.frc.team1732.robot.autocommands.shoot.besideboilerandshoot.StartBesideBoilerAndShootRed;
import org.usfirst.frc.team1732.robot.autocommands.shoot.startonwallandshoot.StartOnWallAndShootBlue;
import org.usfirst.frc.team1732.robot.autocommands.shoot.startonwallandshoot.StartOnWallAndShootRed;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ClearTotalDistance;
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

		ScoreSideGearThenBalls(new ScoreSideGearThenBallsRed(), new ScoreSideGearThenBallsBlue()),
		ScoreCenterGearThenBalls(new ScoreSideGearThenBallsRed(), new ScoreSideGearThenBallsBlue()),
		ScoreBallsThenGear(new ScoreBallsThenGearSideRed(), new ScoreBallsThenSideGearBlue()),

		GrabBallsForwardThenShoot(new StraightHopperShootRed(), new StraightHopperShootBlue()),
		GrabBallsKeylineAndShoot(new KeylineHopperShootRed(), new KeylineHopperShootBlue()),

		TwoGearAutoLeft(new TwoGearAutoLeft()),
		TwoGearAutoRight(new TwoGearAutoRight()),
		SideTwoGearAutoLeft(new SideTwoGearAutoLeft()),
		SideTwoGearAutoRight(new SideTwoGearAutoRight()),

		StartOnWallAndShoot(new StartOnWallAndShootRed(), new StartOnWallAndShootBlue()),
		StartBesideBoilerAndShoot(new StartBesideBoilerAndShootRed(), new StartBesideBoilerAndShootBlue()),

		// testing
		DriveEncodersBrake(new DriveEncodersWithBraking(110, 20)),
		TurnWithEncodersWithBraking90(new TurnWithEncodersWithBraking(90)),
		TurnWithEncodersWithBraking180(new TurnWithEncodersWithBraking(180)),
		TurnWithEncodersWithBraking45(new TurnWithEncodersWithBraking(45)),

		// Turn180Degrees(new TurnWithEncoders(180)),
		// Turn90Degrees(new TurnWithEncoders(90)),
		// TurnWithEncoders180(new TurnWithEncoders(-180)),
		// TurnWithEncoders90(new TurnWithEncoders(-90)),
		// TurnWithEncoders45(new TurnWithEncoders(-45)),
		DriveTime(new DriveTime(2, 0.5)),
		DriveTimeBackwards(new DriveTime(2, -0.5)),

		ResetEncoders(new ClearTotalDistance()),

		ScoreMiddleGearThenBallsEncoders(
				new ScoreMiddleGearThenBallsEncodersRed(),
				new ScoreMiddleGearThenBallsEncodersBlue());
		// ResetEncoders(new ClearTotalDistance()),
		// DriveEncodersFar(new DriveEncoders(97.5)),
		// DriveEncodersShort(new DriveEncoders(40));

		private final BooleanSupplier isRedAlliance;
		private final Command ifRed;
		private final Command ifBlue;

		AutoModes(Command ifRed, Command ifBlue) {
			isRedAlliance = Robot.isRedAlliance::getValue;
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
		autoChooser.addDefault(
				AutoModes.GrabBallsKeylineAndShoot.ordinal() + ": " + AutoModes.GrabBallsKeylineAndShoot.name(),
				AutoModes.GrabBallsKeylineAndShoot);
		AutoModes[] autoModes = AutoModes.values();
		for (int i = 0; i < autoModes.length; i++) {
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
		chosenauto = dashboard.addItem(SmartDashboardItem.newDoubleReciever("Auto Number",
				(double) AutoModes.GrabBallsKeylineAndShoot.ordinal()));
	}

}