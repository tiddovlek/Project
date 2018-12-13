package com.arlania.world.content.combat.strategy.impl;

import com.arlania.model.Animation;
import com.arlania.model.Locations;
import com.arlania.util.Misc;
import com.arlania.world.content.combat.CombatContainer;
import com.arlania.world.content.combat.CombatType;
import com.arlania.world.content.combat.strategy.CombatStrategy;
import com.arlania.world.entity.impl.Character;
import com.arlania.world.entity.impl.npc.NPC;

public class Shrek implements CombatStrategy {

	private String[] shrekSayings = { "Its all ogre now! I should kill you!", "Get Shrekt M8", "Youre done bud",
			"GET OUT OF ME SWAMP", "Where is Donnnkeey?" };

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
		NPC shrek = (NPC) entity;
		int ran = Misc.getRandom(shrekSayings.length - 1);
		shrek.forceChat(shrekSayings[ran]);
		if (Locations.goodDistance(shrek.getPosition().copy(), victim.getPosition().copy(), 1)) {
			shrek.performAnimation(new Animation(shrek.getDefinition().getAttackAnimation()));
			shrek.getCombatBuilder().setContainer(new CombatContainer(shrek, victim, 1, 1, CombatType.MELEE, true));
		}
		return true;
	}

	@Override
	public int attackDelay(Character entity) {
		return entity.getAttackSpeed();
	}

	@Override
	public int attackDistance(Character entity) {
		return 3;
	}

	@Override
	public CombatType getCombatType() {
		return CombatType.MELEE;
	}
}
