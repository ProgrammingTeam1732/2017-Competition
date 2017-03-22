package org.usfirst.frc.team1732.robot.subsystems;

import org.usfirst.frc.team1732.robot.RobotMap;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

public class PixyCamera extends Subsystem {

	private final Relay lightRelay = new Relay(RobotMap.RELAY_CHANNEL);

	public PixyCamera() {
		lightRelay.setDirection(Direction.kBoth);
	}

	@Override
	public void initDefaultCommand() {}

	public void turnOnLights() {
		lightRelay.set(Value.kForward);
	}

	public void turnOffLights() {
		lightRelay.set(Value.kOff);
	}
}
