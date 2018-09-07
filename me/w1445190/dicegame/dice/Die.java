package me.w1445190.dicegame.dice;

import java.awt.Image;

public class Die implements Comparable<Die>, DieIntf {

	public Image image;
	public int value;
	
	public Die(Image image, int value) {
		this.image = image;
		this.value = value;
	}
	
	@Override
	public Image getDieImage() {
		return image;
	}

	@Override
	public void setDieImage(Image image) {
		this.image = image;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public void setValue(int value) {
		// Sanity check
		if (value <= 0 || value > 6) {
			return;
		}
		
		this.value = value;
	}

	@Override
	public int compareTo(Die die) {
		// Compare the two dice's values
		return Integer.valueOf(die.getValue()).compareTo(value);
	}
}