package com.arlania.world.content.combat.strategy.impl;

import com.arlania.engine.task.Task;
import com.arlania.engine.task.TaskManager;
import com.arlania.model.Animation;
import com.arlania.model.Graphic;
import com.arlania.model.GraphicHeight;
import com.arlania.model.Locations;
import com.arlania.model.Projectile;
import com.arlania.util.Misc;
import com.arlania.util.RandomUtility;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;

public class Abbadon implements CombatStrategy {

	private static final Animation anim = new Animation(10922);
	private static final Animation anim2 = new Animation(10918);
	private static final Animation anim3 = new Animation(10917);
	private static final Graphic gfx1 = new Graphic(1886, 3, GraphicHeight.MIDDLE);
	private static final Graphic gfx2 = new Graphic(1885);

	private int[] common_drop = {18830,14010,14009,14008,14011,14013,14012,14014,14015,14016,15126,10696};
	private int[] rare_drop = {13215,13216,13217,13218,13219,13220,6199,6199,6199};
	private int[] Legendary_drop = {20112,20113,20114};
	private int[] Epic_drop = {21075,17847,21080,21079};

	protected int getReward() {
		int reward;
		int random = Misc.random(1000);
		if( random < 400) {
			reward = common_drop[RandomUtility.getRandom(common_drop.length-1)];
		} else if( random < 900) {
			reward = rare_drop[RandomUtility.getRandom(rare_drop.length-1)];
		} else if( random < 990) {
			reward = Legendary_drop[RandomUtility.getRandom(Legendary_drop.length-1)];
		} else {
			reward = Epic_drop[RandomUtility.getRandom(Epic_drop.length-1)];
		}
		return reward;
	}

	public static void death() {

	}
	@Override
	public boolean canAttack(Character entity, Character victim) {
		return true;
	}

	@Override
	public CombatContainer attack(Character entity, Character victim) {
		return null;
	}

	@Override
	public boolean customContainerAttack(Character entity, Character victim) {
		NPC td = (NPC)entity;
		if(victim.getConstitution() <= 0) {
			return true;
		}
		if(td.isChargingAttack()) {
			return true;
		}
		if(Locations.goodDistance(td.getPosition().copy(), victim.getPosition().copy(), 1) && Misc.getRandom(6) <= 4) {
			td.performAnimation(anim);
			td.performGraphic(gfx1);
			td.getCombatBuilder().setContainer(new CombatContainer(td, victim, 1, 2, CombatType.MELEE, true));
		} else if(Misc.getRandom(10) <= 7) {
			td.performAnimation(anim2);
			td.setChargingAttack(true);
			td.getCombatBuilder().setContainer(new CombatContainer(td, victim, 1, 2, CombatType.RANGED, true));
			TaskManager.submit(new Task(1, td, false) {
				@Override
				protected void execute() {
					new Projectile(td, victim, 1884, 44, 3, 43, 31, 0).sendProjectile();
					td.setChargingAttack(false).getCombatBuilder().setAttackTimer(td.getDefinition().getAttackSpeed() - 1);
					stop();
				}
			});
		} else {
			td.performAnimation(anim3);
			victim.performGraphic(gfx2);
			td.getCombatBuilder().setContainer(new CombatContainer(td, victim, 1, 2, CombatType.MAGIC, true));
		}
		return true;
	}


	@Override
	public int attackDelay(Character entity) {
		return entity.getAttackSpeed();
	}

	@Override
	public int attackDistance(Character entity) {
		return 5;
	}
	
	@Override
	public CombatType getCombatType() {
		return CombatType.MIXED;
	}
}
