package org.usfirst.frc.team1732.robot.autocommands;

import java.util.function.Supplier;

import org.usfirst.frc.team1732.robot.Robot;
import org.usfirst.frc.team1732.robot.RobotMap;
import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.keylinehoppershoot.KeylineHopperShoot;
import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.keylinehoppershoot.KeylineHopperShootWings;
import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.straighthoppershoot.StraightHopperShoot;
import org.usfirst.frc.team1732.robot.autocommands.grabballsthenshoot.straighthoppershoot.StraightHopperShootArc;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoremiddlegear.ScoreMiddleGearEncoders;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.ScoreSideGearWithTurningVisionLeft;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.scoresidegear.ScoreSideGearWithTurningVisionRight;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.twogearauto.TwoGearAutoLeft;
import org.usfirst.frc.team1732.robot.autocommands.scoregear.twogearauto.TwoGearAutoRight;
import org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoremiddlegearthenballs.ScoreMiddleGearThenBallsHitWall;
import org.usfirst.frc.team1732.robot.autocommands.shoot.besideboilerandshoot.StartBesideBoilerAndShoot;
import org.usfirst.frc.team1732.robot.commands.ballsystem.flywheel.EnableFlywheel;
import org.usfirst.frc.team1732.robot.commands.ballsystem.shooting.ShootTime;
import org.usfirst.frc.team1732.robot.commands.ballsystem.shooting.ShuffleBallsWithWait;
import org.usfirst.frc.team1732.robot.commands.drivetrain.DriveTime;
import org.usfirst.frc.team1732.robot.commands.drivetrain.dualpid.VelocityTest;
import org.usfirst.frc.team1732.robot.commands.drivetrain.encoder.ClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.drivetrain.talon.MagicMotionTest;
import org.usfirst.frc.team1732.robot.commands.helpercommands.Wait;
import org.usfirst.frc.team1732.robot.commands.test.TestClearTotalDistance;
import org.usfirst.frc.team1732.robot.commands.vision.movement.DitherTurnWithVision;
import org.usfirst.frc.team1732.robot.commands.vision.movement.TurnWithEncodersUntilCheeseWheel;
import org.usfirst.frc.team1732.robot.commands.vision.movement.TurnWithEncodersUntilGearPeg;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardGroup;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;

import edu.wpi.first.wpilibj.Joystick;
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
	MiddleGearThenShootBallsEncoders(() -> new ScoreMiddleGearThenBallsHitWall()), // ScoreMiddleGearThenBallsEncoders::new),
										       // 3
	GrabBallsForwardThenShoot(StraightHopperShoot::new), // 4
	GrabBallsForwardThenShootArc(StraightHopperShootArc::new), // 5
	GrabBallsKeylineThenShootNoWings(KeylineHopperShoot::new), // 6
	GrabBallsKeylineThenShootWings(KeylineHopperShootWings::new), // 7
	TwoGearAutoLeftSide(TwoGearAutoLeft::new), // 8
	TwoGearAutoRightSide(TwoGearAutoRight::new), // 9
	StartBesideBoilerThenShoot(StartBesideBoilerAndShoot::new), // 10

	// testing
	ClearDistanceTest(() -> new TestClearTotalDistance()),
	TestVelocityControl(() -> new VelocityTest(50)), // 11
	TestMotionMagic(() -> new MagicMotionTest(60)), // 12
	DriveFullSpeedForward(() -> new DriveTime(5, 1)), // 13
	DriveFullSpeedBackward(() -> new DriveTime(5, -1)), // 14
	TurnUntilCheeseWheel(() -> new TurnWithEncodersUntilCheeseWheel(-120)), // 15
	TurnUntilGearPeg(() -> new TurnWithEncodersUntilGearPeg(120)), // 16
	WallMiddleGearAndShoot(() -> new ScoreMiddleGearThenBallsHitWall()), // 17
	TestForwardDrive(() -> new DriveTime(5, 0.5)),
	TestBackwardDrive(() -> new DriveTime(5, -0.5)),
	ClearEncoders(() -> new ClearTotalDistance()),
	DitherTurnTest(() -> new DitherTurnWithVision(0)),
	SlowShuffle(() -> new CommandGroup() {
	    {
		addSequential(new EnableFlywheel());
		double shootTime = 5;
		addSequential(new Wait(3));
		addParallel(new ShuffleBallsWithWait(), shootTime);
		addSequential(new ShootTime(shootTime));
	    }
	}),
	FashShuffle(() -> new CommandGroup() {
	    {
		addSequential(new EnableFlywheel());
		double shootTime = 5;
		addSequential(new Wait(3));
		double scalar = 2;
		double shuffleCount = 11 * scalar;
		double shuffleTime = 1.36 / scalar;
		addParallel(new ShuffleBallsWithWait((int) shuffleCount, shuffleTime), shootTime);
		addSequential(new ShootTime(shootTime));
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

    public static final AutoModes defaultAuto = AutoModes.MiddleGearThenShootBallsEncoders;

    public AutoChooser() {
	autoChooser.addDefault(defaultAuto.ordinal() + ": " + defaultAuto.name(), defaultAuto);
	AutoModes[] autoModes = AutoModes.values();
	for (int i = 0; i < autoModes.length; i++) {
	    autoChooser.addObject(autoModes[i].ordinal() + ": " + autoModes[i].name(), autoModes[i]);
	}
	SmartDashboard.putData("AutonomousChooser", autoChooser);
    }

    private Joystick buttons = new Joystick(RobotMap.DIAL_USB);

    public AutoModes getSelected() {
	AutoModes selected;
	if (Robot.oi.overridePressed()) {
	    int value = 0;
	    if (chosenauto != null)
		value = chosenauto.getValue().intValue();
	    if (value < 0 || value >= AutoModes.values().length)
		value = 0;
	    selected = AutoModes.values()[value];
	} else {
	    int index = 1;
	    for (int i = 2; i <= 10; i++) {
		if (buttons.getRawButton(i)) {
		    // System.out.println(i);
		    index = i;
		    break;
		}
	    }
	    selected = AutoModes.values()[index - 1];
	}
	// System.out.println(selected);
	return selected;
    }

    private SmartDashboardItem<Double> chosenauto;

    @Override
    public void addToSmartDashboard(MySmartDashboard dashboard) {
	chosenauto = dashboard
		.addItem(SmartDashboardItem.newDoubleReciever("Auto Number", (double) defaultAuto.ordinal()));
    }

}