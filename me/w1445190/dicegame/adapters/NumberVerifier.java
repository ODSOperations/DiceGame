package me.w1445190.dicegame.adapters;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NumberVerifier extends KeyAdapter {

	@Override
	public void keyTyped(KeyEvent event) {
		// Reject all non-number characters
		char character = event.getKeyChar();
		
		if (!(Character.isDigit(character) || character == KeyEvent.VK_BACK_SPACE || character == KeyEvent.VK_DELETE)) {
			event.consume();
		}
	}
}