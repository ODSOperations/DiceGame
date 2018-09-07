package me.w1445190.dicegame;

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;

public class Utils {

	public static int parseInt(String string) {
		// Parse an integer if possible, if not just return 0.
		try {
			return Integer.valueOf(string);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static void centerFrame(JFrame frame) {
		// Center a JFrame on the first screen.
		// I can't use setLocationRelativeTo because it doesn't work in a multiscreen environment,
		// i.e when one part of the GUI is on the first screen and the second part is on the second screen.
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle bounds = environment.getScreenDevices()[0].getDefaultConfiguration().getBounds();
		
		int frameX = ((bounds.width - frame.getWidth()) / 2) + bounds.x;
		int frameY = ((bounds.height - frame.getHeight()) / 2) + bounds.y;
		
		frame.setLocation(frameX, frameY);
	}
}