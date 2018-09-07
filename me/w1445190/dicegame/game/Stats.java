package me.w1445190.dicegame.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

import me.w1445190.dicegame.Main;
import me.w1445190.dicegame.Utils;

public class Stats {

	private int humanWins = 0;
	private int computerWins = 0;
	
	public Stats() {
		// Load stats
		loadStats("stats.txt");
	}
	
	public int getHumanWins() {
		return humanWins;
	}
	
	public int getComputerWins() {
		return computerWins;
	}
	
	public int getTotalGames() {
		return humanWins + computerWins;
	}
	
	public void addHumanWin() {
		humanWins++;
		Main.getGame().getGameGui().updateStats();
		writeStats("stats.txt");
	}
	
	public void addComputerWin() {
		computerWins++;
		Main.getGame().getGameGui().updateStats();
		writeStats("stats.txt");
	}
	
	public void loadStats(String filename) {
		// Load stats
		File file = new File(filename);
		
		if (!file.exists()) {
			// No file? Then we can't load the stats
			return;
		}
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
	
			humanWins = Utils.parseInt(reader.readLine());
			computerWins = Utils.parseInt(reader.readLine());
			
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeStats(String filename) {
		// Write stats
		File file = new File(filename);
		PrintWriter writer;
		
		try {
			if (!file.exists()) {
				// No file? We need to create one
				file.createNewFile();
			}
				
			writer = new PrintWriter(file);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		// Write the stats and close the writer
		writer.println(humanWins);
		writer.println(computerWins);
		writer.close();
	}
}