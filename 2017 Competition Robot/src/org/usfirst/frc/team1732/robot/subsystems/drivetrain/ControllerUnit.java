package org.usfirst.frc.team1732.robot.subsystems.drivetrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

// more or less a wrapper class to hide some of the PIDController methods

public class ControllerUnit {

    public final String name;
    private final DoubleSupplier sensor;
    private final PIDSource pidSource;
    private final PIDController controller;

    public final PIDData defaultPIDValues;
    private PIDData currentPIDValues;

    private boolean justEnteredIZone = false;

    public ControllerUnit(String n, DoubleSupplier sen, PIDData defaultPID) {
	name = n;
	sensor = sen;
	pidSource = makePIDSource(sen);

	defaultPIDValues = currentPIDValues = defaultPID;

	controller = new PIDController(defaultPIDValues.P, defaultPIDValues.I, defaultPIDValues.D, pidSource,
		ControllerUnit::voidMethod, 20);

	setPIDValues(currentPIDValues);

	controller.setContinuous(false);
	controller.setSetpoint(0);
	controller.enable();
    }

    public void setPIDValues(PIDData values) {
	currentPIDValues = values;
	controller.setAbsoluteTolerance(values.deadband);
	controller.setOutputRange(values.minOut, values.maxOut);

	if (insideIZone()) {
	    clearIntegral();
	    controller.setPID(values.P, values.I, values.D);
	} else {
	    controller.setPID(values.P, 0, values.D);
	}
    }

    public PIDData getPID() {
	return currentPIDValues;
    }

    public void resetToDefaultValues() {
	setPIDValues(defaultPIDValues);
    }

    public boolean isErrorNegative() {
	return getError() < 0;
    }

    public double getError() {
	return controller.getAvgError();
    }

    public void clearIntegral() {
	controller.reset();
	controller.enable();
    }

    public void setSetpoint(double setpoint) {
	clearIntegral();
	controller.setSetpoint(setpoint);
    }

    public void adjustSetpoint(double setpoint) {
	controller.setSetpoint(setpoint);
    }

    public double getSetpoint() {
	return controller.getSetpoint();
    }

    public double getOutput() {
	if (insideIZone() && !justEnteredIZone) {
	    // if it just entered the IZone
	    clearIntegral();
	    controller.setPID(currentPIDValues.P, currentPIDValues.I, currentPIDValues.D);
	    justEnteredIZone = true;
	}
	if (!insideIZone() && justEnteredIZone) {
	    controller.setPID(currentPIDValues.P, 0, currentPIDValues.D);
	    justEnteredIZone = false;
	}
	return controller.get() + currentPIDValues.D * getSetpoint();
    }

    public void disableController() {
	controller.reset();
    }

    public void enableController() {
	controller.enable();
    }

    public boolean insideIZone() {
	return Math.abs(getError()) < currentPIDValues.iZone;
    }

    public boolean onTarget() {
	return controller.onTarget();
    }

    public boolean onTarget(double offset) {
	return Math.abs(getError() - offset) < currentPIDValues.deadband;
    }

    // this method exists because changing the encoder sourcetype will change it
    // for all the PID objects, and also making a copy of the encoder is not
    // possible
    private PIDSource makePIDSource(DoubleSupplier e) {
	PIDSource pidSource = new PIDSource() {
	    @Override
	    public void setPIDSourceType(PIDSourceType pidSource) {
	    }

	    @Override
	    public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	    }

	    @Override
	    public double pidGet() {
		return e.getAsDouble();
	    }
	};

	return pidSource;

    }

    private static void voidMethod(double d) {
    }

    @Override
    public ControllerUnit clone() {
	return new ControllerUnit(name, sensor, defaultPIDValues);
    }
}