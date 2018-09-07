package me.w1445190.dicegame.guis;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import me.w1445190.dicegame.Main;
import me.w1445190.dicegame.Utils;
import me.w1445190.dicegame.game.GUIDie;
import me.w1445190.dicegame.game.Game;
import me.w1445190.dicegame.game.Player;
import me.w1445190.dicegame.game.Stats;
import me.w1445190.dicegame.listeners.KeepListener;
import me.w1445190.dicegame.listeners.NewGameListener;
import me.w1445190.dicegame.listeners.ScoreListener;
import me.w1445190.dicegame.listeners.ThrowListener;

public class GameGUI extends JFrame {
	
	private JLabel roundLabel;
	private JLabel totalGamesLabel;
	private JLabel humanWinsLabel;
	private JLabel computerWinsLabel;
	private JButton throwButton;
	private JButton scoreButton;
	
	public GameGUI(int targetScore) {
		// Basic stuff
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1419, 707));
		setTitle("Dice Game");
		setIconImage(Main.getDice().getDie(6).getDieImage());
		// Border layout so we can have a top frame, center frame and bottom frame
		setLayout(new BorderLayout());
		
		// Create top pane with target score and rerolls label
		JPanel topPane = new JPanel();
		
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.Y_AXIS));
		
		// Create our labels
		roundLabel = createLabel(topPane, "Round 1", Font.BOLD, 30, Color.ORANGE);
		createLabel(topPane, "Score to reach: " + targetScore, Font.BOLD, 25, Color.RED);
		JLabel rerollLabel = createLabel(topPane, "Rerolls left: 2", Font.BOLD, 25, Color.RED);
		
		// Finally add the top pane
		add(topPane, BorderLayout.NORTH);
		
		// Seperate panels for player and computer
		JPanel playerPanel = new JPanel();
		JPanel computerPanel = new JPanel();
		
		// Set them up
		Player human = setupPanel(playerPanel, true);
		Player computer = setupPanel(computerPanel, false);
		
		human.setRerollLabel(rerollLabel);
		
		// Create a split pane
		JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		pane.setLeftComponent(playerPanel);
		pane.setRightComponent(computerPanel);
		pane.setResizeWeight(0.5);
		pane.setEnabled(false);
		pane.setDividerSize(1);
		add(pane, BorderLayout.CENTER);
		
		// Create bottom pane with button and stat panel
		JPanel bottomPane = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel statPanel = new JPanel();

		// For bottom pane - box layout so we can have the 2 panels underneath each other
		// For button panel - flow layout so we can have centered buttons
		// For stat panel - box layout so we can show the text in the center in multiple lines
		bottomPane.setLayout(new BoxLayout(bottomPane, BoxLayout.Y_AXIS));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
		statPanel.setLayout(new BoxLayout(statPanel, BoxLayout.Y_AXIS));
		
		// Create button panel with buttons
		throwButton = createButton(buttonPanel, "Throw", 100, 50, new ThrowListener(), null);
		createButton(buttonPanel, "New Game", 100, 50, new NewGameListener(), null);
		scoreButton = createButton(buttonPanel, "Score", 100, 50, new ScoreListener(), null);
		
		// Finally add the button panel
		bottomPane.add(buttonPanel);
		
		// Create stat panel
		// Create our labels
		createLabel(statPanel, "STATS", Font.BOLD + Font.ITALIC, 25, Color.BLACK, 25);
		totalGamesLabel = createLabel(statPanel, "Total games played: 0", Font.BOLD + Font.ITALIC, 25, Color.BLACK, 5);
		humanWinsLabel = createLabel(statPanel, "Human wins: 0", Font.BOLD + Font.ITALIC, 25, Color.BLACK, 5);
		computerWinsLabel = createLabel(statPanel, "Computer wins: 0", Font.BOLD + Font.ITALIC, 25, Color.BLACK);
		updateStats();
		
		// Finally add the stat panel
		insertSeparator(bottomPane);
		bottomPane.add(statPanel);
		
		// Add the bottom pane to the bottom
		add(bottomPane, BorderLayout.SOUTH);
		
		// Create our game
		Main.setGame(new Game(targetScore, this, human, computer));

		// Pack up
		pack();
		Utils.centerFrame(this);
	}
	
	public JLabel getRoundLabel() {
		return roundLabel;
	}
	
	public JLabel getTotalGamesLabel() {
		return totalGamesLabel;
	}
	
	public JLabel getHumanWinsLabel() {
		return humanWinsLabel;
	}
	
	public JLabel getComputerWinsLabel() {
		return computerWinsLabel;
	}
	
	public JButton getThrowButton() {
		return throwButton;
	}
	
	public JButton getScoreButton() {
		return scoreButton;
	}
	
	public JButton createButton(String text, int xSize, int ySize, ActionListener listener) {
		// Create a button with the given options.
		JButton button = new JButton(text);
		button.setPreferredSize(new Dimension(xSize, ySize));
		button.addActionListener(listener);
		
		return button;
	}
	
	public JButton createButton(JPanel panel, String text, int xSize, int ySize, ActionListener listener, Object constraints) {
		// Create a button AND add it to the panel.
		JButton button = createButton(text, xSize, ySize, listener);
		
		panel.add(button, constraints);
		return button;
	}
	
	public JLabel createLabel(String text, int fontType, int fontSize, Color color) {
		// Create a label with the given options.
		JLabel label = new JLabel(text);
		label.setFont(new Font("Segoe UI", fontType, fontSize));
		label.setForeground(color);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		return label;
	}
	
	public JLabel createLabel(JPanel panel, String text, int fontType, int fontSize, Color color) {
		// Create a label AND add it to the panel.
		JLabel label = createLabel(text, fontType, fontSize, color);
		
		panel.add(label);
		return label;
	}
	
	public JLabel createLabel(JPanel panel, String text, int fontType, int fontSize, Color color, int strut) {
		// Create a label, add it to the panel, and add strut.
		JLabel label = createLabel(panel, text, fontType, fontSize, color);
		
		panel.add(Box.createVerticalStrut(strut));
		return label;
	}
	
	public void insertSeparator(JPanel panel) {
		// Insert a horizontal seperator
		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		separator.setPreferredSize(new Dimension(50, 3));
		panel.add(separator);
	}
	
	public Player setupPanel(JPanel panel, boolean human) {
		// Let's setup the panel!
		// First things first, let's set a layout... BorderLayout because we are splitting this to top and center panels.
		panel.setLayout(new BorderLayout());
		
		// First, let's create a stat panel.
		// We are using a BoxLayout so we can display text underneath each other.
		JPanel statPanel = new JPanel();
		
		statPanel.setLayout(new BoxLayout(statPanel, BoxLayout.Y_AXIS));
		
		// Let's add our stats.
		createLabel(statPanel, human ? "Human" : "Computer", Font.BOLD, 20, human ? Color.BLUE : Color.RED, 15);
		JLabel scoreLabel = createLabel(statPanel, "Score: 0", Font.BOLD + Font.ITALIC, 15, human ? Color.RED : Color.BLUE);
		
		// We're done with statistics, let's add the frame to the top part of the GUI.
		panel.add(statPanel, BorderLayout.NORTH);
		
		// Let's work on the dices next.
		// We are wrapping the dice panel to a GridBagLayout so it's centered.. RIP Java
		// We are using a GridBagLayout so we can arrange these objects in a grid with space between them.
		JPanel dicePanel1 = new JPanel(new GridBagLayout());
		JPanel dicePanel = new JPanel(new GridBagLayout());
		GridBagConstraints bag = new GridBagConstraints();

		dicePanel1.add(dicePanel);

		// Let's set weight and insets first. Those are the same for every object added.
		bag.weightx = 1.0;
		bag.weighty = 1.0;
		bag.insets = new Insets(5, 20, 5, 20);
		
		// Let's add our dice!
		GUIDie[] guiDice = new GUIDie[5];

		for (int i = 0; i < 5; i++) {
			// First create a die image...
			// We are using grid nr. 1 for the buttons
			JLabel die = new JLabel();
			JButton keepButton = null;
			bag.gridy = 1;
			
			// Is this a human?
			// If so, create a Keep button, else create a fake rigid area so the two panels
			// (player and computer) are at the same level.
			if (human) {
				keepButton = createButton(dicePanel, "Keep", 100, 50, new KeepListener(), bag);
				keepButton.setBackground(Color.RED);
			} else {
				dicePanel.add(Box.createRigidArea(new Dimension(100, 50)), bag);
			}
			
			// We are using grid nr. 0 for the images.
			bag.gridy = 0;
			dicePanel.add(die, bag);
			
			// Add the GUI Die object to our dice array. This will allow us to update.
			guiDice[i] = new GUIDie(die, keepButton);
		}
		
		// Add the wrapped dice panel to the center of the panel
		panel.add(dicePanel1, BorderLayout.CENTER);
		
		// Return a new player object
		return new Player(scoreLabel, guiDice);
	}
	
	public void updateStats() {
		// Update our stats
		Stats stats = Main.getStats();
		
		totalGamesLabel.setText("Total games played: " + stats.getTotalGames());
		humanWinsLabel.setText("Human wins: " + stats.getHumanWins());
		computerWinsLabel.setText("Computer wins: " + stats.getComputerWins());
	}
	
	public void enterIntermission() {
		// Enter round intermission.
		throwButton.setText("Continue");
		scoreButton.setText("Continue");
	}
	
	public void exitIntermission() {
		// Exit round intermission.
		throwButton.setText("Throw");
		scoreButton.setText("Score");
	}
}