package net.samagames.dropper;

import net.samagames.api.games.GamePlayer;
import net.samagames.dropper.common.GameItems;
import net.samagames.dropper.common.GameLocations;
import net.samagames.dropper.level.AbstractLevel;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @author Vialonyx
 */

public class DropperPlayer extends GamePlayer {

    private AbstractLevel currentlyOn;

    public DropperPlayer(Player player) {
        super(player);
        this.currentlyOn = null;
    }

    @Override
    public void handleLogin(boolean reconnect){
        getPlayerIfOnline().teleport(GameLocations.SPAWN.locationValue());
        getPlayerIfOnline().getInventory().addItem(GameItems.BACK_LEVEL_HUB.getStackValue());
        getPlayerIfOnline().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 2, false, false));
    }

    public void setCurrentlyLevel(AbstractLevel level){
        this.currentlyOn = level;
    }

    public AbstractLevel getCurrentlyLevel(){
        return this.currentlyOn;
    }

}
