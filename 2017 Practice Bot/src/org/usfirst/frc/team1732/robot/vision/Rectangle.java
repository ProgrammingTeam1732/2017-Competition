package org.usfirst.frc.team1732.robot.vision;

public class Rectangle extends java.awt.Rectangle {

	// public final int x, y, width, height, signature;
	public final int signature;

	public Rectangle(int signature, int x, int y, int width, int height) {
		this.signature = signature;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getRightX() {
		return x + width;
	}

	public int getBottomY() {
		return y + height;
	}

	public double getCenterX() {
		return x + width / 2;
	}

	public double getCenterY() {
		return y + width / 2;
	}

	@Override
	public String toString() {
		return String.format("sig: %d x: %d y: %d width: %d height: %d", signature, x, y, width, height);
	}

	public int getArea() {
		return width * height;
	}
}