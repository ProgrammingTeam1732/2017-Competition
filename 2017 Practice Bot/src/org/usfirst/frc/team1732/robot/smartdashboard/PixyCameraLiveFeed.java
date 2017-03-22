package org.usfirst.frc.team1732.robot.smartdashboard;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.usfirst.frc.team1732.robot.Robot;

public class PixyCameraLiveFeed extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BufferedImage bf;
	private Timer timer;
	private Rectangle[] rectangles;

	public PixyCameraLiveFeed() {
		rectangles = Robot.visionMain.getRectangles();
		bf = new BufferedImage(320, 200, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < bf.getWidth(); i++) {
			for (int j = 0; j < bf.getHeight(); j++) {
				for (Rectangle r : rectangles) {
					if (r.contains(i, j))
						bf.setRGB(i, j, Color.WHITE.getRGB());
				}
			}
		}
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel i = new JLabel(new ImageIcon(bf));
		this.add(i);
		this.pack();
		this.setVisible(true);
		timer = new Timer(1000 / 30, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < bf.getWidth(); i++) {
					for (int j = 0; j < bf.getHeight(); j++) {
						bf.setRGB(i, j, Color.BLACK.getRGB());
					}
				}
				for (int i = 0; i < bf.getWidth(); i++) {
					for (int j = 0; j < bf.getHeight(); j++) {
						for (Rectangle r : rectangles) {
							if (r.contains(i, j))
								bf.setRGB(i, j, Color.WHITE.getRGB());
						}
					}
				}
				i.setIcon(new ImageIcon(bf));
			}
		});
	}

	public void play() {
		timer.start();
	}

	public void pause() {
		timer.stop();
	}
}
