package org.usfirst.frc.team1732.robot.vision;

public class Rectangle {

	public final int x, y, width, height, signature;

	public Rectangle(int signature, int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.signature = signature;
	}

	public int getRightX() {
		return x + width;
	}

	public int getBottomY() {
		return y + height;
	}

	public int getCenterY() {
		return (int) Math.round(y + height / 2.0);
	}

	public int getCenterX() {
		return (int) Math.round(x + width / 2.0);
	}

	@Override
	public String toString() {
		return String.format("sig: %d x: %d y: %d width: %d height: %d", signature, x, y, width, height);
	}

	public int getArea() {
		return width * height;
	}
}