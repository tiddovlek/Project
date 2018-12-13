package com.arlania.world.content.gambling.gamble.impl;

import com.arlania.world.content.gambling.GamblingManager;
import com.arlania.world.content.gambling.gamble.GamblingGame;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Graphic;
import com.arlania.util.Misc;
import com.arlania.world.entity.impl.player.Player;

/**
 * 
 * Handles the dice duel gamble game
 *
 * @author 2012 <http://www.rune-server.org/members/dexter+morgan/>
 *
 */
public class DiceDuelGamblingGame extends GamblingGame {

	/**
	 * The dice duel gamble game
	 * 
	 * @param opponent
	 *            the host player
	 * @param opponent
	 *            the opponent player
	 */
	public DiceDuelGamblingGame(Player host, Player opponent) {
		super(host, opponent);
	}

	@Override
	public String toString() {
		return "Dice Duel";
	}

	@Override
	public void gamble() {
		TaskManager.submit(new Task(1) {

			int time = 0;
			int hostScore = 0;
			int opponentScore = 0;

			@Override
			public void execute() {

				switch (time) {
				case 1:
					getHost().performAnimation(new Animation(11900));
					getHost().performGraphic(new Graphic(2075, 10));
					hostScore = Misc.getRandom(100);

					getOpponent().performAnimation(new Animation(11900));
					getOpponent().performGraphic(new Graphic(2075, 10));
					opponentScore = Misc.getRandom(100);
					break;
				case 4:
					getHost().forceChat("I rolled " + hostScore + " on the dice.");
					getOpponent().sendMessage("Your opponent rolled " + hostScore + " on the dice.");
					getOpponent().forceChat("I rolled " + opponentScore + " on the dice. ");
					getHost().sendMessage("Your opponent rolled " + opponentScore + " on the dice.");
					break;

				case 7:
					getHost().sendMessage("Opponent score: " + opponentScore);
					getOpponent().sendMessage("Opponent score: " + hostScore);
					if (hostScore > opponentScore) {
						setHostScore(getHostScore() + 1);
						getHost().sendMessage("I have won that round, the total score: you: " + getHostScore()
								+ ", opponent: " + getOpponentScore());
						getOpponent().sendMessage("I have lost that round, the total score: opponent: " + getHostScore()
								+ ", you: " + getOpponentScore());
					} else if (opponentScore > hostScore) {
						setOpponentScore(getOpponentScore() + 1);
						getHost().sendMessage("I have lost that round, the total score: you: " + getHostScore()
								+ ", opponent: " + getOpponentScore());
						getOpponent().sendMessage("I have won that round, the total score: opponent: " + getHostScore()
								+ ", you: " + getOpponentScore());
					}
					break;

				case 8:
					if (getHostScore() == 3 || getOpponentScore() == 3) {
						GamblingManager.finishGamble(GamblingManager.DICE_DUEL_ID, getHost(), getOpponent(),
								getHostScore(), getOpponentScore());
						this.stop();
					} else {
						time = 0;
					}
					break;
				}
				time++;
			}
		});
	}
}