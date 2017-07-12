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
import org.usfirst.frc.team1732.robot.autocommands.scoregearandballs.scoremiddlegearthenballs.ScoreMiddleGearThenBallsEncoders;
import org.usfirst.frc.team1732.robot.autocommands.shoot.besideboilerandshoot.StartBesideBoilerAndShoot;
import org.usfirst.frc.team1732.robot.autocommands.shoot.startonwallandshoot.StartOnWallAndShoot;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardGroup;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoChooser implements SmartDashboardGroup {

    /**
     * Class to help with choosing autos Just add autos to here, if there are
     * separate red and blue add the red first then the blue
     */

    public static enum AutoModes {
	KeylineHopperShootNoWings(KeylineHopperShoot::new), // 0
	KeylineHopperShootWithWings(KeylineHopperShootWings::new), // 1
	GrabBallsForwardThenShoot(StraightHopperShoot::new), // 2
	GrabBallsForwardThenShootArc(StraightHopperShootArc::new), // 3
	StartBesideBoilerThenShoot(StartBesideBoilerAndShoot::new), // 4
	StartOnWallThenShoot(StartOnWallAndShoot::new), // 5

	MiddleGearEncoders(ScoreMiddleGearEncoders::new), // 6
	RightGear(ScoreSideGearWithTurningVisionRight::new), // 7
	LeftGear(ScoreSideGearWithTurningVisionLeft::new), // 8
	MiddleGearThenShootBallsEncoders(ScoreMiddleGearThenBallsEncoders::new), // 9
	GrabBallsKeylineThenShootNoWings(KeylineHopperShoot::new), // 10
	GrabBallsKeylineThenShootWings(KeylineHopperShootWings::new), // 11
	TwoGearAutoLeftSide(TwoGearAutoLeft::new), // 12
	TwoGearAutoRightSide(TwoGearAutoRight::new); // 13

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

    // public AutoModes getSelected() {
    // int value = 0;
    // if (chosenauto != null)
    // value = chosenauto.getValue().intValue();
    // if (value < 0 || value >= AutoModes.values().length)
    // value = 0;
    // return AutoModes.values()[value];
    // // return autoChooser.getSelected().getSelected();
    // }
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