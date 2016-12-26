package net.samagames.dropper;

import net.samagames.api.games.GamePlayer;
import net.samagames.dropper.common.GameItems;
import net.samagames.dropper.common.GameLocations;
import org.bukkit.entity.Player;

/**
 * @author Vialonyx
 */

public class DropperPlayer extends GamePlayer {

    public DropperPlayer(Player player) {
        super(player);
    }

    @Override
    public void handleLogin(boolean reconnect){
        getPlayerIfOnline().teleport(GameLocations.SPAWN.locationValue());
        getPlayerIfOnline().getInventory().addItem(GameItems.BACK_LEVEL_HUB.getStackValue());
    }

}
