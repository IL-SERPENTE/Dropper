package net.samagames.dropper.common;

import net.samagames.dropper.Dropper;
import net.samagames.tools.ProximityUtils;
import org.bukkit.entity.Player;

/**
 * @author Vialonyx
 */

public class ProximityTaskManager {

    private Dropper instance;
    public ProximityTaskManager(Dropper instance){
        this.instance = instance;

        ProximityUtils.onNearbyOf(this.instance,
                this.instance.getLevelManager().LEVEL_1.getStartSecretAs(),
                2.0D, 2.0D, 2.0D, Player.class, player -> this.instance.getServer().getScheduler().runTask(instance,
                        () -> this.instance.getLevelManager().joinLevel(player, this.instance.getLevelManager().LEVEL_1)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.instance.getLevelManager().LEVEL_1.getWinSecretAs(),
                2.0D, 2.0D, 2.0D, Player.class, player -> this.instance.getServer().getScheduler().runTask(instance,
                        () -> this.instance.getLevelManager().setLevelWin(player)));

    }

}
