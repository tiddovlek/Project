package com.arlania.engine.task.impl;

import com.arlania.engine.task.Task;
import com.arlania.model.Animation;
import com.arlania.model.PlayerRights;
import com.arlania.model.Skill;
import com.arlania.model.Locations.Location;
import com.arlania.world.content.Consumables;
import com.arlania.world.entity.impl.player.Player;

public class FuriousPotionTask extends Task {

	public FuriousPotionTask(Player player) {
		super(1, player, true);
		this.player = player;
	}
	
	final Player player;

	@Override
	public void execute() {
		if(player == null || !player.isRegistered()) {
			stop();
			return;
		}
		int timer = player.getFuriousPotionTimer();
		if(timer == 600 || timer == 598 || timer == 596 || timer == 594 || timer == 592) {
			player.performAnimation(new Animation(2890));
		}
		if (timer == 600 || timer == 570 || timer == 540 || timer == 510 || timer == 480 || timer == 450 || timer == 420 || timer == 390 || timer == 360 || timer == 330 || timer == 300 || timer == 270 || timer == 240 || timer == 210 || timer == 180 || timer == 150 || timer == 120 || timer == 90 || timer == 60 || timer == 30) {
			Consumables.furiousIncrease(player, Skill.ATTACK, 0.29);
			Consumables.furiousIncrease(player, Skill.STRENGTH, 0.29);
			Consumables.furiousIncrease(player, Skill.DEFENCE, 0.29);
			Consumables.furiousIncrease(player, Skill.RANGED, 0.255);
			player.getSkillManager().setCurrentLevel(Skill.MAGIC, player.getSkillManager().getMaxLevel(Skill.MAGIC) + 7);
		}
		player.setFuriousPotionTimer(timer - 1);
		if(player.getFuriousPotionTimer() == 20) 
			player.getPacketSender().sendMessage("Your Furious effect is about to run out.");
		if(player.getFuriousPotionTimer() <= 0 || player.getLocation() == Location.DUEL_ARENA || player.getLocation() == Location.DUNGEONEERING || player.getLocation() == Location.WILDERNESS && player.getRights() != PlayerRights.DEVELOPER) {
			player.getPacketSender().sendMessage("Your Furious effect has run out.");
			for(int i = 0; i < 7; i++) {
				if(i == 3 || i == 5)
					continue;
				player.getSkillManager().setCurrentLevel(Skill.forId(i), player.getSkillManager().getMaxLevel(i));
			}
			player.setFuriousPotionTimer(0);
			stop();
		}
	}
}
