package com.arlania.world.content.gambling;

import java.util.ArrayList;
import java.util.Objects;

import com.arlania.world.content.gambling.gamble.GamblingGame;
import com.arlania.world.content.gambling.gamble.impl.ABCFlowersGamblingGame;
import com.arlania.world.content.gambling.gamble.impl.BlackJackGamblingGame;
import com.arlania.world.content.gambling.gamble.impl.DiceDuelGamblingGame;
import com.arlania.world.content.gambling.gamble.impl.FiftyFiveGamblingGame;
import com.arlania.world.content.gambling.gamble.impl.FlowerPokerGamblingGame;
import com.arlania.world.content.gambling.gamble.impl.FrostyFlowerGamblingGame;
import com.arlania.world.content.gambling.gamble.impl.HotOrColdGamblingGame;
import com.arlania.model.GameObject;
import com.arlania.model.Item;
import com.arlania.model.Locations;
import com.arlania.model.Locations.Location;
import com.arlania.model.input.impl.HandleGambleOfferInput;
import com.arlania.net.packet.PacketBuilder;
import com.arlania.net.packet.interaction.PacketInteraction;
import com.arlania.util.Misc;
import com.arlania.world.World;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.entity.impl.player.Player;

/**
 * 
 * Handles the GamblingManager
 *
 * @author 2012 <http://www.rune-server.org/members/dexter+morgan/>
 * 
 * 
 */
public class GamblingManager extends PacketInteraction {

	public static final int ABC_FLOWERS_ID = 1;
	public static final int BLACK_JACK_ID = 2;
	public static final int DICE_DUEL_ID = 3;
	public static final int FIFTY_FIVE_ID = 4;
	public static final int FLOWER_POKER_ID = 5;
	public static final int FROSTY_FLOWER_ID = 6;
	public static final int HOT_COLD_ID = 7;

	/**
	 * The gambling stages to a gamble
	 */
	public enum GambleStage {
		/**
		 * Not in a gamble stage
		 */
		OFFLINE,
		/**
		 * Offering to gamble another player
		 */
		SENDING_OFFER,
		/**
		 * Gamble has been accepted, placing a bet
		 */
		PLACING_BET,
		/**
		 * The bet has been confirmed. The gamble in progress
		 */
		IN_PROGRESS,
	}

	/**
	 * The game types
	 */
	public enum GameType {
		/**
		 * No game
		 */
		OFFLINE(-1, "", 0),
		/**
		 * Dice dueling
		 */
		DICE_DUEL(30895, "Duel your partner in\\n" + "a dice gamble. There\\n" + "are 3 rounds. Beat\\n"
				+ "your opponent by\\n" + "rolling a higher\\n" + "number than them.", 3),
		/**
		 * 55x2
		 */
		FIFTY_FIVE(30893, "A very quick game\\n" + "where the dice is\\n" + "rolled. If the number\\n"
				+ "is lower than 55\\n" + "then the opponent\\n" + "wins the game.", 1),
		/**
		 * Black jack
		 */
		BLACK_JACK(30896, "Hit and stand in\\n" + "this game trying to\\n" + "stay below 100 but\\n"
				+ "having a number\\n" + "that is larger than\\n" + "your opponents.", 4),
		/**
		 * Frosty flowers
		 */
		FROSTY_FLOWERS(30897, "A basic game where\\n" + "the aim is to get\\n" + "a flower that is\\n"
				+ "considered to be\\n" + "within the nature\\n" + "of being cold", 5),
		/**
		 * Flower poker
		 */
		FLOWER_POKER(30894, "Plant 5 flowers\\n" + "and use the rules\\n" + "of poker to play\\n"
				+ "and determine the\\n" + "winner of the game.\\n", 2),
		/**
		 * Abc flowers,
		 */
		ABC_FLOWERS(30898, "Plant 1 flower\\n" + "and use the first\\n" + "letter of the \\n" + "of the flower and\\n"
				+ "try to get close to A\\n", 6),
		/**
		 * Hot or cold
		 */
		HOT_OR_COLD(30899, "Host plants a flower.\\n" + "Pick either hot\\n" + "or cold. You'll win\\n"
				+ "whether the flower\\n" + " is hot or cold\\n", 7),
		/**
		 * Random
		 */
		RANDOM(30900, "Not sure what to\\n" + "play? Let a game be\\n" + "selected for you.\\n", 8);

		/**
		 * The button
		 */
		private int button;

		/**
		 * The description
		 */
		public String description;

		/**
		 * The config
		 */
		public int config;

		/**
		 * The game type
		 * 
		 * @param button
		 *            the button
		 */
		GameType(int button, String description, int config) {
			this.setButton(button);
			this.description = description;
			this.config = config;
		}

		/**
		 * Gets the button
		 * 
		 * @return the button
		 */
		public int getButton() {
			return button;
		}

		/**
		 * Sets the button
		 *
		 * @param button
		 *            the button
		 */
		public void setButton(int button) {
			this.button = button;
		}
	}

	/**
	 * The flowers
	 */
	public enum Flowers {
		/**
		 * The black flower
		 */
		BLACK(2988),
		/**
		 * The blue flower
		 */
		BLUE(2982),
		/**
		 * The orange flower
		 */
		ORANGE(2985),
		/**
		 * The pastel flower. Purple, blue, cyan
		 */
		PASTEL(2980),
		/**
		 * The purple flower
		 */
		PURPLE(2984),
		/**
		 * The rainbow flower. Red, yellow, blue
		 */
		RAINBOW(2986),
		/**
		 * The red flower
		 */
		RED(2981),
		/**
		 * The white flower
		 */
		WHITE(2987),
		/**
		 * The yellow
		 */
		YELLOW(2983);

		/**
		 * The id
		 */
		private int id;

		/**
		 * The id
		 * 
		 * @param id
		 *            the id
		 */
		Flowers(int id) {
			this.setId(id);
		}

		/**
		 * Gets the id
		 * 
		 * @return the id
		 */
		public int getId() {
			return id;
		}

		/**
		 * Sets the id
		 * 
		 * @param id
		 *            the id
		 */
		public void setId(int id) {
			this.id = id;
		}
	}

	/**
	 * The offer inventory interface
	 */
	public static final int OFFER_INVENTORY = 28351;

	/**
	 * The opponent inventory interface
	 */
	public static final int OPPONENT_INVENTORY = 28353;

	/**
	 * The flowers
	 */
	private ArrayList<Flowers> flowers = new ArrayList<Flowers>();

	/**
	 * The game flowers
	 */
	private ArrayList<GameObject> gameFlowers = new ArrayList<GameObject>();

	/**
	 * The gambling level
	 */
	private int level;

	/**
	 * The gambling experience
	 */
	private double experience;

	/**
	 * The game
	 */
	private GamblingGame game;

	/**
	 * The gambling stage
	 */
	private GambleStage stage = GambleStage.OFFLINE;

	/**
	 * No game type selected
	 */
	private GameType type = GameType.OFFLINE;

	/**
	 * The player we requested to gamble
	 */
	private Player requested;

	/**
	 * Whether the gamble has been fully confirmed
	 */
	private boolean confirmed;

	/**
	 * The game selection
	 */
	private int selection;

	/**
	 * Sends the invite
	 * 
	 * @param player
	 *            the player
	 * @param otherPlayer
	 *            the other player
	 */
	public static void sendInvitation(final Player player, Player otherPlayer) {
		Player requested = otherPlayer.getGambling().getRequested();
		if (!Objects.isNull(requested)) {
			GamblingManager.acceptInvitation(player, otherPlayer);
		} else {
			player.getPacketSender().sendMessage("You've sent a gamble invite to " + otherPlayer.getUsername() + ".");
			otherPlayer.getPacketSender().sendMessage(player.getUsername() + ":gamblereq:");
			player.getPacketSender().sendInterfaceRemoval();
			player.getGambling().setRequested(otherPlayer);
			player.getGambling().setStage(GambleStage.SENDING_OFFER);
		}
	}

	/**
	 * Opens the game selection screen
	 * 
	 * @param player
	 *            the player
	 * @param otherPlayer
	 *            the other player
	 */
	public static void acceptInvitation(final Player player, Player otherPlayer) {
		/**
		 * Can't gamble oneself
		 */
		if (player == otherPlayer || illegalGamble(player) || illegalGamble(otherPlayer)) {
			return;
		}
		/**
		 * Can't gamble
		 */
		if (!canGamble(player, GambleStage.OFFLINE) && !canGamble(otherPlayer, GambleStage.SENDING_OFFER)) {
			return;
		}
		/**
		 * Can't gamble
		 */
		if (!canGamble(otherPlayer, GambleStage.OFFLINE) && !canGamble(otherPlayer, GambleStage.SENDING_OFFER)) {
			return;
		}
		/**
		 * Not close enough
		 */
		if (!Locations.goodDistance(player.getPosition().getX(), player.getPosition().getY(),
				otherPlayer.getPosition().getX(), otherPlayer.getPosition().getY(), 2)) {
			player.getPacketSender().sendMessage("Please get closer to invite to gamble.");
			return;
		}
		/**
		 * Player sending invite
		 */
		player.getGambling().setStage(GambleStage.SENDING_OFFER);
		/**
		 * Sets requested
		 */
		player.getGambling().setRequested(otherPlayer);
		player.getPacketSender().sendString(30912, otherPlayer.getUsername());
		otherPlayer.getPacketSender().sendString(30912, player.getUsername());

		/**
		 * The other player has a requested
		 */
		if (otherPlayer.getGambling().getRequested() != null) {
			/**
			 * We are the requested one
			 */
			if (player.getIndex() == otherPlayer.getGambling().getRequested().getIndex()) {
				/**
				 * Opens game selection screen
				 */
				openOfferScreen(otherPlayer, player);
				openOfferScreen(player, otherPlayer);
			}
		}
	}

	/**
	 * Accepting the game selection
	 * 
	 * @param player
	 *            the player
	 */
	private static void acceptGameSelection(Player player) {
		/**
		 * No game selected
		 */
		if (player.getGambling().getType() == null) {
			player.getPacketSender().sendMessage("You need to select a game first.");
			return;
		}
		/**
		 * The requested player
		 */
		Player otherPlayer = player.getGambling().getRequested();
		/**
		 * Other player is null
		 */
		if (otherPlayer == null) {
			return;
		}
		/**
		 * Already confirmed
		 */
		if (player.getGambling().isConfirmed()) {
			return;
		}
		/**
		 * Confirms selection
		 */
		player.getGambling().setConfirmed(true);
		otherPlayer.getPacketSender().sendString(30910, player.getUsername() + " has accepted.");
		player.getPacketSender().sendString(30910, "Waiting for other player\\nto accept.");
		player.getPacketSender().sendMessage("You have accepted the gamble.");
		otherPlayer.getPacketSender().sendMessage(player.getUsername() + " has accepted the gamble.");
		/**
		 * No game selected
		 */
		if (player.getGambling().getType() != otherPlayer.getGambling().getType()) {
			return;
		}
		/**
		 * Other player confirmed
		 */
		if (otherPlayer.getGambling().isConfirmed()) {
			/**
			 * The game the other player is offering
			 */
			GamblingGame game = getGame(otherPlayer, player, otherPlayer.getGambling().getType());
			/**
			 * Sets the game
			 */
			player.getGambling().setGame(game);
			otherPlayer.getGambling().setGame(game);
			/**
			 * Sets stage for us
			 */
			player.getGambling().setStage(GambleStage.SENDING_OFFER);
			/**
			 * Sets requested
			 */
			player.getGambling().setRequested(otherPlayer);
			/**
			 * Opens the offer screen
			 */
			startGamble(player);
		}
	}

	/**
	 * Opens the offer screen
	 * 
	 * @param player
	 *            the player
	 * @param otherPlayer
	 *            the other player
	 */
	public static void openOfferScreen(final Player player, Player otherPlayer) {
		/**
		 * Sets the stage
		 */
		player.getGambling().setConfirmed(false);
		player.getGambling().setStage(GambleStage.PLACING_BET);
		/**
		 * Displays items
		 */
		player.getGambleOffer().refreshItems();
		player.getPacketSender().sendItemsOnInterface(OFFER_INVENTORY, player.getGambleOffer().getValidItemsArray());
		player.getPacketSender().sendItemsOnInterface(OPPONENT_INVENTORY,
				otherPlayer.getGambleOffer().getValidItemsArray());
		/**
		 * Sets up interface test
		 */
		player.getPacketSender().sendString(28347, player.getUsername());
		player.getPacketSender().sendString(28348, otherPlayer.getUsername());
		player.getPacketSender().sendString(28349, "");
		player.getPacketSender().sendConfig(1107, 0);
		player.getPacketSender().sendString(30910, "");
		player.getPacketSender().setScrollBar(28350, 225);
		player.getPacketSender().setScrollBar(28352, 225);
		/**
		 * Interface
		 */
		player.getPacketSender().sendInterfaceSet(30891, 3321);
		player.getPacketSender().sendItemContainer(player.getInventory(), 3322);
	}

	/**
	 * Starts the gamble
	 * 
	 * @param player
	 *            the host
	 */
	public static void startGamble(Player player) {
		/**
		 * Can't gamble
		 */
		if (!canGamble(player, GambleStage.SENDING_OFFER)) {
			return;
		}
		/**
		 * The requested player
		 */
		Player otherPlayer = player.getGambling().getRequested();
		/**
		 * Other player is null
		 */
		if (otherPlayer == null) {
			return;
		}
		/**
		 * Confirmed
		 */
		if (otherPlayer.getGambling().isConfirmed()) {
			/**
			 * No game available
			 */
			if (player.getGambling().getGame() == null) {
				return;
			}
			/**
			 * No host
			 */
			if (player.getGambling().getGame().getHost() == null) {
				return;
			}
			/**
			 * Host doesn't have a game
			 */
			if (player.getGambling().getGame().getHost().getGambling().getGame() == null) {
				return;
			}
			/**
			 * Removes interfaces
			 */
			player.getSession().queueMessage(new PacketBuilder(219));
			otherPlayer.getSession().queueMessage(new PacketBuilder(219));
			/**
			 * Sets the stage
			 */
			player.getGambling().setStage(GambleStage.IN_PROGRESS);
			otherPlayer.getGambling().setStage(GambleStage.IN_PROGRESS);
			/**
			 * Movement locked
			 */
			player.getMovementQueue().setLockMovement(true);
			otherPlayer.getMovementQueue().setLockMovement(true);
			/**
			 * The gamble
			 */
			player.getGambling().getGame().gamble();
		}
	}

	/**
	 * Player disconnected
	 * 
	 * @param player
	 *            the host
	 */
	public static void autoDisconnectWin(Player player) {
		/**
		 * No game available
		 */
		if (player.getGambling().getGame() == null) {
			return;
		}
		/**
		 * No host
		 */
		if (player.getGambling().getGame().getHost() == null) {
			return;
		}
		/**
		 * Host doesn't have a game
		 */
		if (player.getGambling().getGame().getHost().getGambling().getGame() == null) {
			return;
		}
		/**
		 * The requested player
		 */
		Player otherPlayer = player.getGambling().getRequested();
		/**
		 * Other player is null
		 */
		if (otherPlayer == null) {
			return;
		}
		/**
		 * Ends gamble
		 */
		finishGamble(-1, player, otherPlayer, 0, 1);
	}

	/**
	 * Finishing the gamble
	 * 
	 * @param host
	 *            the host
	 * @param opponent
	 *            the opponent
	 */
	public static void finishGamble(int gameId, Player host, Player opponent, int hostScore, int opponentScore) {
		/**
		 * No game available
		 */
		if (host.getGambling().getGame() == null) {
			return;
		}
		/**
		 * No host
		 */
		if (host.getGambling().getGame().getHost() == null) {
			return;
		}
		/**
		 * Host doesn't have a game
		 */
		if (host.getGambling().getGame().getHost().getGambling().getGame() == null) {
			return;
		}
		/**
		 * Opponent invalid
		 */
		if (!World.getPlayers().contains(opponent) && World.getPlayers().contains(host)) {
			setWinnings(gameId, 'H', host, opponent, false);
		} else if (World.getPlayers().contains(opponent) && !World.getPlayers().contains(host)) {
			setWinnings(gameId, 'O', opponent, host, false);
		} else {
			/**
			 * Winning
			 */
			boolean hostWon = hostScore > opponentScore;
			setWinnings(gameId, hostWon ? 'H' : 'O', hostWon ? host : opponent, hostWon ? opponent : host,
					hostScore == opponentScore);
		}
		/**
		 * Resets host
		 */
		if (host != null) {
			resetGamble(host);
		}
		/**
		 * Resets opponent
		 */
		if (opponent != null) {
			resetGamble(opponent);
		}
	}

	private static void sendGameOverMessages(int gameId, char winnerIdentifier, Player winner, Player loser) {
		switch (gameId) {

		case DICE_DUEL_ID:
			winner.forceChat("I have won the dice duel!");
			loser.forceChat("I have lost the dice duel.");
			break;

		case FIFTY_FIVE_ID:
			if (winnerIdentifier == 'H') {
				// host won
				winner.forceChat("The roll was under 55, I have won the 55x2.");
			} else {
				winner.forceChat("The roll was 55 or higher, I have won the 55x2.");
			}
			break;

		case -1:
			winner.forceChat("I have won!");
			break;

		default:
			winner.forceChat("I have won!");
			loser.forceChat("I have lost!");
			break;
		}
	}

	/**
	 * Sets the winning
	 * 
	 * @param winner
	 *            the winner
	 * @param loser
	 *            the loser
	 */
	private static void setWinnings(int gameId, char winnerIdentifier, Player winner, Player loser, boolean draw) {
		/**
		 * Draw
		 */
		if (draw) {
			/**
			 * Player
			 */
			if (winner != null) {
				winner.getInventory().add(winner.getGambleOffer().getItems());
				winner.getPacketSender().sendMessage("It's a draw!");
				winner.forceChat("It's a draw!");
			}
			/**
			 * Opponent
			 */
			if (loser != null) {
				loser.getInventory().add(loser.getGambleOffer().getItems());
				loser.getPacketSender().sendMessage("It's a draw!");
				loser.forceChat("It's a draw!");
			}
		} else {
			/**
			 * Returns back items
			 */
			if (winner != null) {
				winner.getInventory().add(winner.getGambleOffer().getItems());
				if (winner.getGambleOffer().getValidItems().size()
						+ loser.getGambleOffer().getValidItems().size() > 28) {
					winner.getBank(0).add(loser.getGambleOffer().getItems());
					winner.sendMessage("The remainder of your winnings have been sent to your bank.");
				} else {
					winner.getInventory().add(loser.getGambleOffer().getItems());
				}
				/**
				 * Player won
				 */
				sendGameOverMessages(gameId, winnerIdentifier, winner, loser);
			}
		}
		/**
		 * Resets loser
		 */
		if (loser != null) {
			loser.getGambleOffer().resetItems();
		}
		/**
		 * Resets winner
		 */
		if (winner != null) {
			winner.getGambleOffer().resetItems();
		}
	}

	/**
	 * Selects a game
	 * 
	 * @param player
	 *            the player
	 * @param type
	 *            the game
	 */
	public static void selectGameType(Player player, GameType type) {
		/**
		 * The requested player
		 */
		Player otherPlayer = player.getGambling().getRequested();
		/**
		 * Other player is null
		 */
		if (otherPlayer == null) {
			return;
		}
		/**
		 * Selects game type
		 */
		player.getGambling().setConfirmed(false);
		player.getGambling().setType(type);
		player.getPacketSender().sendString(30910, type.description);

		otherPlayer.getGambling().setConfirmed(false);
		otherPlayer.getGambling().setType(type);
		otherPlayer.getPacketSender().sendString(30910, type.description);
		otherPlayer.getPacketSender().sendConfig(1107, type.config);
	}

	/**
	 * Resets the gambling
	 * 
	 * @param player
	 *            the player
	 */
	public static void resetGamble(final Player player) {

		/**
		 * Not in gamble
		 */
		if (player.getGambling().getStage().equals(GambleStage.OFFLINE)) {
			return;
		}
		/**
		 * Gamble settings
		 */
		player.getGambling().setConfirmed(false);
		player.getGambling().setGame(null);
		player.getGambling().setType(null);
		player.getGambling().setStage(GambleStage.OFFLINE);
		player.getGambling().setSelection(-1);
		player.setDialogueActionId(-1);
		player.setInterfaceId(-1);
		player.getSession().queueMessage(new PacketBuilder(219));
		player.getPacketSender().sendConfig(1107, 0);
		player.getPacketSender().sendString(30910, "");
		removeFlowers(player);
		/**
		 * Movement unlocked
		 */
		player.getMovementQueue().setLockMovement(false);
		/**
		 * Resets other player
		 */
		if (player.getGambling().getRequested() != null) {
			resetGamble(player.getGambling().getRequested());
		}
		/**
		 * Resets request
		 */
		player.getGambling().setRequested(null);
	}

	/**
	 * Cancelling gambling session
	 * 
	 * @param player
	 *            the player
	 */
	private static void cancelGamble(Player player) {
		player.getInventory().add(player.getGambleOffer().getItems());
		player.getGambleOffer().resetItems();
		/**
		 * The requested player
		 */
		Player otherPlayer = player.getGambling().getRequested();
		/**
		 * Other player is null
		 */
		if (otherPlayer != null) {
			/**
			 * Cancels gamble
			 */
			otherPlayer.getInventory().add(otherPlayer.getGambleOffer().getItems());
			otherPlayer.getGambleOffer().resetItems();
			resetGamble(otherPlayer);
		}
		/**
		 * Resets gamble
		 */
		resetGamble(player);
	}

	/**
	 * Checking whether a player can gamble
	 * 
	 * @param player
	 *            the player
	 * @param requiredStage
	 *            the required stage
	 * @return whether can gamble
	 */
	private static boolean canGamble(final Player player, GambleStage requiredStage) {
		/**
		 * Invalid player
		 */
		if (player == null) {
			return false;
		}
		/**
		 * Stage exists
		 */
		if (Objects.nonNull(requiredStage)) {
			/**
			 * Wrong stage
			 */
			if (!player.getGambling().getStage().equals(requiredStage)) {
				return false;
			}
		}
		/**
		 * Player is busy
		 */
		if (player.getConstitution() <= 0 || player.isTeleporting()) {
			return false;
		}
		/**
		 * Not in area
		 */
		if (player.getLocation() != Location.GAMBLING_ZONE) {
			return false;
		}
		/**
		 * Decline trade
		 */
		if (player.getTrading().inTrade()) {
			player.getTrading().declineTrade(true);
			return false;
		}
		/**
		 * Remove interfaces
		 */
		if (player.isShopping() || player.isBanking()) {
			player.getPacketSender().sendInterfaceRemoval();
			return false;
		}
		return true;
	}

	/**
	 * Checks whether more than 2 players are involved
	 * 
	 * @param player
	 *            the player
	 * @return the players involved
	 */
	private static boolean illegalGamble(Player player) {

		int amount = 0;
		for (Player players : World.getPlayers()) {
			if (players == null) {
				continue;
			}

			if (players.getGambling().getRequested() == player
					&& !players.getGambling().getStage().equals(GambleStage.OFFLINE)) {
				amount++;
			}
		}
		return amount > 2;
	}

	/**
	 * Handles the gamble offer
	 * 
	 * @param player
	 *            the player
	 * @param interfaceId
	 *            the interface id
	 * @param item
	 *            the item
	 * @param slot
	 *            the slot
	 * @return the interface id
	 */
	public static boolean handleGambleOffer(Player player, int interfaceId, Item item, int slot) {
		Player otherPlayer = player.getGambling().getRequested();
		switch (interfaceId) {
		case OFFER_INVENTORY:
			player.getGambling().setConfirmed(false);
			player.getPacketSender().sendString(28349, "");
			if (player.getGambling().getRequested() != null) {
				player.getGambling().getRequested().getPacketSender().sendString(28349, "");
			}
			if (otherPlayer != null) {
				otherPlayer.getPacketSender().sendMessage(":modifiedSlot:_" + slot + "_" + OPPONENT_INVENTORY);
				player.getPacketSender().sendMessage(":modifiedSlot:_" + slot + "_" + OFFER_INVENTORY);
			}
			player.getGambleOffer().switchItem(player.getInventory(), item, slot, false, true);
			if (player.getGambleOffer().getValidItems().size() >= 15) {
				player.getPacketSender().setScrollBar(28350, player.getGambleOffer().getValidItems().size() * 14);
			}
			if (otherPlayer != null) {
				if (otherPlayer.getGambleOffer().getValidItems().size() >= 15) {
					otherPlayer.getPacketSender().setScrollBar(28352,
							player.getGambleOffer().getValidItems().size() * 14);
				}
			}
			return true;
		case 3322:
			if (player.getLocation() == Location.GAMBLING_ZONE && !player.getTrading().inTrade()) {
				player.getGambling().setConfirmed(false);
				player.getPacketSender().sendString(28349, "");
				if (player.getGambling().getRequested() != null) {
					player.getGambling().getRequested().getPacketSender().sendString(28349, "");
				}
				player.getInventory().switchItem(player.getGambleOffer(), item, slot, false, true);

				if (player.getGambleOffer().getValidItems().size() >= 15) {
					player.getPacketSender().setScrollBar(28350, player.getGambleOffer().getValidItems().size() * 14);
				}
				/**
				 * Other player is null
				 */
				if (otherPlayer != null) {
					if (otherPlayer.getGambleOffer().getValidItems().size() >= 15) {
						otherPlayer.getPacketSender().setScrollBar(28352,
								player.getGambleOffer().getValidItems().size() * 14);
					}
				}
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Selects the option
	 * 
	 * @param player
	 *            the player
	 * @param option
	 *            the option
	 */
	private static void selectOption(Player player, int option) {
		/**
		 * No game available
		 */
		if (player.getGambling().getGame() == null) {
			return;
		}
		/**
		 * No host
		 */
		if (player.getGambling().getGame().getHost() == null) {
			return;
		}
		/**
		 * Host doesn't have a game
		 */
		if (player.getGambling().getGame().getHost().getGambling().getGame() == null) {
			return;
		}
		/**
		 * Sets the selection
		 */
		player.getGambling().setSelection(option);
		/**
		 * Black jack
		 */
		if (player.getGambling().getGame().getHost().getGambling().getGame() instanceof BlackJackGamblingGame) {
			/**
			 * Second option
			 */
			if (option == 2) {
				player.getGambling().getGame().getHost().getGambling().getGame().setHostTurn(true);
				player.getGambling().getGame().setHostTurn(true);
				if (player.getGambling().getGame().getHost() != player) {
					player.forceChat("Stay");
				}
			} else {
				if (player.getGambling().getGame().getHost() != player) {
					player.forceChat("Hit");
				}
			}
		}
	}

	/**
	 * Removes the flowers
	 * 
	 * @param player
	 *            the player
	 */
	private static void removeFlowers(Player player) {

		/**
		 * Loops through the game flowers
		 */
		for (GameObject gameFlower : player.getGambling().getGameFlowers()) {
			/**
			 * The flower to remove
			 */
			GameObject remove = new GameObject(-1, gameFlower.getPosition().copy());
			/**
			 * Spawns flower
			 */
			CustomObjects.spawnObject(player, remove);
			/**
			 * Sends to other player
			 */
			/**
			 * The requested player
			 */
			Player otherPlayer = player.getGambling().getRequested();
			/**
			 * Other player is null
			 */
			if (otherPlayer != null) {
				CustomObjects.spawnObject(otherPlayer, remove);
			}
		}
	}

	/**
	 * Gets the game
	 * 
	 * @param player
	 *            the player
	 * @param otherPlayer
	 *            the other player
	 * @param type
	 *            the type
	 * @return the game
	 */
	public static GamblingGame getGame(Player player, Player otherPlayer, GameType type) {
		switch (type) {
		case ABC_FLOWERS:
			return new ABCFlowersGamblingGame(player, otherPlayer);
		case BLACK_JACK:
			return new BlackJackGamblingGame(player, otherPlayer);
		case DICE_DUEL:
			return new DiceDuelGamblingGame(player, otherPlayer);
		case FIFTY_FIVE:
			return new FiftyFiveGamblingGame(player, otherPlayer);
		case FLOWER_POKER:
			return new FlowerPokerGamblingGame(player, otherPlayer);
		case FROSTY_FLOWERS:
			return new FrostyFlowerGamblingGame(player, otherPlayer);
		case HOT_OR_COLD:
			return new HotOrColdGamblingGame(player, otherPlayer);
		case RANDOM:
			GamblingGame game = getGame(player, otherPlayer,
					GameType.values()[Misc.getRandom(GameType.values().length - 1)]);
			player.sendMessage("You are playing " + game.toString() + "!");
			otherPlayer.sendMessage("You are playing " + game.toString() + "!");
			return game;
		default:
			break;
		}
		return null;
	}

	/**
	 * Gets the flowers
	 * 
	 * @return the flowers
	 */
	public ArrayList<Flowers> getFlowers() {
		return flowers;
	}

	/**
	 * Sets the flowers
	 *
	 * @param flowers
	 *            the flowers
	 */
	public void setFlowers(ArrayList<Flowers> flowers) {
		this.flowers = flowers;
	}

	/**
	 * @return the gameFlowers
	 */
	public ArrayList<GameObject> getGameFlowers() {
		return gameFlowers;
	}

	/**
	 * @param gameFlowers
	 *            the gameFlowers to set
	 */
	public void setGameFlowers(ArrayList<GameObject> gameFlowers) {
		this.gameFlowers = gameFlowers;
	}

	/**
	 * Gets the level
	 *
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Sets the level
	 * 
	 * @param level
	 *            the level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Gets the experience
	 *
	 * @return the experience
	 */
	public double getExperience() {
		return experience;
	}

	/**
	 * Sets the experience
	 * 
	 * @param experience
	 *            the experience
	 */
	public void setExperience(double experience) {
		this.experience = experience;
	}

	/**
	 * Gets the game
	 *
	 * @return the game
	 */
	public GamblingGame getGame() {
		return game;
	}

	/**
	 * Sets the game
	 * 
	 * @param game
	 *            the game
	 */
	public void setGame(GamblingGame game) {
		this.game = game;
	}

	/**
	 * Gets the stage
	 *
	 * @return the stage
	 */
	public GambleStage getStage() {
		return stage;
	}

	/**
	 * Sets the stage
	 * 
	 * @param stage
	 *            the stage
	 */
	public void setStage(GambleStage stage) {
		this.stage = stage;
	}

	/**
	 * Gets the type
	 * 
	 * @return the type
	 */
	public GameType getType() {
		return type;
	}

	/**
	 * Sets the type
	 *
	 * @param type
	 *            the type
	 */
	public void setType(GameType type) {
		this.type = type;
	}

	/**
	 * Gets the requested
	 *
	 * @return the requested
	 */
	public Player getRequested() {
		return requested;
	}

	/**
	 * Sets the requested
	 * 
	 * @param requested
	 *            the requested
	 */
	public void setRequested(Player requested) {
		this.requested = requested;
	}

	@Override
	public boolean handleButtonInteraction(final Player player, final int button) {
		/**
		 * Selecting game type
		 */
		for (GameType type : GameType.values()) {
			if (type.getButton() == button) {
				selectGameType(player, type);
				return true;
			}
		}
		/**
		 * Clicking buttons
		 */
		switch (button) {
		case 30914:
			acceptGameSelection(player);
			return true;
		case 28356:
			startGamble(player);
			return true;
		case 28393:
			selectOption(player, 2);
			return true;
		case 28394:
			selectOption(player, 1);
			return true;
		case 28389:
			resetGamble(player);
			return true;
		case 30915:
			cancelGamble(player);
			return true;
		}
		return false;
	}

	@Override
	public boolean handleItemContainerInteraction(Player player, int interfaceId, int slot, Item item, int type) {
		/**
		 * The amount
		 */
		int amount = 1;
		/**
		 * The types
		 */
		if (type == 2) {
			amount = 5;
		} else if (type == 3) {
			amount = 10;
		} else if (type == 4) {
			amount = Integer.MAX_VALUE;
		} else if (type == 5) {

			switch (interfaceId) {
			case OFFER_INVENTORY:
				player.setInputHandling(new HandleGambleOfferInput(item.getId(), slot, interfaceId));
				player.getPacketSender().sendEnterAmountPrompt("How many would you like to remove?");
				return true;
			case 3322:
				if (!player.getTrading().inTrade()) {
					player.setInputHandling(new HandleGambleOfferInput(item.getId(), slot, interfaceId));
					player.getPacketSender().sendEnterAmountPrompt("How many would you like to add?");
				}
				return true;
			}
		}
		/**
		 * Sets amount
		 */
		item.setAmount(amount);
		/**
		 * Handles offer
		 */
		return handleGambleOffer(player, interfaceId, item, slot);
	}

	/**
	 * Gets the confirmed
	 * 
	 * @return the confirmed
	 */
	public boolean isConfirmed() {
		return confirmed;
	}

	/**
	 * Sets the confirmed
	 *
	 * @param confirmed
	 *            the confirmed
	 */
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	/**
	 * @return the selection
	 */
	public int getSelection() {
		return selection;
	}

	/**
	 * @param selection
	 *            the selection to set
	 */
	public void setSelection(int selection) {
		this.selection = selection;
	}
}