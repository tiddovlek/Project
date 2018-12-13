package com.arlania.util;

import com.arlania.world.entity.impl.player.Player;

/**
 * @author Stan van der Bend
 */
public interface Command {

    void execute(final Player player, String command);

}
