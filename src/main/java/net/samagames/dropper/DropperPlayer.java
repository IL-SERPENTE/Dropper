package net.samagames.dropper;

import net.samagames.api.games.GamePlayer;
import net.samagames.dropper.level.AbstractLevel;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;;

/**
 * @author Vialonyx
 */

public class DropperPlayer extends GamePlayer {

    private Player player;
    private AbstractLevel currentlyOn;

    public DropperPlayer(Player player) {
        super(player);
        this.player = player;
        this.currentlyOn = null;
    }

    @Override
    public void handleLogin(boolean reconnect){
        getPlayerIfOnline().getInventory().clear();
        getPlayerIfOnline().getInventory().addItem(GameItems.BACK_LEVEL_HUB.getStackValue());
        getPlayerIfOnline().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 2, false, false));
        getPlayerIfOnline().setGameMode(GameMode.ADVENTURE);
    }
    
    /**
     * Set current played level for player
     * @param level the level
     */

    public void setCurrentlyLevel(AbstractLevel level){
        this.currentlyOn = level;
    }
    
    /**
     * Get the current played level by player
     * @return the level
     */

    public AbstractLevel getCurrentlyLevel(){
        return this.currentlyOn;
    }

}
