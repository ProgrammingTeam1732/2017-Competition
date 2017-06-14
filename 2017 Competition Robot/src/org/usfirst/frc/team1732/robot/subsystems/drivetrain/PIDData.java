package org.usfirst.frc.team1732.robot.subsystems.drivetrain;

public class PIDData {

    public final double P;
    public final double I;
    public final double D;
    public final double F;
    public final double iZone;
    public final double deadband;
    public final double maxOut;
    public final double minOut;

    public PIDData(double p, double i, double d, double iZone, double deadband, double maxOut, double minOut) {
	this(p, i, d, 0, iZone, deadband, maxOut, minOut);
    }

    public PIDData(double p, double i, double d, double deadband, double maxOut, double minOut) {
	this(p, i, d, 0, 0, deadband, maxOut, minOut);
    }

    public PIDData(double p, double i, double d, double f, double iZone, double deadband, double maxOut,
	    double minOut) {
	P = p;
	I = i;
	D = d;
	F = f;
	this.iZone = iZone;
	this.deadband = deadband;
	this.maxOut = maxOut;
	this.minOut = minOut;
    }

    @Override
    public String toString() {
	return String.format("P:%f I:%f D:%f F:%f iZone:%f Deadband:%f Max:%f Min:%f", P, I, D, F, iZone, deadband,
		maxOut, minOut);
    }

    // return new PIDData(P, I, D, F, iZone, deadband, maxOut, minOut);

    public PIDData changePID(double p, double i, double d) {
	return new PIDData(p, i, d, F, iZone, deadband, maxOut, minOut);
    }

    public PIDData changeF(double f) {
	return new PIDData(P, I, D, f, iZone, deadband, maxOut, minOut);
    }

    public PIDData changeDeadband(double deadband) {
	return new PIDData(P, I, D, F, iZone, deadband, maxOut, minOut);
    }

    public PIDData changeiZone(double iZone) {
	return new PIDData(P, I, D, F, iZone, deadband, maxOut, minOut);
    }
}