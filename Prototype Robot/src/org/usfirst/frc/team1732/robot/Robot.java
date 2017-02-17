
package org.usfirst.frc.team1732.robot;

import org.usfirst.frc.team1732.robot.autocommands.Score10BallsAndGearLeft;
import org.usfirst.frc.team1732.robot.autocommands.Score10BallsAndGearRight;
import org.usfirst.frc.team1732.robot.autocommands.ScoreGearAnd10BallsLeft;
import org.usfirst.frc.team1732.robot.autocommands.ScoreGearAnd10BallsRight;
import org.usfirst.frc.team1732.robot.autocommands.ScoreSideGearLeft;
import org.usfirst.frc.team1732.robot.autocommands.ScoreSideGearRight;
import org.usfirst.frc.team1732.robot.autocommands.VisionPlaceGear;
import org.usfirst.frc.team1732.robot.commands.drivetrain.TurnWithGyro;
import org.usfirst.frc.team1732.robot.commands.vision.DriveWithVision;
import org.usfirst.frc.team1732.robot.smartdashboard.MySmartDashboard;
import org.usfirst.frc.team1732.robot.smartdashboard.SmartDashboardItem;
import org.usfirst.frc.team1732.robot.subsystems.Arm;
import org.usfirst.frc.team1732.robot.subsystems.BallIntake;
import org.usfirst.frc.team1732.robot.subsystems.Climber;
import org.usfirst.frc.team1732.robot.subsystems.DriveTrain;
import org.usfirst.frc.team1732.robot.subsystems.Feeder;
import org.usfirst.frc.team1732.robot.subsystems.Flywheel;
import org.usfirst.frc.team1732.robot.subsystems.GearIntake;
import org.usfirst.frc.team1732.robot.subsystems.unused.OtherShooter;
import org.usfirst.frc.team1732.robot.vision.VisionMain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI			oi;
	public static DriveTrain	driveTrain;
	public static BallIntake	ballIntake;
	public static Climber		climber;
	public static Feeder		feeder;
	public static Flywheel		flywheel;
	public static GearIntake	gearIntake;
	public static OtherShooter	otherShooter;
	public static Arm 			arm;

	public static VisionMain					visionMain;
	public static MySmartDashboard				dashboard;
	public static SmartDashboardItem<Double>	distanceSetpointReciever;
	private Command autoCommand;
	private SendableChooser<Command> autoChooser;
	private static boolean isRedAlliance;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		driveTrain = new DriveTrain();
		flywheel = new Flywheel();
		gearIntake = new GearIntake();
		oi = new OI();
		visionMain = new VisionMain();
		isRedAlliance = DriverStation.getInstance().getAlliance().equals(Alliance.Red);//SmartDashboard.getBoolean("IsRedAlliance?", false);
		// Smartdashboard code
		dashboard = new MySmartDashboard();
		// Add items to smartdashboard
		driveTrain.addToSmartDashboard(dashboard);
		visionMain.addToSmartDashboard(dashboard);
		dashboard.addItem(SmartDashboardItem.newDoubleReciever(	"Turning P Slope", DriveWithVision.slope,
																DriveWithVision::setSlope));
		dashboard.addItem(SmartDashboardItem.newDoubleReciever(	"Turning P Lower", DriveWithVision.lower,
																DriveWithVision::setLower));
		dashboard.addItem(SmartDashboardItem.newDoubleReciever(	"Turning P Upper", DriveWithVision.upper,
																DriveWithVision::setUpper));
		dashboard.addItem(SmartDashboardItem.newDoubleReciever(	"Turning P Middle", DriveWithVision.middle,
																DriveWithVision::setMiddle));
		// Add auto chooser
		autoChooser = new SendableChooser<>();
		autoChooser.addDefault("Vision Place Gear", new VisionPlaceGear(-40));
		autoChooser.addObject("Score Side Gear Right", new ScoreSideGearRight());
		autoChooser.addObject("Score Side Gear Left", new ScoreSideGearLeft());
		autoChooser.addObject("Score Gear and 10 Balls", isRedAlliance ? new ScoreGearAnd10BallsRight() : new ScoreGearAnd10BallsLeft());
		autoChooser.addObject("Score 10 Balls and Gear", isRedAlliance ? new Score10BallsAndGearRight() : new Score10BallsAndGearLeft());
		SmartDashboard.putData("Autonomous Chooser", autoChooser);
		//SmartDashboard.putBoolean("IsRedAlliance?", false);
		autoCommand = new VisionPlaceGear(-40);
		SmartDashboard.putString("Current Command", autoCommand.getName());
		SmartDashboard.putBoolean("IsRedAlliance", isRedAlliance);
		// Initialize smartdashboard
		dashboard.init();
	}

	@Override
	public void robotPeriodic() {
		visionMain.run();
		dashboard.run();
		SmartDashboard.putString("Current Command", autoChooser.getSelected().getName());
		isRedAlliance = DriverStation.getInstance().getAlliance().equals(Alliance.Red);
		SmartDashboard.putBoolean("IsRedAlliance", isRedAlliance);
		//System.out.println(isRedAlliance);
	}
	
	public static boolean isRedAlliance(){
		return isRedAlliance;
	}
	@Override
	public void disabledInit() {}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		Scheduler.getInstance().removeAll(); // Cancels commands

		// Can use a SmartDashboard chooser to select auto command
		isRedAlliance = DriverStation.getInstance().getAlliance().equals(Alliance.Red);//SmartDashboard.getBoolean("IsRedAlliance?", false);
		autoCommand = (Command) autoChooser.getSelected();
		autoCommand.start();
		Scheduler.getInstance().add(autoCommand);
	} 

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		Scheduler.getInstance().removeAll(); // Cancels commands
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testInit() {}

	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}