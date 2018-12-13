package com.arlania.world.content.minigames.impl.weapon;

import java.util.*;
import java.util.Map.Entry;

import com.google.common.collect.*;
import com.arlania.engine.task.*;
import com.arlania.model.*;
import com.arlania.model.container.impl.Equipment;
import com.arlania.world.World;
import com.arlania.world.content.transportation.TeleportHandler;
import com.arlania.world.content.transportation.TeleportType;
import com.arlania.world.entity.impl.player.*;

/**
 * The class that will handle the Weapon Game minigame.
 * 
 * @author Jacob G. <Skype: Eclips3.HF>
 */
public final class WeaponGame extends Task {

	/**
	 * The state of a server-wide game.
	 */
	public static boolean ACTIVE_GAME;
	
	/**
	 * The minimum amount of {@link Player}s 
	 * required in the waiting room to be able
	 * to start a game.
	 */
	public static final int MINIMUM_PLAYERS = 2;
	
	/**
	 * The maximum amount of players that can be in
	 * the waiting room or in-game simultaneously.
	 */
	public static final int MAXIMUM_PLAYERS = 25;
	
	/**
	 * The amount of points that will be awarded
	 * to the {@link Player} who participates in,
	 * but does not win, the {@link WeaponGame}.
	 */
	public static final int PARTICIPANT_REWARD = 5;
	
	/**
	 * The amount of time (in minutes) that
	 * games will last for.
	 */
	public static final int WAIT_TIME = 1;
	
	/**
	 * The tick-counter that will keep track
	 * of how much time is left in a game.
	 */
	public static int GAME_TIME = WAIT_TIME * 100;
	
	/**
	 * The amount of points that will be awarded
	 * to the {@link Player} who wins the
	 * {@link WeaponGame}.
	 */
	public static final int WIN_REWARD = 20;
	
	/**
	 * The {@link Position} that a {@link Player}
	 * will spawn in if they enter the game.
	 */
	public static final Position GAME_LOCATION = new Position(3168, 9758);
	
	/**
	 * The {@link Position} that a {@link Player}
	 * will spawn in if they enter the lobby.
	 */
	public static final Position LOBBY_LOCATION = new Position(3187, 9758);
	
	/**
	 * The {@link Position} that a {@link Player}
	 * will spawn in if they leave the lobby, die
	 * in an active game, etc.
	 */
	public static final Position RESPAWN_LOCATION = new Position(3318, 3241);
	
	/**
	 * The {@link Set} that will hold the name
	 * of every {@link Player} in the lobby of
	 * this minigame.
	 */
	public static final Set<String> PLAYERS_IN_LOBBY = new HashSet<>();

	/**
	 * The {@link Multimap} that will hold the name
	 * of every {@link Player} in this minigame along
	 * with the rank of their current weapon.
	 */
	public static final Multimap<WeaponGameWeapons, String> PLAYERS_IN_GAME = TreeMultimap.create(Ordering.natural().reverse(), Ordering.natural());
	
	/**
	 * The constructor of this class which is
	 * only used to start the task at a rate
	 * of executing once every 10 minutes.
	 */
	public WeaponGame() {
		super(GAME_TIME, false);
	}
	
	/**
	 * The method that will be called once
	 * every 10 minutes (1,000 ticks) from
	 * when the server starts.
	 * <p>
	 * Most of the {@link WeaponGame} will
	 * be handled within this method.
	 */
	@Override
	protected void execute() {
		/*
		 * If there is an active game,
		 * we can assume that there has
		 * been no winner.
		 */
		if (ACTIVE_GAME) {
			endGame(Optional.empty());
		}
		
		/*
		 * Now that the active game
		 * has been emptied, we can
		 * check if there are enough
		 * players to start a new game.
		 */
		if (PLAYERS_IN_LOBBY.size() >= MINIMUM_PLAYERS) {
			movePlayersIntoGame();
		} else {
			World.sendMessage("[@dre@WEAPON GAME@bla@] " + (MINIMUM_PLAYERS - PLAYERS_IN_LOBBY.size()) + 
				" more players are needed to start a new game!");
		}
		
		/*
		 * Reset GAME_TIME.
		 */
		GAME_TIME = WAIT_TIME * 100;
	}
	
	/**
	 * The method that will be called when a
	 * {@link Player} attempts to join the
	 * lobby.
	 * <p>
	 * This method is {@code synchronized} so
	 * that players are added 'one-by-one', thus not
	 * allowing the amount of players in the lobby
	 * to go past the {@code MAXIMUM_PLAYERS} limit.
	 * 
	 * @param player
	 * 		The player attempting to join the lobby.
	 * 
	 * @return {@code true} if the player successfully
	 * 		   joined the lobby, otherwise {@code false}.
	 */
	public static synchronized boolean addToLobby(Player player) {
		/*
		 * If there are already MAXIMUM_PLAYERS in
		 * the lobby, we don't want the player to
		 * join the lobby.
		 * 
		 * We also want to make sure that nothing
		 * else is using PLAYERS_IN_LOBBY before
		 * checking the size, so we synchronize it
		 * first.
		 */
		synchronized (PLAYERS_IN_LOBBY) {
			if (PLAYERS_IN_LOBBY.contains(player.getUsername())) {
				player.getPacketSender().sendMessage("You are already in the lobby!");
				return false;
			}
			
			if (PLAYERS_IN_LOBBY.size() == MAXIMUM_PLAYERS) {
				player.getPacketSender().sendMessage("The lobby is currently full, please try again later!");
				return false;
			}
			
			/*
			 * If the player has any items equipped, we
			 * don't want the player to join the lobby.
			 */
			if (player.getEquipment().getFreeSlots() != player.getEquipment().capacity()) {
				player.getPacketSender().sendMessage("You must remove all of your equipment before joining the lobby!");
				return false;
			}
			
			/**
			 * If the player has any items in their
			 * inventory, we don't want the player 
			 * to join the lobby.
			 */
			if (player.getInventory().getFreeSlots() != player.getInventory().capacity()) {
				player.getPacketSender().sendMessage("You must remove everything from your inventory before joining the lobby!");
				return false;
			}
			
			/*
			 * Otherwise, allow them to join the
			 * lobby.
			 */
			if (PLAYERS_IN_LOBBY.add(player.getUsername())) {
				TeleportHandler.teleportPlayer(player, LOBBY_LOCATION, TeleportType.NORMAL);
				return true;
			}
		}
		
		/*
		 * The code should never reach this point.
		 */
		throw new IllegalStateException("Invalid Weapon Lobby Add");
	}
	
	/**
	 * The method that will be called when a
	 * {@link Player} attempts to leave the
	 * lobby.
	 * <p>
	 * We want to synchronize {@code PLAYERS_IN_LOBBY}
	 * as well so that if a player decides to leave
	 * right as they're being added to a game, nothing
	 * will conflict.
	 * 
	 * @param player
	 * 		The player attempting to leave
	 * 		the lobby.
	 */
	public static void removeFromLobby(Player player) {
		synchronized (PLAYERS_IN_LOBBY) {
			if (PLAYERS_IN_LOBBY.remove(player.getUsername())) {
				player.moveTo(RESPAWN_LOCATION);
				player.getPacketSender().sendInterfaceRemoval();
				return;
			}
			
			/*
			 * The code should never reach this point.
			 */
			throw new IllegalStateException("Invalid Weapon Lobby Remove");
		}
	}
	
	/**
	 * This method will add every {@link Player}
	 * currently in the {@link WeaponGame} lobby
	 * into the actual game area.
	 * <p>
	 * We assume that when this method is called,
	 * every assertion has passed since they will
	 * be checked inside {@link #execute()}.
	 * <p>
	 * Also, we want to synchronize {@code PLAYERS_IN_LOBBY}
	 * so that no player is left behind.
	 */
	public static void movePlayersIntoGame() {
		synchronized (PLAYERS_IN_LOBBY) {
			for (String username : PLAYERS_IN_LOBBY) {
				Player player = World.getPlayerByName(username);
				
				player.moveTo(GAME_LOCATION);
				player.getPacketSender().sendInteractionOption("Attack", 2, true);
				
				PLAYERS_IN_GAME.put(WeaponGameWeapons.UNARMED, username);
			}
			
			PLAYERS_IN_LOBBY.clear();
			
			ACTIVE_GAME = true;
			
			World.sendMessage("[@dre@WEAPON GAME@bla@] A new game has just begun!");
		}
	}
	
	/**
	 * This method removes a {@link Player} from
	 * an active, server-wide {@link WeaponGame}.
	 * <p>
	 * Because they have decided to leave mid-game,
	 * they will not be rewarded any points.
	 * <p>
	 * {@code PLAYERS_IN_GAME} is synchronized so
	 * that, we can properly check if the player
	 * leaving is the last player left.
	 * 
	 * @param player
	 * 		The player leaving the game.
	 */
	public static boolean removeFromGame(Player player) {
		synchronized (PLAYERS_IN_GAME) {
			if (PLAYERS_IN_GAME.size() == 1) {
				player.getPacketSender().sendMessage("You have already won! Please wait.");
				return false;
			}
			
			if (PLAYERS_IN_GAME.remove(getWeapon(player), player.getUsername())) {
				player.moveTo(RESPAWN_LOCATION);
				player.getPacketSender().sendInterfaceRemoval();
				return true;
			}
			
			/*
			 * The code should never reach this point.
			 */
			throw new IllegalStateException("Invalid Weapon Game Remove");
		}
	}
	
	/**
	 * The method that is called when the {@link WeaponGame}
	 * ends, either due to a {@link Player} winning or the
	 * time running out.
	 * 
	 * @param player
	 * 		An {@link Optional} that is either empty
	 * 		or holds the winning player.
	 */
	public static void endGame(Optional<Player> maybePlayer) {
		/*
		 * Set the state of the minigame to
		 * inactive so that the players in
		 * the lobby are able to join.
		 */
		ACTIVE_GAME = false;
		
		/*
		 * If there is no winner, then we must
		 * find the player with the most
		 * powerful weapon to award the
		 * points to. 
		 */
		if (!maybePlayer.isPresent()) {
			Player player = null;
			
			for (Entry<WeaponGameWeapons, String> e : PLAYERS_IN_GAME.entries()) {
				/*
				 * If only one person has this weapon,
				 * then we can reward them with the
				 * amount of points that a winner would
				 * get and reward the rest with a smaller
				 * amount.
				 * 
				 * Otherwise, reward everyone with a
				 * small amount because there was a
				 * tie between multiple players.
				 */
				if (PLAYERS_IN_GAME.get(e.getKey()).size() == 1) {
					player = World.getPlayerByName(e.getValue());
					
					if (player == null) {
						continue;
					}
					
					player.setWeaponGamePoints(player.getWeaponGamePoints() + 20);
					player.getPacketSender().sendMessage("You have been rewarded with @dre@20 @bla@Weapon Game Points!");
					break;
				}
			}
			
			for (String username : PLAYERS_IN_GAME.values()) {
				if (player != null && player.getUsername().equals(username)) {
					player.getInventory().resetItems();
					player.getEquipment().resetItems();
					player.moveTo(RESPAWN_LOCATION);
					continue;
				}
				
				Player p = World.getPlayerByName(username);
				
				if (p == null) {
					continue;
				}
				
				p.getInventory().resetItems();
				p.getEquipment().resetItems();
				p.moveTo(RESPAWN_LOCATION);
				p.setWeaponGamePoints(p.getWeaponGamePoints() + 5);
				p.getPacketSender().sendMessage("You have been rewarded with @dre@5 @bla@Weapon Game Points!");
			}
			
			resetInterface();
			return;
		}
		
		/*
		 * Otherwise, award the most points
		 * to the winner and a smaller amount
		 * of points to the participants that
		 * are left.
		 */
		Player winner = maybePlayer.get();
		
		winner.setWeaponGamePoints(winner.getWeaponGamePoints() + 20);
		winner.getPacketSender().sendMessage("You have been rewarded with @dre@20 @bla@Weapon Game Points!");
		
		for (String username : PLAYERS_IN_GAME.values()) {
			if (winner.getUsername().equals(username)) {
				winner.getInventory().resetItems();
				winner.getEquipment().resetItems();
				winner.moveTo(RESPAWN_LOCATION);
				continue;
			}
			
			Player player = World.getPlayerByName(username);
			
			if (player == null) {
				continue;
			}
			
			player.getInventory().resetItems();
			player.getEquipment().resetItems();
			player.moveTo(RESPAWN_LOCATION);
			player.setWeaponGamePoints(player.getWeaponGamePoints() + 5);
			player.getPacketSender().sendMessage("You have been rewarded with @dre@5 @bla@Weapon Game Points!");
		}
		
		World.sendMessage("[@dre@WEAPON GAME@bla@] Congratulations to @dre@" + winner.getUsername() + 
			" @bla@for winning the last Weapon Game!");
		
		resetInterface();
	}
	
	/**
	 * A helper method to easily get a
	 * {@link Player}'s weapon rank when
	 * providing a player as a parameter.
	 * 
	 * @param player
	 * 		The player to get the weapon
	 * 		rank for.
	 * 
	 * @return the player's weapon rank.
	 */
	public static WeaponGameWeapons getWeapon(Player player) {
		return WeaponGameWeapons.forItem(Optional.of(player.getEquipment().get(Equipment.WEAPON_SLOT)));
	}
	
	/**
	 * A method that is called every tick
	 * so we can easily keep track of time.
	 */
	public static void sequence() {
		GAME_TIME--;
		
		/*
		 * If 3 seconds have passed, we want
		 * to update the client-sided times
		 * for all of our players. The remaining
		 * time of each 3-second interval is
		 * interpolated client-sided.
		 */
		if (GAME_TIME % 5 == 0) {
			int minutes = GAME_TIME / 100;
			int seconds = GAME_TIME % 100 * 6 / 10;
			
			String timeLeft = "Time Left | " + (minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds;
			
			List<String> players = new ArrayList<>(PLAYERS_IN_LOBBY.size() + PLAYERS_IN_GAME.size());
			
			players.addAll(PLAYERS_IN_LOBBY);
			players.addAll(PLAYERS_IN_GAME.values());
			
			for (String username : players) {
				Player player = World.getPlayerByName(username);
				
				if (player == null) {
					continue;
				}
				
				player.getPacketSender().sendString(62008, timeLeft);
			}
		}
	}
	
	/**
	 * A helper method to remove the
	 * top five {@link Player}s on the
	 * interface when in the game or lobby.
	 * <p>
	 * Because this method will only be
	 * called when a game ends, we can
	 * clear {@code PLAYERS_IN_GAME}.
	 */
	private static void resetInterface() {
		List<String> players = new ArrayList<>(WeaponGame.PLAYERS_IN_LOBBY.size() + WeaponGame.PLAYERS_IN_GAME.size());
		
		players.addAll(WeaponGame.PLAYERS_IN_LOBBY);
		players.addAll(WeaponGame.PLAYERS_IN_GAME.values());
		
		for (String username : players) {
			Player player = World.getPlayerByName(username);
			
			if (player == null) {
				continue;
			}
			
			for (int i = 0; i < 5; i++) {
				player.getPacketSender().sendString(62003 + i, "");
			}
		}
		
		PLAYERS_IN_GAME.clear();
	}
	
}