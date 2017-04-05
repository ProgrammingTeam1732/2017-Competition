package org.usfirst.frc.team1732.robot.autocommands;

import java.util.function.Supplier;

import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.keylinehoppershoot.KeylineHopperShoot;
import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.straighthoppershoot.StraightHopperShoot;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoremiddlegear.ScoreMiddleGear;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoremiddlegear.ScoreMiddleGearEncoders;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.ScoreSideGearLeft;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.ScoreSideGearLeftEncoders;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.ScoreSideGearRight;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.ScoreSideGearRightEncoders;
import org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoreballsthensidegear.ScoreBallsThenSideGear;
import org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoreballsthensidegear.ScoreBallsThenSideGearEncoders;
import org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoremiddlegearthenballs.ScoreMiddleGearThenBalls;
import org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoremiddlegearthenballs.ScoreMiddleGearThenBallsEncoders;
import org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoresidegearthenballs.ScoreSideGearThenBalls;
import org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoresidegearthenballs.ScoreSideGearThenBallsEncoders;
import org.usfirst.frc.team1732.robot.autocommands.shoot.besideboilerandshoot.StartBesideBoilerAndShoot;
import org.usfirst.frc.team1732.robot.autocommands.shoot.startonwallandshoot.StartOnWallAndShoot;
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

	public static enum AutoModes {
		MiddleGear(ScoreMiddleGear::new),
		MiddleGearEncoders(ScoreMiddleGearEncoders::new),
		RightGear(ScoreSideGearRight::new),
		RightGearEncoders(ScoreSideGearRightEncoders::new),
		LeftGear(ScoreSideGearLeft::new),
		LeftGearEncoders(ScoreSideGearLeftEncoders::new),

		LeftGearThenShootBalls(ScoreSideGearThenBalls::new),
		LeftGearThenShootBallsEncoders(ScoreSideGearThenBallsEncoders::new),
		MiddleGearThenShootBalls(ScoreMiddleGearThenBalls::new),
		MiddleGearThenShootBallsEncoders(ScoreMiddleGearThenBallsEncoders::new),
		ShootBallsThenSideGear(ScoreBallsThenSideGear::new),
		ShootBallsThenSideGearEncoders(ScoreBallsThenSideGearEncoders::new),

		GrabBallsForwardThenShoot(StraightHopperShoot::new),
		GrabBallsKeylineThenShoot(KeylineHopperShoot::new),

		//		TwoGearAutoLeft(new TwoGearAutoLeft()),
		//		TwoGearAutoRight(new TwoGearAutoRight()),
		//		SideTwoGearAutoLeft(new SideTwoGearAutoLeft()),
		//		SideTwoGearAutoRight(new SideTwoGearAutoRight()),

		StartOnWallThenShoot(StartOnWallAndShoot::new),
		StartBesideBoilerThenShoot(StartBesideBoilerAndShoot::new),

		// testing
		DriveEncodersBrake(() -> new DriveEncodersWithBraking(110, 20)),
		TurnWithEncodersWithBraking90(() -> new TurnWithEncodersWithBraking(90)),
		TurnWithEncodersWithBraking180(() -> new TurnWithEncodersWithBraking(180)),
		TurnWithEncodersWithBraking45(() -> new TurnWithEncodersWithBraking(45)),

		DriveTime(() -> new DriveTime(2, 0.5)),
		DriveTimeBackwards(() -> new DriveTime(2, -0.5)),
		ResetEncoders(() -> new ClearTotalDistance());

		// DriveEncodersFar(new DriveEncoders(97.5)),
		// DriveEncodersShort(new DriveEncoders(40)),

		// Turn180Degrees(new TurnWithEncoders(180)),
		// Turn90Degrees(new TurnWithEncoders(90)),
		// TurnWithEncoders180(new TurnWithEncoders(-180)),
		// TurnWithEncoders90(new TurnWithEncoders(-90)),
		// TurnWithEncoders45(new TurnWithEncoders(-45)),

		private final Supplier<Command> commandSupplier;

		AutoModes(Supplier<Command> commandSupplier) {
			this.commandSupplier = commandSupplier;
		}

		public Command getCommand() {
			return commandSupplier.get();
		}

		public void start() {
			getCommand().start();
		}
	}

	private final SendableChooser<AutoModes> autoChooser = new SendableChooser<>();

	public static final AutoModes defaultAuto = AutoModes.GrabBallsKeylineThenShoot;

	public AutoChooser() {
		autoChooser.addDefault(defaultAuto.ordinal() + ": " + defaultAuto.name(), defaultAuto);
		AutoModes[] autoModes = AutoModes.values();
		for (int i = 0; i < autoModes.length; i++) {
			autoChooser.addObject(autoModes[i].ordinal() + ": " + autoModes[i].name(), autoModes[i]);
		}
		SmartDashboard.putData("AutonomousChooser", autoChooser);
	}

	public AutoModes getSelected() {
		int value = 0;
		if (chosenauto != null)
			value = chosenauto.getValue().intValue();
		if (value < 0 || value >= AutoModes.values().length)
			value = 0;
		return AutoModes.values()[value];
		// return autoChooser.getSelected().getSelected();
	}

	private SmartDashboardItem<Double> chosenauto;

	@Override
	public void addToSmartDashboard(MySmartDashboard dashboard) {
		chosenauto = dashboard
				.addItem(SmartDashboardItem.newDoubleReciever("Auto Number", (double) defaultAuto.ordinal()));
	}

}