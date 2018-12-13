package com.arlania.world.content.gambling.gamble.impl;

import com.arlania.world.content.gambling.GamblingManager;
import com.arlania.world.content.gambling.GamblingManager.Flowers;
import com.arlania.world.content.gambling.gamble.GamblingGame;
import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.GameObject;
import com.arlania.net.packet.PacketBuilder;
import com.arlania.util.Misc;
import com.arlania.world.content.CustomObjects;
import com.arlania.world.entity.impl.player.Player;

/**
 * 
 * Handles the hot or cold gambling game
 *
 * @author 2012 <http://www.rune-server.org/members/dexter+morgan/>
 *
 */
public class HotOrColdGamblingGame extends GamblingGame {

	/**
	 * The hot or cold gambling game
	 * 
	 * @param host
	 *            the host
	 * @param opponent
	 *            the opponent
	 */
	public HotOrColdGamblingGame(Player host, Player opponent) {
		super(host, opponent);
	}

	@Override
	public String toString() {
		return "Hot or ColdPushed ";
	}

	@Override
	public void gamble() {
		getOpponent().getPacketSender().sendString(28394, "Hot");
		getOpponent().getPacketSender().sendString(28395, "@red@Cold");
		getOpponent().getGambling().setSelection(-1);

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

			/**
			 * Whether the host won
			 */
			boolean hostWon = true;

			/**
			 * The selection
			 */
			int selection = -1;

			@Override
			public void execute() {
				switch (time) {
				case 0:
					selection = getOpponent().getGambling().getSelection();
					break;
				case 1:
					getOpponent().getSession().queueMessage(new PacketBuilder(219));
					getOpponent().forceChat("I pick " + (selection == 1 ? "hot" : "cold") + " flowers.");
					flower = Flowers.values()[Misc.getRandom(Flowers.values().length - 1)];
					break;
				case 3:
					getHost().performAnimation(new Animation(827));
					break;

				case 5:
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
					/**
					 * The flower types
					 */
					switch (flower) {
					case ORANGE:
					case RED:
					case YELLOW:
						replay = false;
						if (selection == 1) {
							hostWon = false;
						}
						getHost().forceChat("I planted a hot flower.");
						getOpponent().sendMessage("The flower planted was a hot flower.");
						getHost().getMovementQueue().walkStep(0, -1);
						break;
					case PURPLE:
					case PASTEL:
					case BLUE:
						replay = false;
						if (selection == 2) {
							hostWon = false;
						}
						getHost().forceChat("I planted a cold flower.");
						getOpponent().sendMessage("The flower planted was a cold flower.");
						getHost().getMovementQueue().walkStep(0, -1);
						break;
					case WHITE:
					case BLACK:
						getHost().forceChat("I planted a white/black flower. I'm replanting!");
						getHost().getMovementQueue().walkStep(0, -1);
						replay = true;
						time = 0;
						break;
					case RAINBOW:
						hostWon = true;
						replay = false;
						getHost().forceChat("I planted a rainbow flower.");
						getOpponent()
								.sendMessage("The flower planted was a rainbow flower. That is auto win for the host.");
						getHost().getMovementQueue().walkStep(0, -1);
						break;
					}

					if (getOpponent().getPosition().getY() == getHost().getPosition().getY()) {
						if (getHost().getMovementQueue().canWalk(0, -1)) {
							getHost().getMovementQueue().walkStep(0, -1);
						}
					}
					if (getOpponent().getPosition().getX() == getHost().getPosition().getX()) {
						if (getHost().getMovementQueue().canWalk(1, 0)) {
							getHost().getMovementQueue().walkStep(1, 0);
						}
					}
					break;
				case 9:
					if (!replay) {
						GamblingManager.finishGamble(GamblingManager.HOT_COLD_ID, getHost(), getOpponent(),
								hostWon ? 1 : 0, hostWon ? 0 : 1);
						this.stop();
					} else {
						replay = false;
					}
					break;
				}
				/**
				 * No selection
				 */
				if (selection != -1) {
					time++;
				} else {
					getOpponent().getPacketSender().sendInterface(28392);
				}
			}
		});
	}
}