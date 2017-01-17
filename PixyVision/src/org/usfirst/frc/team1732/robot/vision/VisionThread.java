package org.usfirst.frc.team1732.robot.vision;

public class VisionThread implements Runnable {

	public GearTarget gearTarget;
	public BoilerTarget boilerTarget;
	public Arduino arduino = new Arduino();
	private String total = "";
	private boolean found = false;
	@Override
	public void run() {
		Rectangle[] rectangles = new Rectangle[0];
		while (!Thread.interrupted()) {
			String s = arduino.getData();
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (found && c != '^')
					total += c;
				if (c == '$')
					found = true;
				else if (c == '^') {
					found = false;
					if (total != "") {
						String[] rects = total.split("\n");
						rectangles = new Rectangle[rects.length];
						for (int j = 0; j < rects.length; j++) {
							String[] data = rects[j].split(" ");
							rectangles[j] = new Rectangle(Integer.parseInt(data[1]), Integer.parseInt(data[3]),
									Integer.parseInt(data[5]), Integer.parseInt(data[7]), Integer.parseInt(data[9]));
							System.out.println(j + ": " + rectangles[j]);
						}
						gearTarget = GearTarget.getBestVisionTarget(rectangles);
						System.out.println(gearTarget.boundingBox);
						System.out.println();
					}
					total = "";
				}
				// change this later
				//rectangles = new Rectangle[0];
				// System.out.println(s);
			}
			//gearTarget = GearTarget.getBestVisionTarget(rectangles);

			/*if (serialLine != "") {
				String[] rects = serialLine.split("\n");
				Rectangle[] total = new Rectangle[rects.length];
				for (int i = 0; i < rects.length; i++) {
					String[] data = rects[i].split(" ");
					total[i] = new Rectangle(Integer.parseInt(data[1]), Integer.parseInt(data[3]),
							Integer.parseInt(data[5]), Integer.parseInt(data[7]), Integer.parseInt(data[9]));
				}
				gearTarget = GearTarget.getBestVisionTarget(total);
			}*/
			// int length = 0;
			// Rectangle[] rectangles = new Rectangle[length];
			// calculate best gear and boiler target
			// gearTarget = GearTarget.getBestVisionTarget(rectangles);
		}
	}

	public double getInchesToGearPeg() {
		if (gearTarget == null)
			return -1;
		return gearTarget.getHorizontalDistance(GearTarget.GEAR_TARGET_WIDTH_INCHES, HORIZONTAL_FIELD_OF_VIEW,
				IMAGE_WIDTH);
	}

	public double getAngleToGearPeg() {
		return gearTarget.getHorizontalAngle(HORIZONTAL_FIELD_OF_VIEW, IMAGE_WIDTH);
	}

	public static final double HORIZONTAL_FIELD_OF_VIEW = 75;
	public static final double VERTICAL_FIELD_OF_VIEW = 47;

	// double check
	public static final int IMAGE_WIDTH = 320;
	public static final int IMAGE_HEIGHT = 200;
}
