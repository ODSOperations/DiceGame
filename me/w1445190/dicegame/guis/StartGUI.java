package me.w1445190.dicegame.guis;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import me.w1445190.dicegame.Main;
import me.w1445190.dicegame.Utils;
import me.w1445190.dicegame.adapters.NumberVerifier;
import me.w1445190.dicegame.listeners.StartListener;

public class StartGUI extends JFrame {
	
	private JTextField numberField;
	
	public StartGUI() {
		// RIP our current game
		Main.setGame(null);
		
		// Set general settings
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Welcome!");
		setLayout(new GridLayout(3, 0));
		setMinimumSize(new Dimension(300, 100));
		setIconImage(Main.getDice().getDie(6).getDieImage());
		
		// Create our objects
		JLabel label = new JLabel("Welcome to Dice Game! What score should we try to reach?", SwingConstants.CENTER);
		numberField = new JTextField("", SwingConstants.CENTER);
		JButton button = new JButton("Play now!");
		
		// Add key listener to verify that we only get numbers at input
		// and an action listener to start a new game
		numberField.addKeyListener(new NumberVerifier());
		button.addActionListener(new StartListener(this));
		
		// Add our objects to the frame and pack up
		add(label);
		add(numberField);
		add(button);
		pack();
		Utils.centerFrame(this);
	}
	
	public JTextField getNumberField() {
		return numberField;
	}
}