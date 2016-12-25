package net.samagames.dropper;

import net.samagames.api.games.GamePlayer;
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
        this.getPlayerIfOnline().sendMessage("Test");
    }

}
