package net.samagames.dropper.common;

import net.samagames.dropper.Dropper;
import net.samagames.tools.ProximityUtils;
import org.bukkit.entity.Player;

/**
 * @author Vialonyx
 */

public class ProximityTaskManager {
	
	/*
	 * This class manages all proximity tasks. 
	 * This tasks checks if player is under the defined radius and call a callback method.
	 */

    private Dropper instance;
    public ProximityTaskManager(Dropper instance){
        this.instance = instance;

        ProximityUtils.onNearbyOf(this.instance,
                this.instance.getLevelManager().LEVEL_1.getStartSecretAs(),
                1.0D, 1.0D, 1.0D, Player.class, player -> this.instance.getServer().getScheduler().runTask(instance,
                        () -> this.instance.getLevelManager().joinLevel(player, this.instance.getLevelManager().LEVEL_1)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.instance.getLevelManager().LEVEL_1.getWinSecretAs(),
                2.0D, 2.0D, 2.0D, Player.class, player -> this.instance.getServer().getScheduler().runTask(instance,
                        () -> this.instance.getLevelManager().setLevelWin(player)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.instance.getLevelManager().LEVEL_2.getStartSecretAs(),
                1.0D, 1.0D, 1.0D, Player.class, player -> this.instance.getServer().getScheduler().runTask(instance,
                        () -> this.instance.getLevelManager().joinLevel(player, this.instance.getLevelManager().LEVEL_2)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.instance.getLevelManager().LEVEL_2.getWinSecretAs(),
                2.0D, 2.0D, 2.0D, Player.class, player -> this.instance.getServer().getScheduler().runTask(instance,
                        () -> this.instance.getLevelManager().setLevelWin(player)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.instance.getLevelManager().LEVEL_3.getStartSecretAs(),
                1.0D, 1.0D, 1.0D, Player.class, player -> this.instance.getServer().getScheduler().runTask(instance,
                        () -> this.instance.getLevelManager().joinLevel(player, this.instance.getLevelManager().LEVEL_3)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.instance.getLevelManager().LEVEL_3.getWinSecretAs(),
                2.0D, 2.0D, 2.0D, Player.class, player -> this.instance.getServer().getScheduler().runTask(instance,
                        () -> this.instance.getLevelManager().setLevelWin(player)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.instance.getLevelManager().LEVEL_4.getStartSecretAs(),
                1.0D, 1.0D, 1.0D, Player.class, player -> this.instance.getServer().getScheduler().runTask(instance,
                        () -> this.instance.getLevelManager().joinLevel(player, this.instance.getLevelManager().LEVEL_4)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.instance.getLevelManager().LEVEL_4.getWinSecretAs(),
                2.0D, 2.0D, 2.0D, Player.class, player -> this.instance.getServer().getScheduler().runTask(instance,
                        () -> this.instance.getLevelManager().setLevelWin(player)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.instance.getLevelManager().LEVEL_5.getStartSecretAs(),
                1.0D, 1.0D, 1.0D, Player.class, player -> this.instance.getServer().getScheduler().runTask(instance,
                        () -> this.instance.getLevelManager().joinLevel(player, this.instance.getLevelManager().LEVEL_5)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.instance.getLevelManager().LEVEL_5.getWinSecretAs(),
                2.0D, 2.0D, 2.0D, Player.class, player -> this.instance.getServer().getScheduler().runTask(instance,
                        () -> this.instance.getLevelManager().setLevelWin(player)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.instance.getLevelManager().LEVEL_6.getStartSecretAs(),
                1.0D, 1.0D, 1.0D, Player.class, player -> this.instance.getServer().getScheduler().runTask(instance,
                        () -> this.instance.getLevelManager().joinLevel(player, this.instance.getLevelManager().LEVEL_6)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.instance.getLevelManager().LEVEL_6.getWinSecretAs(),
                2.0D, 2.0D, 2.0D, Player.class, player -> this.instance.getServer().getScheduler().runTask(instance,
                        () -> this.instance.getLevelManager().setLevelWin(player)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.instance.getLevelManager().LEVEL_7.getStartSecretAs(),
                1.0D, 1.0D, 1.0D, Player.class, player -> this.instance.getServer().getScheduler().runTask(instance,
                        () -> this.instance.getLevelManager().joinLevel(player, this.instance.getLevelManager().LEVEL_7)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.instance.getLevelManager().LEVEL_7.getWinSecretAs(),
                2.0D, 2.0D, 2.0D, Player.class, player -> this.instance.getServer().getScheduler().runTask(instance,
                        () -> this.instance.getLevelManager().setLevelWin(player)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.instance.getLevelManager().LEVEL_8.getStartSecretAs(),
                1.0D, 1.0D, 1.0D, Player.class, player -> this.instance.getServer().getScheduler().runTask(instance,
                        () -> this.instance.getLevelManager().joinLevel(player, this.instance.getLevelManager().LEVEL_8)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.instance.getLevelManager().LEVEL_8.getWinSecretAs(),
                2.0D, 2.0D, 2.0D, Player.class, player -> this.instance.getServer().getScheduler().runTask(instance,
                        () -> this.instance.getLevelManager().setLevelWin(player)));

    }

}
