package me.w1445190.dicegame.game;

import javax.swing.JOptionPane;

import me.w1445190.dicegame.Main;
import me.w1445190.dicegame.guis.GameGUI;
import me.w1445190.dicegame.guis.StartGUI;

public class Game {

	// All the things we care about
	private int targetScore;
	private int rerollsGiven = 3;
	private GameGUI gameGui;
	private Player human;
	private Player computer;
	private int round = 1;

	public Game(int targetScore, GameGUI gameGui, Player human, Player computer) {
		this.targetScore = targetScore;
		this.gameGui = gameGui;
		this.human = human;
		this.computer = computer;
	}
	
	public int getTargetScore() {
		return targetScore;
	}
	
	public int getRerollsGiven() {
		return rerollsGiven;
	}
	
	public GameGUI getGameGui() {
		return gameGui;
	}
	
	public Player getHuman() {
		return human;
	}
	
	public Player getComputer() {
		return computer;
	}
	
	public int getRound() {
		return round;
	}
	
	public boolean isIntermission() {
		return gameGui.getThrowButton().getText().equals("Continue");
	}
	
	// Human player scored
	public void score() {
		if (isIntermission()) {
			// Are we in the intermission? Start a new round.
			newRound();
			return;
		} else if (!human.score()) {
			// We don't have any dice yet? Complain.
			JOptionPane.showMessageDialog(null, "You need to throw first!", "Oops!", JOptionPane.PLAIN_MESSAGE);
			return;
		}

		// The computer does it's strategy
		new Thread(() -> {
			computer.useAllRerolls();
			computer.score();
			continueScore();
		}).start();
	}
	
	public void continueScore() {
		// Let's check, did we go over the score?
		if (human.getScore() >= targetScore || computer.getScore() >= targetScore) {
			int scoreComparation = human.compareTo(computer);
			
			if (scoreComparation == -1) {
				// Computer's score is higher, the human has lost.
				Main.getStats().addComputerWin();
				JOptionPane.showMessageDialog(null, "Darn! You lost :(\nPress OK to go back to the main menu.", "Oh well!", JOptionPane.PLAIN_MESSAGE);
				disposeGameGUI();
				return;
			} else if (scoreComparation == 1) {
				// Human's score is higher, the human has won.
				Main.getStats().addHumanWin();
				JOptionPane.showMessageDialog(null, "You win! Congraulations!\nPress OK to go back to the main menu.", "You win!", JOptionPane.PLAIN_MESSAGE);
				disposeGameGUI();
				return;
			} else {
				// Tie: set rerolls given to 1, this means there are no rerolls. The game continues.
				rerollsGiven = 1;
			}
		}
		
		gameGui.enterIntermission();
	}
	
	public void disposeGameGUI() {
		// Dispose of the game GUI.
		gameGui.dispose();
		new StartGUI().setVisible(true);
	}
	
	public void newRound() {
		// Start a new round.
		round++;
		gameGui.getRoundLabel().setText("Round " + round);
		human.newRound();
		computer.newRound();
		gameGui.exitIntermission();
	}
}