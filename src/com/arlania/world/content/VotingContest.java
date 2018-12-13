package com.arlania.world.content;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.arlania.util.Misc;
import com.arlania.world.World;
import com.arlania.world.entity.impl.player.Player;

/**
 * Handles the Voting contest.
 * @author Gabriel Hannason
 * Modified by Jake
 */
public class VotingContest {

	/**
	 * The list holding all users who have entered the contest.
	 */
	private static final List<String> CONTESTERS = new ArrayList<String>();

	/*
	 * The location to the Voting file where users are saved.
	 */
	private static final File CONTESTERS_FILE_LOCATION = new File("./data/saves/voting/votes.txt");

	/*
	 * The location to the Voting file where the winners are saved.
	 */
	private static final File LAST_WINNER_FILE_LOCATION = new File("./data/saves/voting/voteswin.txt");

	/*
	 * Can players enter the Contest right now?
	 */
	private static boolean CONTEST_ENABLED = true;

	/*
	 * The user who won the Contest last
	 */
	private static String LAST_WINNER = "Jake";

	public static String getLastWinner() {
		return LAST_WINNER;
	}

	/*
	 * Has the last week's winner been rewarded?
	 */
	private static boolean LAST_WINNER_REWARDED = true;

	/**
	 * Gets a random winner for the contest.
	 * @return	A random user who has won the contest.
	 */
	public static String getRandomWinner() {
		String winner = null;
		int listSize = CONTESTERS.size();
		if(listSize >= 4)
			winner = CONTESTERS.get(Misc.getRandom(listSize - 1));
		return winner;
	}

	

	/**
	 * Reads the contest list and adds every user from the .txt files to the lists.
	 */
	public static void init() {
		try {
			BufferedReader r = new BufferedReader(new FileReader(CONTESTERS_FILE_LOCATION));
			while(true) {
				String line = r.readLine();
				if(line == null) {
					break;
				} else {
					line = line.trim();
				}
				if(line.length() > 0) {
					if(!CONTESTERS.contains(line))
						CONTESTERS.add(line);
				}
			}
			r.close();

			BufferedReader r2 = new BufferedReader(new FileReader(LAST_WINNER_FILE_LOCATION));
			while(true) {
				String line = r2.readLine();
				if(line == null) {
					break;
				} else {
					line = line.trim();
				}
				if(line.length() > 0) {
					if(!line.contains("NOT REWARDED. NEEDS REWARD!"))
						LAST_WINNER = line;
					else
						LAST_WINNER_REWARDED = false;
				}
			}
			r2.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Restarts the contest and rewards this week's winner.
	 */
	public static void restartContest() {
		if(!CONTEST_ENABLED)
			return;
		try {
			String winner = getRandomWinner();
			if(winner != null) {
				LAST_WINNER = winner;
				Player player = World.getPlayerByName(winner);
				BufferedWriter writer = new BufferedWriter(new FileWriter(LAST_WINNER_FILE_LOCATION));
				writer.write(winner);
				writer.newLine();
				if(player != null) {
					rewardPlayer(player, true);
				} else {
					LAST_WINNER_REWARDED = false;
					writer.write("NOT REWARDED. NEEDS REWARD!");
					System.out.println("Player "+winner+" won the contest but wasn't online.");
				}
				CONTESTERS.clear();
				writer.close();
				writer = new BufferedWriter(new FileWriter(CONTESTERS_FILE_LOCATION));
				writer.write("");
				writer.close();
				World.sendMessage("<col=FFFF00>"+winner+" Has won 10 Donator Points from Voting!!!");
			} else
				World.sendMessage("<col=FFFF00>Need more votes to pick a winner!.");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Rewards a player with items for winning the contest.
	 * @param player			The player to reward
	 * @param ignore			Should a check be ignored?
	 * @throws IOException		Throws exceptions
	 */
	public static void rewardPlayer(Player player, boolean ignore) throws IOException {
		if((!LAST_WINNER_REWARDED || ignore) && LAST_WINNER.equalsIgnoreCase(player.getUsername())) {
			LAST_WINNER_REWARDED = true;
			player.getPacketSender().sendMessage("You've won the Voting Contest for this week! Congratulations!");
			player.getInventory().add(619, 10); //change reward
			BufferedWriter writer = new BufferedWriter(new FileWriter(LAST_WINNER_FILE_LOCATION));
			writer.write(player.getUsername());
			writer.close();
		}
	}

	/**
	 * Handles the contest for a player on login
	 * Checks if a user won the contest without being rewarded.
	 * @param p		The player to handle login for.
	 */
	public static void onLogin(Player p) {
		try {
			rewardPlayer(p, false);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}