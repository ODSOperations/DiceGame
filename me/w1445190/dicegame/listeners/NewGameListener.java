package me.w1445190.dicegame.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import me.w1445190.dicegame.Main;

public class NewGameListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent event) {
		// Ask the player if he wants to create a new game.
		int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to start a new game?", "New Game", JOptionPane.YES_NO_OPTION);
		
		if (dialogResult == 0) {
			// Yeah? Then dispose of our current game.
			Main.getGame().disposeGameGUI();
		}
	}
}