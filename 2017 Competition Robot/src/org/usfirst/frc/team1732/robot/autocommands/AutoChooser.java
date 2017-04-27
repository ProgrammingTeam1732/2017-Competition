package org.usfirst.frc.team1732.robot.autocommands;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.keylinehoppershoot.KeylineHopperShoot;
import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.keylinehoppershoot.KeylineHopperShootWings;
import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.straighthoppershoot.StraightHopperShoot;
import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.straighthoppershoot.StraightHopperShootArc;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoremiddlegear.ScoreMiddleGearEncoders;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.ScoreSideGearWithTurningVisionLeft;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.ScoreSideGearWithTurningVisionRight;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.twogearauto.TwoGearAutoLeft;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.twogearauto.TwoGearAutoRight;
import org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoremiddlegearthenballs.ScoreMiddleGearThenBallsEncoders;
import org.usfirst.frc.team1732.robot.autocommands.shoot.besideboilerandshoot.StartBesideBoilerAndShoot;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveEncodersSimpleRamp;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.DriveUntilEncoders;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.TurnWithEncodersSimpleRamp;
import org.usfirst.frc.team1732.robot.commands.vision.movement.DitherTurnWithVision;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardGroup;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooser implements SmartDashboardGroup {

    /**
     * Class to help with choosing autos Just add autos to here, if there are
     * separate red and blue add the red first then the blue
     */

    public static enum AutoModes {
	MiddleGearEncoders(ScoreMiddleGearEncoders::new), // 0
	RightGear(ScoreSideGearWithTurningVisionRight::new), // 1
	LeftGear(ScoreSideGearWithTurningVisionLeft::new), // 2
	MiddleGearThenShootBallsEncoders(ScoreMiddleGearThenBallsEncoders::new), // 3
	GrabBallsForwardThenShoot(StraightHopperShoot::new), // 4
	GrabBallsForwardThenShootArc(StraightHopperShootArc::new), // 5
	GrabBallsKeylineThenShootNoWings(KeylineHopperShoot::new), // 6
	GrabBallsKeylineThenShootWings(KeylineHopperShootWings::new), // 7
	TwoGearAutoLeftSide(TwoGearAutoLeft::new), // 8
	TwoGearAutoRightSide(TwoGearAutoRight::new), // 9
	StartBesideBoilerThenShoot(StartBesideBoilerAndShoot::new), // 10

	DitherTurnTest(() -> new DitherTurnWithVision(0)),
	Turn90DegreesRamp(() -> new TurnWithEncodersSimpleRamp(90)),
	ArcTurn(() -> new CommandGroup() {
	    {
		double driveTowardHopperDistance = 23;
		double driveTowardHopperSpeed = 1;
		boolean stop = false;
		addSequential(new DriveUntilEncoders(driveTowardHopperDistance, driveTowardHopperSpeed,
			driveTowardHopperSpeed, stop));
		boolean isRed = Robot.isRedAlliance.getValue();
		DoubleSupplier leftArc;
		DoubleSupplier rightArc;
		double innerRadius = 32;
		double outerRadius = innerRadius + DriveTrain.EFFECTIVE_TURNING_CIRCUMFERENCE;
		double innerCircleV1 = Math.PI * 2 * innerRadius / 4; // C =
								      // 2pi*r/4
		double outerCircleV1 = Math.PI * 2 * outerRadius / 4; // C =
								      // 2pi*r/4
		double ratio = innerCircleV1 / outerCircleV1;
		double innerCircle = innerCircleV1 - 4;
		double outerCircle = innerCircle / ratio;
		// double A = a;
		// double B = A / ratio;
		if (isRed) {
		    leftArc = () -> outerCircle;
		    rightArc = () -> innerCircle;
		} else {
		    leftArc = () -> innerCircle;
		    rightArc = () -> outerCircle;
		}

		addSequential(new DriveEncodersSimpleRamp(leftArc, rightArc));
	    }
	});

	// MiddleGear(ScoreMiddleGearVision::new),

	// RightGearEncoders(ScoreSideGearRightEncoders::new),
	// LeftGearEncoders(ScoreSideGearLeftEncoders::new),

	// LeftGearThenShootBalls(ScoreSideGearThenBallsVision::new),
	// LeftGearThenShootBallsEncoders(ScoreSideGearThenBallsEncoders::new),
	// MiddleGearThenShootBalls(ScoreMiddleGearThenBallsVision::new),
	// ShootBallsThenSideGear(ScoreBallsThenSideGearVision::new),
	// ShootBallsThenSideGearEncoders(ScoreBallsThenSideGearEncoders::new),

	// SideTwoGearAutoLeft(SideTwoGearAutoLeft::new),
	// SideTwoGearAutoRight(SideTwoGearAutoRight::new),
	// MiddleSideTwoGear(TwoGearMiddleThenSideAuto::new),

	// StartOnWallThenShoot(StartOnWallAndShoot::new),

	// testing
	// DriveEncodersBrake(() -> new DriveEncodersWithBraking(110, 20)),
	// TurnWithEncodersWithBraking90(() -> new
	// TurnWithEncodersWithBraking(90)),
	// TurnWithEncodersWithBraking180(() -> new
	// TurnWithEncodersWithBraking(180)),
	// TurnWithEncodersWithBraking45(() -> new
	// TurnWithEncodersWithBraking(45)),
	//
	// DriveTime(() -> new DriveTime(2, 0.5)),
	// DriveTimeBackwards(() -> new DriveTime(2, -0.5)),
	// ResetEncoders(ClearTotalDistance::new),
	// TestShooterShort(TestShootShort::new),
	// TestShooterLong(TestShootLong::new),
	//
	// TestVisionTurning(() -> new DitherTurnWithVision(0)),
	// // DriveEncodersFar(new DriveEncoders(97.5)),
	// DriveEncodersTest(() -> new DriveEncoders(100)),
	// DriveEncodersReverse(() -> new DriveEncoders(-100)),

	// DriveEncodersArc(()->new DriveUntilEncoders(20 * Math.PI, 33 *
	// Math.PI, )),
	// DriveEncodersSimpleRamping(() -> new DriveEncodersSimpleRampBase(()
	// -> 20 * Math.PI, () -> 33 * Math.PI)),
	// // Turn180Degrees(new TurnWithEncoders(180)),
	// TurnNegative90DegreesRamp(() -> new TurnWithEncodersSimpleRamp(-90)),
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

    public static final AutoModes defaultAuto = AutoModes.GrabBallsKeylineThenShootNoWings;

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