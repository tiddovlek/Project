package com.arlania.world.content;

import com.arlania.util.Misc;
import com.arlania.util.Stopwatch;
import com.arlania.world.World;

/*
 * @author Bas
 * www.Arlania.com
 */

public class Reminders {
	
	
    private static final int TIME = 900000; //15 minutes
	private static Stopwatch timer = new Stopwatch().reset();
	public static String currentMessage;
	
	/*
	 * Random Message Data
	 */
	private static final String[][] MESSAGE_DATA = { 
			{"@blu@[SERVER]@bla@ Join 'Help' CC For Help/Tips!"},
			{"@blu@[SERVER]@bla@ Do ::benefits To Check out donator Rank Benefits!"},
			{"@blu@[SERVER]@bla@ Donate to help the server grow! ::store"},
			{"@blu@[SERVER]@bla@ Use the command :: drop (npcname) for drop tables"},
			{"@blu@[SERVER]@bla@ Use the ::help command to ask staff for help"},
			{"@blu@[SERVER]@bla@ Remember to vote daily for rewards!"},
			{"@blu@[SERVER]@bla@ Remember to spread the word and invite your friends to play!"},
			{"@blu@[SERVER]@bla@ Use ::commands to find a list of commands"},
			{"@blu@[SERVER]@bla@ Toggle your client settings to your preference in the wrench tab!"},
			{"@blu@[SERVER]@bla@ Register and post on the forums to keep them active! ::Forum"},
			{"@blu@[SERVER]@bla@ Donating rsgp? speak to Lewis/Drapht for more info"},
			{"@blu@[SERVER]@bla@ Want to see something ingame? Suggest it on ::forums"},
			
		
	};

	/*
	 * Sequence called in world.java
	 * Handles the main method
	 * Grabs random message and announces it
	 */
	public static void sequence() {
		if(timer.elapsed(TIME)) {
			timer.reset();
			{
				
			currentMessage = MESSAGE_DATA[Misc.getRandom(MESSAGE_DATA.length - 1)][0];
			World.sendMessage(currentMessage);
			World.savePlayers();
					
				}
				
			World.savePlayers();
			}
		

          }

}