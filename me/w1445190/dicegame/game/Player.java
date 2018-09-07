package me.w1445190.dicegame.game;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import me.w1445190.dicegame.Main;
import me.w1445190.dicegame.dice.Die;

public class Player implements Comparable<Player> {

	// Self explanatory
	private JLabel scoreLabel;
	private JLabel rerollLabel;
	private GUIDie[] dice;
	private int score = 0;
	private int rerollsLeft = 3;
	
	public Player(JLabel scoreLabel, GUIDie[] dice) {
		this.scoreLabel = scoreLabel;
		this.dice = dice;
	}
	
	public JLabel getScoreLabel() {
		return scoreLabel;
	}
	
	public JLabel getRerollLabel() {
		return rerollLabel;
	}
	
	public void setRerollLabel(JLabel rerollLabel) {
		this.rerollLabel = rerollLabel;
	}
	
	public boolean hasRerollLabel() {
		return rerollLabel != null;
	}
	
	public GUIDie[] getDice() {
		return dice;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getRerollsLeft() {
		return rerollsLeft;
	}
	
	public boolean getCanReroll() {
		return rerollsLeft > 0;
	}
	
	public void updateRerollLabel(int offset) {
		// Offset is necessary because the first roll counts as a reroll.
		if (!hasRerollLabel()) {
			// We can't update this label for the computer
			return;
		}

		rerollLabel.setText("Rerolls left: " + (rerollsLeft + offset));
	}
	
	public void addScore(int score) {
		this.score += score;
		scoreLabel.setText("Score: " + this.score);
	}
	
	public void useReroll() {
		rerollsLeft--;
		updateRerollLabel(0);
	}
	
	public boolean willKeepDie(GUIDie die) {
		if (die.getValue() == 0) {
			// We can't keep question mark dice.
			return false;
		} else if (die.hasKeepButton()) {
			// If the player has a keep button for the die, then it is a human player.
			// In this case check the keep button's color.
			return die.getKeepButton().getBackground() == Color.GREEN;
		} else {
			// On the first roll, any 5s or 6s are kept. Then the first re-roll is used to change anything not kept.
			// After the first re-roll, any new 4s, 5s or 6s are kept. Anything not kept is re-rolled the second time.
			// After the second re-roll, no changes can be made.
			// The chance of getting all 6s and 5s on the first throw is 1/729. Individually, there is a 2/6th chance per dice of getting a 5 or 6.
			// For the first re-roll, I decided to stick with keeping 4s, 5s and 6s, instead of 3s, 4s, 5s and 6s.
			// Assuming no dice were kept from the initial roll, the chance of getting all 4s 5s and 6s is 1/64. If I included 3s that chance would be 1/11.
			// However, after testing I found that the best strategy is to go with the slightly lower, but acceptable, odds of 1/64 to keep only the 4s, 5s and 6s after the first re-roll.
			// Step 7 and Step 15 are contradictory, step 7 says we should randomly reroll but step 15 says we should have a strategy.
			
			int value = die.getValue();
			
			if (rerollsLeft == 2) {
				return value == 5 || value == 6;
			} else {
				return value == 4 || value == 5 || value == 6;
			}
		}
	}
	
	public void roll() {
		if (Main.getGame().isIntermission()) {
			// Are we in the middle of an intermission? Start a new round.
			Main.getGame().newRound();
			return;
		}

		boolean success = false;
		
		for (GUIDie die : dice) {
			if (!willKeepDie(die)) {
				// If we aren't keeping this die, we are changing it with a random one
				die.setDie(Main.getDice().rollOne());
				success = true;
			}
		}
		
		if (!success) {
			// We haven't changed any dice, so return.
			if (hasRerollLabel()) {
				JOptionPane.showMessageDialog(null, "Sorry, but you can't reroll if you are keeping all your dice!", "Oops!", JOptionPane.PLAIN_MESSAGE);
			}
			
			return;
		}

		// Use up a reroll.
		useReroll();
		
		// If we have no more rerolls, score automatically.
		if (rerollsLeft == 0 && hasRerollLabel()) {
			Main.getGame().score();
		}
	}
	
	public boolean score() {
		// Count together all dice's values
		int score = 0;

		for (GUIDie die : dice) {
			if (die.getValue() == 0) {
				return false;
			}

			score += die.getValue();
		}

		// Add to the score
		addScore(score);
		return true;
	}
	
	public void useAllRerolls() {
		// Use up all rerolls
		for (int i = 0; i < rerollsLeft; i++) {
			roll();
		}
	}
	
	public void newRound() {
		// Start a new round - replace dice with question marks, set keep button colors and update reroll label
		Die baseDie = Main.getDice().getDie(0);
		
		for (GUIDie die : dice) {
			die.setDie(baseDie);
			
			if (die.hasKeepButton()) {
				die.getKeepButton().setBackground(Color.RED);
			}
		}
		
		// Set rerolls left to the game's set value: this is useful during a tie
		rerollsLeft = Main.getGame().getRerollsGiven();
		updateRerollLabel(-1);
	}
	
	@Override
	public int compareTo(Player player) {
		// Compare the two player's scores
		return Integer.valueOf(score).compareTo(player.getScore());
	}
}