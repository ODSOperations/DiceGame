package me.w1445190.dicegame.dice;

import java.util.Random;

import javax.imageio.ImageIO;

public class Dice {

	private Die[] dice;
	private Random random = new Random();
	
	public Die[] getDice() {
		return dice;
	}
	
	public Random getRandom() {
		return random;
	}
	
	public Die getDie(int value) {
		// Get a die by value.
		return dice[value];
	}
	
	public void fillWithDice() {
		// Fill the dice with images
		dice = new Die[7];
		
		for (int i = 0; i < 7; i++) {
			try {
				dice[i] = new Die(ImageIO.read(getClass().getClassLoader().getResource(String.format("die_face_%s.png", i))), i);
			} catch (Exception e) {
				// RIP
				System.out.println("Couldn't load dice!");
				System.exit(0);
			}
		}
	}
	
	public Die rollOne() {
		// Get a random die.
		return dice[random.nextInt(dice.length - 1) + 1];
	}
	
	public Die[] roll() {
		// Get an array of 5 random dice.
		Die[] dice = new Die[6];
		
		for (int i = 0; i < 5; i++) {
			dice[i] = rollOne();
		}
		
		return dice;
	}
}