package org.usfirst.frc.team1732.robot.vision;

public class CheeseWheelTarget extends VisionTarget {

    public CheeseWheelTarget(Rectangle singleRectangle) {
	super(singleRectangle);
    }

    @Override
    protected double calculateScore() {
	return 0;
    }

}
