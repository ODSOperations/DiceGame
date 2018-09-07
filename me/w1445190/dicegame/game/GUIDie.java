package me.w1445190.dicegame.game;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import me.w1445190.dicegame.Main;
import me.w1445190.dicegame.dice.Die;

public class GUIDie {

	// Self explanatory
	private JLabel icon;
	private JButton keepButton;
	private int value;
	
	public GUIDie(JLabel icon, JButton keepButton) {
		this.icon = icon;
		this.keepButton = keepButton;
		// The default image is the question mark
		setDie(Main.getDice().getDie(0));
	}
	
	public JLabel getIcon() {
		return icon;
	}
	
	public void setIcon(Icon icon) {
		this.icon.setIcon(icon);
	}
	
	public void setIcon(Image image) {
		setIcon(new ImageIcon(image));
	}
	
	public JButton getKeepButton() {
		return keepButton;
	}
	
	public boolean hasKeepButton() {
		return keepButton != null;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setDie(Die die) {
		setIcon(die.getDieImage());
		value = die.getValue();
	}
}