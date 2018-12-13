package com.arlania.world.content.gambling.gamble.impl;

import com.arlania.world.content.gambling.GamblingManager;
import com.arlania.world.content.gambling.gamble.GamblingGame;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Graphic;
import com.arlania.net.packet.PacketBuilder;
import com.arlania.util.Misc;
import com.arlania.world.entity.impl.player.Player;

/**
 * 
 * Handles the black jack gamble game
 *
 * @author 2012 <http://www.rune-server.org/members/dexter+morgan/>
 *
 */
public class BlackJackGamblingGame extends GamblingGame {

	/**
	 * The black jack gambling game
	 * 
	 * @param host
	 *            the host
	 * @param opponent
	 *            the opponent
	 */
	public BlackJackGamblingGame(Player host, Player opponent) {
		super(host, opponent);
	}

	@Override
	public String toString() {
		return "Blackjack";
	}

	@Override
	public void gamble() {

		getHost().getGambling().setSelection(-1);
		getOpponent().getGambling().setSelection(-1);

		TaskManager.submit(new Task(1) {

			/**
			 * The time
			 */
			int time = 0;

			/**
			 * The selection
			 */
			int selection = -1;

			/**
			 * The host score
			 */
			int hostScore = 0;
			/**
			 * The opponent score
			 */
			int opponentScore = 0;

			@Override
			public void execute() {

				switch (time) {
				case 0:
					if (!isHostTurn()) {
						if (opponentScore == 0) {
							selection = 1;
						} else {
							getOpponent().getPacketSender().sendInterface(28392);
							getHost().getSession().queueMessage(new PacketBuilder(219));
							selection = getOpponent().getGambling().getSelection();
						}
					} else if (isHostTurn()) {
						if (hostScore == 0) {
							selection = 1;
						} else {
							getHost().getPacketSender().sendInterface(28392);
							getOpponent().getSession().queueMessage(new PacketBuilder(219));
							selection = getHost().getGambling().getSelection();
						}
					}
					break;
				case 1:
					if (selection == 1) {
						getHost().performAnimation(new Animation(11900));
						getHost().performGraphic(new Graphic(2075, 10));
					}
					break;

				case 3:
					if (selection == 1) {
						if (!isHostTurn()) {
							int roll = 1 + Misc.getRandom(99);
							opponentScore += roll;
							getHost().forceChat(
									"I rolled " + roll + " on the dice, your total score is " + opponentScore + ".");
							getOpponent().sendMessage("Rolled: @red@" + roll + ". @bla@Total score: " + opponentScore);
						} else if (isHostTurn()) {
							int roll = 1 + Misc.getRandom(99);
							hostScore += roll;
							getHost().forceChat(
									"I rolled " + roll + " on the dice, my total score is " + hostScore + ".");
						}
					}
					break;

				case 5:
					/**
					 * Hosts turn
					 */
					if (!isHostTurn()) {
						if (opponentScore > 99) {
							GamblingManager.finishGamble(GamblingManager.BLACK_JACK_ID, getHost(), getOpponent(), 1, 0);
							this.stop();
							return;
						}
						if (selection == 2) {
							setHostTurn(true);
							selection = -1;
						}
					} else
					/**
					 * Finished
					 */
					if (isHostTurn()) {
						/**
						 * Opponent wins
						 */
						if (hostScore > opponentScore && hostScore > 99) {
							GamblingManager.finishGamble(GamblingManager.BLACK_JACK_ID, getHost(), getOpponent(), 0, 1);
							this.stop();
						} else
						/**
						 * Host wins
						 */
						if (opponentScore > hostScore && opponentScore > 99) {
							GamblingManager.finishGamble(GamblingManager.BLACK_JACK_ID, getHost(), getOpponent(), 1, 0);
							this.stop();
						} else
						/**
						 * End game
						 */
						if (selection == 2) {
							GamblingManager.finishGamble(GamblingManager.BLACK_JACK_ID, getHost(), getOpponent(),
									hostScore, opponentScore);
							this.stop();
							return;
						}
					}

					/**
					 * Resets
					 */
					getHost().getGambling().setSelection(-1);
					getOpponent().getGambling().setSelection(-1);
					selection = -1;
					time = 0;
					break;
				}
				/**
				 * No selection
				 */
				if (selection != -1) {
					time++;
				}
			}
		});
	}
}
