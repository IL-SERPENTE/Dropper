package net.samagames.dropper.common;

import net.samagames.dropper.Dropper;
import net.samagames.tools.ProximityUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

/**
 * @author Vialonyx
 */

public class ProximityTaskManager {

    private BukkitTask proximityLevel1;

    private Dropper instance;
    public ProximityTaskManager(Dropper instance){
        this.instance = instance;

        this.proximityLevel1 = ProximityUtils.onNearbyOf(this.instance,
                this.instance.getLevelManager().getLevelRegistery().LEVEL_1.getSecretAs(),
                2.0D, 2.0D, 2.0D, Player.class, player -> this.instance.getServer().getScheduler().runTask(instance,
                        () -> this.instance.getLevelManager().joinLevel(player, this.instance.getLevelManager().getLevelRegistery().LEVEL_1)));

    }

}
