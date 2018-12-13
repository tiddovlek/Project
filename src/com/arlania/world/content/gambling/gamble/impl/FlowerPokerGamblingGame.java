package com.arlania.world.content.gambling.gamble.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.arlania.world.content.gambling.GamblingManager;
import com.arlania.world.content.gambling.GamblingManager.Flowers;
import com.arlania.world.content.gambling.gamble.GamblingGame;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.GameObject;
import com.arlania.model.Position;
import com.arlania.model.movement.MovementQueue;
import com.arlania.util.Misc;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.entity.impl.player.Player;

/**
 * 
 * Handles the flower poker gamble game
 *
 * @author 2012 <http://www.rune-server.org/members/dexter+morgan/>
 *
 */
public class FlowerPokerGamblingGame extends GamblingGame {

	@Override
	public String toString() {
		return "Flower Poker";
	}

	/**
	 * The order of rank winnings
	 */
	private enum Ranking {
		/**
		 * Nothing
		 */
		NOTHING,
		/**
		 * Got 1 pair of the same flower
		 */
		PAIR,
		/**
		 * Got 2 pairs of the same flower
		 */
		TWO_PAIR,
		/**
		 * Got 3 of the same flower
		 */
		THREE_OF_KIND,
		/**
		 * Got 3 of the same flower plus 2 other of the same flower
		 */
		FULL_HOUSE,
		/**
		 * Got 4 of the same flower
		 */
		FOUR_OF_KIND,
		/**
		 * Got 5 of the same flower
		 */
		ROYAL_KIND,
	}

	/**
	 * Flower poker gambling games
	 * 
	 * @param host
	 *            the host
	 * @param opponent
	 *            the opponent
	 */
	public FlowerPokerGamblingGame(Player host, Player opponent) {
		super(host, opponent);
	}

	/**
	 * Gets the match of cards
	 * 
	 * @param player
	 *            the player
	 */
	private static Ranking getRank(Player player) {
		/**
		 * All the cards to prevent checking two lists
		 */
		ArrayList<Flowers> flowers = new ArrayList<Flowers>();
		/**
		 * Adds the cards to the list
		 */
		flowers.addAll(player.getGambling().getFlowers());
		/**
		 * Sorts the list
		 */
		Collections.sort(flowers);
		/**
		 * All the pairs
		 */
		Map<Integer, Integer> pairs = getPairs(flowers);
		/**
		 * Loops through the pairs
		 */
		for (int i = 0; i < pairs.size(); i++) {
			/**
			 * Invalid pair
			 */
			if (pairs.get(i) == null) {
				continue;
			}
			/**
			 * Four of kind
			 */
			if (pairs.get(i).intValue() == 5) {
				return Ranking.ROYAL_KIND;
			}
		}
		/**
		 * Has more than one pair
		 */
		if (pairs.size() == 2) {
			/**
			 * Full house
			 */
			if ((pairs.get(0).intValue() == 3 && pairs.get(1).intValue() == 2)
					|| (pairs.get(1).intValue() == 3 && pairs.get(0).intValue() == 2)) {
				return Ranking.FULL_HOUSE;
			}
		}
		/**
		 * The total pairs
		 */
		int totalPairs = 0;
		/**
		 * Loops through the pairs
		 */
		for (int i = 0; i < pairs.size(); i++) {
			/**
			 * Invalid pair
			 */
			if (pairs.get(i) == null) {
				continue;
			}
			/**
			 * Three of kind
			 */
			if (pairs.get(i).intValue() == 3) {
				return Ranking.THREE_OF_KIND;
			}
			/**
			 * Found a pair
			 */
			if (pairs.get(i).intValue() == 2) {
				totalPairs++;
			}
		}
		/**
		 * Two pair
		 */
		if (totalPairs == 2) {
			return Ranking.TWO_PAIR;
		}
		/**
		 * One pair
		 */
		if (totalPairs == 1) {
			return Ranking.PAIR;
		}
		return Ranking.NOTHING;
	}

	/**
	 * Gets the amount of pairs in the list
	 * 
	 * @param list
	 *            the list
	 * @return the pairs
	 */
	private static Map<Integer, Integer> getPairs(ArrayList<Flowers> list) {
		/**
		 * The final pairs
		 */
		Map<Integer, Integer> finalPairs = new HashMap<Integer, Integer>();
		/**
		 * The pairs
		 */
		int[] pairs = new int[14];
		/**
		 * Adds the card number to the resut
		 */
		for (Flowers flower : list) {
			/**
			 * Increases the pair
			 */
			pairs[flower.ordinal()]++;
		}
		/**
		 * The slot
		 */
		int slot = 0;
		/**
		 * Loops through the pairs
		 */
		for (int i = 0; i < pairs.length; i++) {
			/**
			 * Found pairs
			 */
			if (pairs[i] >= 2) {
				finalPairs.put(slot, pairs[i]);
				slot++;
			}
		}
		return finalPairs;
	}

	/**
	 * Planting a flower
	 * 
	 * @param player
	 *            the player
	 */
	private void plant(Player player, Player opponent) {

		TaskManager.submit(new Task(1, false) {

			int time = 0;

			int type = 0;
			Flowers flower = player.getGambling().getFlowers().get(type);

			@Override
			protected void execute() {
				switch (time) {
				case 1:
					player.performAnimation(new Animation(827));
					break;
				case 2:
					/**
					 * The flower
					 */
					flower = player.getGambling().getFlowers().get(type);
					/**
					 * No flower
					 */
					if (flower == null) {
						break;
					}
					/**
					 * The game flower
					 */
					final GameObject gameFlower = new GameObject(flower.getId(), player.getPosition().copy());
					/**
					 * Spawns flower
					 */
					CustomObjects.spawnObject(player, gameFlower);
					/**
					 * Adds the game flower
					 */
					player.getGambling().getGameFlowers().add(gameFlower);
					/**
					 * Sends to other player
					 */
					if (Objects.nonNull(getOtherPlayer(player))) {
						CustomObjects.spawnObject(getOtherPlayer(player), gameFlower);
					}
					/**
					 * Declares flower
					 */
					player.getMovementQueue().setLockMovement(false);
					opponent.getMovementQueue().setLockMovement(false);
					MovementQueue.stepAway(player);
					MovementQueue.stepAway(opponent);
					break;
				case 3:
					player.forceChat("I planted a " + Misc.formatText(flower.name().toLowerCase()) + " flower.");
					player.sendMessage("You planed: @red@" + Misc.formatText(flower.name()).toLowerCase());
					opponent.sendMessage("Opponent planted: @red@" + Misc.formatText(flower.name().toLowerCase()));
					break;
				case 4:
					if (type == 4) {
						this.stop();
						break;
					}
					time = 0;
					type++;
					break;
				}
				time++;
			}
		});
	}

	/**
	 * Gets the other player
	 * 
	 * @param player
	 *            the player
	 * @return the other player
	 */
	private Player getOtherPlayer(Player player) {
		/**
		 * No game
		 */
		if (player.getGambling().getGame() == null) {
			return null;
		}
		return player.getGambling().getGame().getHost() == player ? player.getGambling().getGame().getOpponent()
				: player.getGambling().getGame().getHost();
	}

	@Override
	public void gamble() {
		/**
		 * Resets flowers
		 */
		getHost().getGambling().getFlowers().clear();
		getOpponent().getGambling().getFlowers().clear();
		/**
		 * Resets game flowers
		 */
		getHost().getGambling().getGameFlowers().clear();
		getOpponent().getGambling().getGameFlowers().clear();
		/**
		 * Gets the flowers
		 */
		for (int i = 0; i < 5; i++) {
			Flowers hostFlower = Flowers.values()[Misc.getRandom(Flowers.values().length - 1)];
			Flowers opponentFlower = Flowers.values()[Misc.getRandom(Flowers.values().length - 1)];
			while (hostFlower == Flowers.BLACK || hostFlower == Flowers.WHITE) {
				hostFlower = Flowers.values()[Misc.getRandom(Flowers.values().length - 1)];
			}
			while (opponentFlower == Flowers.BLACK || hostFlower == Flowers.WHITE) {
				opponentFlower = Flowers.values()[Misc.getRandom(Flowers.values().length - 1)];
			}
			getHost().getGambling().getFlowers().add(hostFlower);
			getOpponent().getGambling().getFlowers().add(opponentFlower);
		}
		/**
		 * The host result
		 */
		final Ranking hostResult = getRank(getHost());
		/**
		 * The opponent result
		 */
		final Ranking opponentResult = getRank(getOpponent());
		/**
		 * The task
		 */
		TaskManager.submit(new Task(1, false) {

			int time = 0;

			@Override
			protected void execute() {
				switch (time) {

				case 0:
					Player player = getHost();
					Player opponent = getOpponent();
					player.getMovementQueue().setLockMovement(false);
					opponent.getMovementQueue().setLockMovement(false);
					if (player.getPosition().getX() == opponent.getPosition().getX()) {
						opponent.moveTo(new Position(player.getPosition().getX() - 1, player.getPosition().getY()));
					}
					break;

				case 1:
					plant(getHost(), getOpponent());
					plant(getOpponent(), getHost());
					break;

				case 24:
					getHost().forceChat("I got " + hostResult.name());
					getOpponent().forceChat("I got " + opponentResult.name());

					getOpponent()
							.sendMessage("Opponent got: " + hostResult.name() + ". I got " + opponentResult.name());
					getHost().sendMessage("Opponent got " + opponentResult.name() + ". I got " + hostResult.name());
					break;

				case 25:
					GamblingManager.finishGamble(GamblingManager.FLOWER_POKER_ID, getHost(), getOpponent(),
							hostResult.ordinal(), opponentResult.ordinal());
					this.stop();
					break;
				}
				time++;
			}
		});
	}
}