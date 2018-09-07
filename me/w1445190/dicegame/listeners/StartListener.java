package me.w1445190.dicegame.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import me.w1445190.dicegame.Utils;
import me.w1445190.dicegame.guis.GameGUI;
import me.w1445190.dicegame.guis.StartGUI;

public class StartListener implements ActionListener {

	private StartGUI startGui;
	
	public StartListener(StartGUI startGui) {
		this.startGui = startGui;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		// Verify our score
		int score = Utils.parseInt(startGui.getNumberField().getText());
		
		if (score <= 0 || score > 999) {
			// Not in the range? Then we're gonna complain
			JOptionPane.showMessageDialog(null, "The score must be between 0 and 999!", "Oops!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// Dispose of our start GUI and create the game
		startGui.dispose();
		new GameGUI(score).setVisible(true);
	}
}