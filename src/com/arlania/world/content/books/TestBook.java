package com.arlania.world.content.books;

import com.arlania.model.Animation;
import com.arlania.world.entity.impl.player.Player;

/**
 * Handles the TestBook 
 * @author Lewis
 *
 */
public class TestBook {

	public static void readBook(Player player, int pageIndex, boolean interfaceAllowed) {
		if(player.getInterfaceId() != -1 && !interfaceAllowed) {
			player.getPacketSender().sendMessage("Please close the interface you have open before opening a new one.");
			return;
		}
		if(pageIndex < 0)
			pageIndex = 0;
		if(pageIndex > 10)
			pageIndex = 12;
		player.getMovementQueue().reset();
		player.performAnimation(new Animation(1350));
		player.getPacketSender().sendString(903, "Lewis' Book");
		for(int i = 0; i < TestBookPages[0].length; i++)
			player.getPacketSender().sendString(843+i, TestBookPages[pageIndex][i]);
		for(int i = 0; i < TestBookPages[1].length; i++)
			player.getPacketSender().sendString(843+11+i, TestBookPages[pageIndex+1][i]);
		player.getPacketSender().sendString(14165, "- "+pageIndex+" - ");
		player.getPacketSender().sendString(14166, "- "+(pageIndex+1)+" - ");
		player.getPacketSender().sendInterface(837);
		player.setCurrentBookPage(pageIndex);
	}
	
	private static final String[][] TestBookPages = {
		{
			
		"Oh why, oh why am I",
		"subject to such pain,",
		"and ferocity Jarrod.",
		"You rip and dip like",
		"I am nought to you",
		"But then you will",
		"call upon Lips, that",
		"little slut of yours.",
		"But it's fine because",
		"One day I shall rise",
		"and become the great...",
		"",}
		
		,{
			
		"Oof oof oof oof oof",
		"Oof oof oof oof oof",
		"Oof oof oof oof oof",
		"Oof oof oof oof oof",		
		"Oof oof oof oof oof",
		"Oof oof oof oof oof",
		"Oof oof oof oof oof",
		"Oof oof oof oof oof",		
		"Oof oof oof oof oof",
		"Oof oof oof oof oof",
		"Oof oof oof oof oof",
		
		}, //end of page 1+2
		
		{
			
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",
			"",}
			
			,{
				
			"",
			"",
			"",
			"",		
			"",
			"",
			"",
			"",		
			"",
			"",
			"",
			
			}, //end of page 3+4
			
			{
				
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",
				"",}
				
				,{
					
				"",
				"",
				"",
				"",		
				"",
				"",
				"",
				"",		
				"",
				"",
				"",
				
				}, //end of page 5+6
				
				{
					
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"",
					"",}
					
					,{
						
					"",
					"",
					"",
					"",		
					"",
					"",
					"",
					"",		
					"",
					"",
					"",
					
					}, //end of page 7+8
					{
						
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",
						"",}
						
						,{
							
						"",
						"",
						"",
						"",		
						"",
						"",
						"",
						"",		
						"",
						"",
						"",
						
						}, //end of page 9+10
						
						{
							
							"",
							"",
							"",
							"",
							"",
							"",
							"",
							"",
							"",
							"",
							"",
							"",}
							
							,{
								
							"",
							"",
							"",
							"",		
							"",
							"",
							"",
							"",		
							"",
							"",
							"",
							
							}, //end of page 11+12
							
							{
								
								"",
								"",
								"",
								"",
								"",
								"",
								"",
								"",
								"",
								"",
								"",
								"",}
								
								,{
									
								"",
								"",
								"",
								"",		
								"",
								"",
								"",
								"",		
								"",
								"",
								"",
								
								}, //end of page 13+14
								
								{
									
									"",
									"",
									"",
									"",
									"",
									"",
									"",
									"",
									"",
									"",
									"",
									"",}
									
									,{
										
									"",
									"",
									"",
									"",		
									"",
									"",
									"",
									"",		
									"",
									"",
									"",
									
									}, //end of page 15+16
									
									{
										
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",
										"",}
										
										,{
											
										"",
										"",
										"",
										"",		
										"",
										"",
										"",
										"",		
										"",
										"",
										"",
										
										}, //end of page 17+18
		};
}
