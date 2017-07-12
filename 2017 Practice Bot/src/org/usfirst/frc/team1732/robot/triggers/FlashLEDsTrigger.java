package org.usfirst.frc.team1732.robot.triggers;

import org.usfirst.frc.team1732.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.buttons.Trigger;

public class FlashLEDsTrigger extends Trigger {

    @Override
    public boolean get() {
	return Robot.gearIntake.gearIsIn() && DriverStation.getInstance().isOperatorControl();
    }
}
