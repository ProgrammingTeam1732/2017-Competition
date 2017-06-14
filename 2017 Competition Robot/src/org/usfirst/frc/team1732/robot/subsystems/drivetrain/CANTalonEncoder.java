package org.usfirst.frc.team1732.robot.subsystems.drivetrain;

import com.ctre.CANTalon;
import com.ctre.CANTalon.StatusFrameRate;

public class CANTalonEncoder extends MyEncoder {

    private CANTalon talon;
    private final double distancePerRev;

    public CANTalonEncoder(CANTalon talon, int codesPerRev, double distancePerRev, boolean reverse) {
	this.talon = talon;
	talon.reverseSensor(reverse);
	talon.configEncoderCodesPerRev(codesPerRev);
	talon.setStatusFrameRateMs(StatusFrameRate.QuadEncoder, 20);
	this.distancePerRev = distancePerRev;
    }

    @Override
    public double getDistance() {
	return talon.getPosition() * distancePerRev;
    }

    @Override
    public double getRate() {
	return talon.getSpeed() * distancePerRev;
    }

    @Override
    protected void resetHelper() {
	talon.setPosition(0);
    }

}