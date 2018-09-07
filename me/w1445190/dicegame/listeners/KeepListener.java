package me.w1445190.dicegame.listeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class KeepListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent event) {
		// Change our button's background color.
		// Green means: KEEP
		// Red means: DON'T KEEP
		JButton button = (JButton) event.getSource();
		
		button.setBackground(button.getBackground() == Color.RED ? Color.GREEN : Color.RED);
	}
}