package com.arlania.world.content.dialogue.impl;

import com.arlania.world.content.dialogue.Dialogue;
import com.arlania.world.content.dialogue.DialogueExpression;
import com.arlania.world.content.dialogue.DialogueType;
import com.arlania.world.entity.impl.player.Player;

public class VladimirSpawning {

	public static Dialogue get(Player p, int stage) {
		Dialogue dialogue = null;
		switch(stage) {
		case 0:
			dialogue = new Dialogue() {

				@Override
				public DialogueType type() {
					// TODO Auto-generated method stub
					return DialogueType.NPC_STATEMENT;
				}

				@Override
				public DialogueExpression animation() {
					// TODO Auto-generated method stub
					return DialogueExpression.SCARED;
				}

				@Override
				public String[] dialogue() {
					return new String[]{"What are you doing? Do you realise how dangerous this is!", "Do you realy wish to continue?"};
				}
				
				public int npcId() {
					return 1;
				}
				
				public Dialogue nextDialogue() {
					return get(p, stage + 1);
				}
				
			};			
			break;
		case 1:
			dialogue = new Dialogue() {

				@Override
				public DialogueType type() {
					// TODO Auto-generated method stub
					return DialogueType.OPTION;
				}


				@Override
				public String[] dialogue() {
					return new String[]{"Yes! Spawn this monster already!", "Oh hell no, Imma back off!"};
				}
				
				
				public void specialAction() {
					p.setDialogueActionId(150);
				}


				@Override
				public DialogueExpression animation() {
					// TODO Auto-generated method stub
					return null;
				}
				
			};			
			break;
		case 2:
			dialogue = new Dialogue() {

				@Override
				public DialogueType type() {
					// TODO Auto-generated method stub
					return DialogueType.NPC_STATEMENT;
				}

				@Override
				public DialogueExpression animation() {
					// TODO Auto-generated method stub
					return DialogueExpression.SCARED;
				}

				@Override
				public String[] dialogue() {
					return new String[]{"Would you like to spawn another Vladimir?"};
				}
				
				public int npcId() {
					return 1;
				}
				
				public Dialogue nextDialogue() {
					return get(p, 1);
				}
				
			};			
			break;
		}
		return dialogue;
		
	}
	
}