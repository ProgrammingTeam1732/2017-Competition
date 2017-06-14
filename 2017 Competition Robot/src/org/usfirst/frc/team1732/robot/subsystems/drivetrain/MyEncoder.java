package org.usfirst.frc.team1732.robot.subsystems.drivetrain;

public abstract class MyEncoder {

    private double totalDistance = 0;

    public abstract double getDistance();

    public abstract double getRate();

    public void reset() {
	totalDistance += getDistance();
	resetHelper();
    }

    protected abstract void resetHelper();

    public double getTotalDistance() {
	return getDistance() + totalDistance;
    }

    public void clearTotalDistance() {
	totalDistance = 0;
    }

}