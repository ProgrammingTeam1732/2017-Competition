package org.usfirst.frc.team1732.robot.triggers;

import org.usfirst.frc.team1732.robot.commands.vision.lights.FlashLEDs;

public class Triggers {

    private final FlashLEDsTrigger flashLEDsTrigger = new FlashLEDsTrigger();

    public Triggers() {
	flashLEDsTrigger.whenActive(new FlashLEDs());
    }
}