package me.w1445190.dicegame;

import javax.swing.SwingUtilities;

import me.w1445190.dicegame.dice.Dice;
import me.w1445190.dicegame.game.Game;
import me.w1445190.dicegame.game.Stats;
import me.w1445190.dicegame.guis.StartGUI;

public class Main {

	// Self explanatory
	private static Dice dice = new Dice();
	private static Stats stats = new Stats();
	private static Game game;
	
	public static Dice getDice() {
		return dice;
	}
	
	public static Stats getStats() {
		return stats;
	}
	
	public static Game getGame() {
		return game;
	}
	
	public static void setGame(Game game) {
		Main.game = game;
	}
	
	public static void main(String[] args) {
		// PLEASE COMPILE WITH JAVA 8!!!
		// Lambdas are being used!

		// Fill our images
		dice.fillWithDice();
		
		// Open the Start GUI
		SwingUtilities.invokeLater(() -> new StartGUI().setVisible(true));
	}
}