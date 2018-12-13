package com.arlania.world.content.gambling.gamble.impl;

import com.arlania.world.content.gambling.GamblingManager;
import com.arlania.world.content.gambling.GamblingManager.Flowers;
import com.arlania.world.content.gambling.gamble.GamblingGame;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.GameObject;
import com.arlania.util.Misc;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.entity.impl.player.Player;

/**
 * 
 * Handles the frosty flower gamble game
 *
 * @author 2012 <http://www.rune-server.org/members/dexter+morgan/>
 *
 */
public class FrostyFlowerGamblingGame extends GamblingGame {

	/**
	 * The frosty flower gambling game
	 * 
	 * @param host
	 *            the host
	 * @param opponent
	 *            the opponent
	 */
	public FrostyFlowerGamblingGame(Player host, Player opponent) {
		super(host, opponent);
	}

	@Override
	public String toString() {
		return "Frosty Flowers";
	}

	@Override
	public void gamble() {
		TaskManager.submit(new Task(1) {

			/**
			 * The time
			 */
			int time = 0;

			/**
			 * The random flower
			 */
			Flowers flower = Flowers.values()[Misc.getRandom(Flowers.values().length - 1)];
			/**
			 * Whether replaying
			 */
			boolean replay = false;

			@Override
			public void execute() {
				switch (time) {
				case 1:
					getHost().performAnimation(new Animation(827));
					break;

				case 3:
					/**
					 * Host wins by default
					 */
					boolean hostWon = true;
					/**
					 * Checks the flowers
					 */
					switch (flower) {
					case PURPLE:
					case PASTEL:
					case BLUE:
						hostWon = false;
						break;
					case WHITE:
					case BLACK:
						flower = Flowers.values()[Misc.getRandom(Flowers.values().length - 1)];
						replay = true;
						time = 0;
						break;
					default:
						break;
					}
					/**
					 * The flower object
					 */
					final GameObject flowerObject = new GameObject(flower.getId(), getHost().getPosition().copy());
					/**
					 * Spawns the object
					 */
					CustomObjects.spawnObject(getHost(), flowerObject);
					CustomObjects.spawnObject(getOpponent(), flowerObject);
					getHost().getGambling().getGameFlowers().add(flowerObject);
					getHost().getMovementQueue().setLockMovement(false);
					getHost().getMovementQueue().walkStep(0, -1);
					/**
					 * No replay, end the game
					 */
					if (!replay) {
						final boolean hostWins = hostWon;
						TaskManager.submit(new Task(5, false) {

							@Override
							protected void execute() {
								GamblingManager.finishGamble(GamblingManager.FROSTY_FLOWER_ID, getHost(), getOpponent(),
										hostWins ? 1 : 0, hostWins ? 0 : 1);
								this.stop();
							}
						});
						this.stop();
					}
					replay = false;
					break;
				}
				time++;
			}
		});
	}
}
