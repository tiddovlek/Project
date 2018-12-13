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
 * Handles the 55 x 2 gamble game
 *
 * @author 2012 <http://www.rune-server.org/members/dexter+morgan/>
 *
 */
public class FiftyFiveGamblingGame extends GamblingGame {

	/**
	 * The fifty five gambling game
	 * 
	 * @param host
	 *            the host
	 * @param opponent
	 *            the opponent
	 */
	public FiftyFiveGamblingGame(Player host, Player opponent) {
		super(host, opponent);
	}

	@Override
	public String toString() {
		return "55x2";
	}

	@Override
	public void gamble() {

		TaskManager.submit(new Task(1) {

			/**
			 * The time
			 */
			int time = 0;

			/**
			 * The random number
			 */
			int random = Misc.getRandom(100);

			@Override
			public void execute() {

				switch (time) {
				case 2:
					getHost().performAnimation(new Animation(11900));
					getHost().performGraphic(new Graphic(2075, 10));
					break;
				case 4:
					getHost().forceChat("I rolled " + random + " on the dice.");
					getOpponent().sendMessage("The host rolled " + random + " on the dice.");
					break;

				case 8:
					GamblingManager.finishGamble(GamblingManager.FIFTY_FIVE_ID, getHost(), getOpponent(),
							random > 55 ? 0 : 1, random > 55 ? 1 : 0);
					this.stop();
					break;
				}
				time++;
			}
		});
	}
}
