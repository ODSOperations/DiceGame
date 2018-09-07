package me.w1445190.dicegame.dice;

import java.awt.Image;

public interface DieIntf {

	public Image getDieImage();
	public void setDieImage(Image image);
	public int getValue();
	public void setValue(int value);
}