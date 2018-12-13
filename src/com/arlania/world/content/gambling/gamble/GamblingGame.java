package com.arlania.world.content.gambling.gamble;

import com.arlania.net.packet.interaction.PacketInteraction;
import com.arlania.world.entity.impl.player.Player;

/**
 * 
 * Represents a Gamble
 *
 * @author 2012 <http://www.rune-server.org/members/dexter+morgan/>
 *
 */
public abstract class GamblingGame extends PacketInteraction {

	/**
	 * The host
	 */
	private Player host;

	/**
	 * The opponent
	 */
	private Player opponent;

	/**
	 * The host score
	 */
	private int hostScore;

	/**
	 * The opponent score
	 */
	private int opponentScore;

	/**
	 * Whether it is the hosts turn
	 */
	private boolean hostTurn;

	/**
	 * Represents a game gamble
	 * 
	 * @param host
	 *            the host
	 * @param two
	 *            the second player
	 */
	public GamblingGame(Player host, Player opponent) {
		this.setHost(host);
		this.setOpponent(opponent);
	}

	/**
	 * Initiates the game
	 */
	public abstract void gamble();

	/**
	 * Gets the host
	 * 
	 * @return the host
	 */
	public Player getHost() {
		return host;
	}

	/**
	 * Sets the host
	 *
	 * @param host
	 *            the host
	 */
	public void setHost(Player host) {
		this.host = host;
	}

	/**
	 * Gets the opponent
	 * 
	 * @return the opponent
	 */
	public Player getOpponent() {
		return opponent;
	}

	/**
	 * Sets the opponent
	 *
	 * @param opponent
	 *            the opponent
	 */
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}

	/**
	 * Gets the hostScore
	 * 
	 * @return the hostScore
	 */
	public int getHostScore() {
		return hostScore;
	}

	/**
	 * Sets the hostScore
	 *
	 * @param hostScore
	 *            the hostScore
	 */
	public void setHostScore(int hostScore) {
		this.hostScore = hostScore;
	}

	/**
	 * Gets the opponentScore
	 * 
	 * @return the opponentScore
	 */
	public int getOpponentScore() {
		return opponentScore;
	}

	/**
	 * Sets the opponentScore
	 *
	 * @param opponentScore
	 *            the opponentScore
	 */
	public void setOpponentScore(int opponentScore) {
		this.opponentScore = opponentScore;
	}

	/**
	 * Gets the hostTurn
	 * 
	 * @return the hostTurn
	 */
	public boolean isHostTurn() {
		return hostTurn;
	}

	/**
	 * Sets the hostTurn
	 *
	 * @param hostTurn
	 *            the hostTurn
	 */
	public void setHostTurn(boolean hostTurn) {
		this.hostTurn = hostTurn;
	}
}