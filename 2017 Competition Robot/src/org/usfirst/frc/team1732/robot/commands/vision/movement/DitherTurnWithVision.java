package org.usfirst.frc.team1732.robot.commands.vision.movement;

import static org.usfirst.frc.team1732.robot.Robot.driveTrain;
import static org.usfirst.frc.team1732.robot.Robot.visionMain;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Use this command to control turning the robot with the camera.
 */
public class DitherTurnWithVision extends Command {

    private final double angleSetpoint;
    private long startTime;
    private long absStartTime;
    // were 50 and 15
    private static final double ditherInterval = 85;
    private static final double ditherLength = 25;
    private static final double motorOutput = 0.4;
    private static final double minimum = 0.178;
    private static final double maximum = 1;

    public DitherTurnWithVision(double angle) {
	requires(driveTrain);
	requires(Robot.pixyCamera);
	angleSetpoint = angle;
	setTimeout(5);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
	System.out.println("Start dither vision turn");
	Robot.pixyCamera.turnOnLights();
	visionMain.setGearSetpoint(angleSetpoint);
	startTime = System.currentTimeMillis();
	absStartTime = startTime;
    }

    private boolean foundOnce = false;

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
	visionMain.run();
	// double angle = visionMain.getAngleToGearPeg();
	if (visionMain.canSeeGearPeg()) {
	    foundOnce = true;
	    double gearPIDOutput = visionMain.getGearPIDOutput();
	    // double output = 0;
	    if (System.currentTimeMillis() - startTime > ditherInterval) {
		if (System.currentTimeMillis() - startTime - ditherInterval < ditherLength) {// &&
											     // Math.abs(output)
											     // <
											     // 0.35

		    gearPIDOutput = Math.copySign(motorOutput, gearPIDOutput);
		} else {
		    startTime = System.currentTimeMillis();
		}
	    } else {
		gearPIDOutput = 0;
	    }
	    // driveTrain.driveRawAbsoluteLimit(-gearPIDOutput, gearPIDOutput,
	    // minimum, maximum);
	    SmartDashboard.putString("Gear Drive", driveTrain.driveRawAbsLimit(gearPIDOutput, -gearPIDOutput, .178, 1));
	}
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
	return ((foundOnce && visionMain.isGearPIDOnTarget()) && System.currentTimeMillis() - absStartTime > 500)
		|| isTimedOut();
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
	System.out.println("End dither vision turn");
	driveTrain.driveRaw(0, 0);
	Robot.pixyCamera.turnOffLights();
    }
}
