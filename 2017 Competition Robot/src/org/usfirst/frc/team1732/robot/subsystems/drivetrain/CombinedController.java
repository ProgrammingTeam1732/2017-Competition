package org.usfirst.frc.team1732.robot.subsystems.drivetrain;

import java.util.function.DoubleSupplier;

// class to make using the ControllerUnit class easier

public class CombinedController {

    public final String name;
    public final ControllerUnit left;
    public final ControllerUnit right;

    public CombinedController(String name, DoubleSupplier left, DoubleSupplier right, PIDData valuesLeft,
	    PIDData valuesRight) {
	this.name = name;
	this.left = new ControllerUnit(name + " Left", left, valuesLeft);
	this.right = new ControllerUnit(name + " Right", right, valuesRight);
    }

    public CombinedController(String name, DoubleSupplier left, DoubleSupplier right, PIDData values) {
	this(name, left, right, values, values);
    }

    public void setPIDValues(PIDData valuesLeft, PIDData valuesRight) {
	left.setPIDValues(valuesLeft);
	right.setPIDValues(valuesRight);
    }

    public void setPIDValues(PIDData values) {
	left.setPIDValues(values);
	right.setPIDValues(values);
    }

    public void resetPIDValues() {
	left.resetToDefaultValues();
	right.resetToDefaultValues();
    }

    public boolean areBothErrorNegative() {
	return left.isErrorNegative() && right.isErrorNegative();
    }

    public void clearIntegral() {
	left.clearIntegral();
	right.clearIntegral();
    }

    public void setSetpoint(double setpoint) {
	setSetpoint(setpoint, setpoint);
    }

    public void setSetpoint(double leftSetpoint, double rightSetpoint) {
	left.setSetpoint(leftSetpoint);
	right.setSetpoint(rightSetpoint);
    }

    public void adjustSetpoint(double leftSetpoint, double rightSetpoint) {
	left.adjustSetpoint(leftSetpoint);
	right.adjustSetpoint(rightSetpoint);
    }

    public void disable() {
	left.disableController();
	right.disableController();
    }

    public void enable() {
	left.enableController();
	right.enableController();
    }

    public boolean areBothInsideIZone() {
	return left.insideIZone() && right.insideIZone();
    }

    public boolean areBothOnTarget() {
	return left.onTarget() && right.onTarget();
    }

    public boolean areBothOnTarget(double offset) {
	return left.onTarget(offset) && right.onTarget();
    }
}