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
 * Handles the abc flowers gamble game
 *
 * @author 2012 <http://www.rune-server.org/members/dexter+morgan/>
 *
 */
public class ABCFlowersGamblingGame extends GamblingGame {

	/**
	 * @param host
	 *            the host
	 * @param opponent
	 *            the opponent
	 */
	public ABCFlowersGamblingGame(Player host, Player opponent) {
		super(host, opponent);
	}

	@Override
	public String toString() {
		return "ABC Flowers";
	}

	@Override
	public void gamble() {
		/**
		 * The host flower
		 */
		final Flowers hostResult = Flowers.values()[Misc.getRandom(Flowers.values().length - 1)];
		/**
		 * The opponent flower
		 */
		final Flowers opponentResult = Flowers.values()[Misc.getRandom(Flowers.values().length - 1)];
		/**
		 * The host flower
		 */
		final GameObject hostFlower = new GameObject(hostResult.getId(), getHost().getPosition().copy());
		/**
		 * The opponent flower
		 */
		final GameObject opponentFlower = new GameObject(opponentResult.getId(), getOpponent().getPosition().copy());
		/**
		 * End task
		 */
		TaskManager.submit(new Task(1, false) {

			int time = 0;

			@Override
			protected void execute() {
				switch (time) {
				case 1:
					/**
					 * Plants it
					 */
					getHost().performAnimation(new Animation(827));
					getOpponent().performAnimation(new Animation(827));
					/**
					 * Spawns opponent flower
					 */
					CustomObjects.spawnObject(getHost(), opponentFlower);
					CustomObjects.spawnObject(getOpponent(), opponentFlower);
					getOpponent().getGambling().getGameFlowers().add(opponentFlower);
					/**
					 * Spawns host flower
					 */
					CustomObjects.spawnObject(getHost(), hostFlower);
					CustomObjects.spawnObject(getOpponent(), hostFlower);
					getHost().getGambling().getGameFlowers().add(hostFlower);
					getHost().getMovementQueue().setLockMovement(false);
					getOpponent().getMovementQueue().setLockMovement(false);

					if (getHost().getPosition().getY() == getOpponent().getPosition().getY()) {
						getHost().getMovementQueue().walkStep(0, -1);
						getOpponent().getMovementQueue().walkStep(0, -1);
					}
					if (getHost().getPosition().getX() == getOpponent().getPosition().getX()) {
						getHost().getMovementQueue().walkStep(1, 0);
						getOpponent().getMovementQueue().walkStep(1, 0);
					}

					break;
				case 5:
					getHost().forceChat("I planted a " + hostResult.name());
					getOpponent().forceChat("I planted a " + opponentResult.name());
					break;
				case 6:
					getOpponent().sendMessage("You have planted: " + opponentResult.name() + ", opponent has planted: "
							+ hostResult.name());
					getHost().sendMessage("You have planted: " + hostResult.name() + ", opponent has planted: "
							+ opponentResult.name());
					break;
				case 8:
					GamblingManager.finishGamble(GamblingManager.ABC_FLOWERS_ID, getHost(), getOpponent(),
							opponentResult.ordinal(), hostResult.ordinal());
					this.stop();
					break;
				}
				time++;
			}
		});
	}
}