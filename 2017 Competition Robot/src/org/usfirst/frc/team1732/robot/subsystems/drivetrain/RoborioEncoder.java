package org.usfirst.frc.team1732.robot.subsystems.drivetrain;

import edu.wpi.first.wpilibj.Encoder;

// more or less a wrapper class to hide some of the Encoder methods
public class RoborioEncoder extends MyEncoder {

    private final Encoder encoder;

    public RoborioEncoder(int channelA, int channelB, double distancePerPulse, boolean reverseDirection) {
	this.encoder = new Encoder(channelA, channelB);
	encoder.setDistancePerPulse(distancePerPulse);
	encoder.setReverseDirection(reverseDirection);
	encoder.setSamplesToAverage(3);
    }

    @Override
    public double getDistance() {
	return encoder.getDistance();
    }

    @Override
    public double getRate() {
	return encoder.getRate();
    }

    @Override
    protected void resetHelper() {
	encoder.reset();
    }

}