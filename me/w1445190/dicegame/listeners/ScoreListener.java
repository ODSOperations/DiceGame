package me.w1445190.dicegame.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import me.w1445190.dicegame.Main;

public class ScoreListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent event) {
		// Request a score.
		Main.getGame().score();
	}
}