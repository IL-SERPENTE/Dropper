package net.samagames.dropper;

import net.samagames.dropper.events.PlayerAFKEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.logging.Level;

/**
 * @author Vialonyx
 */

public class AFKChecker {

    private DropperMain instance;
    private Player player;
    private Location atOldCheck;
    private int minutesWithoutMove, task;
    boolean isAfk;

    public AFKChecker(DropperMain instance, Player player){

        this.instance = instance;
        this.player = player;
        this.atOldCheck = player.getLocation();
        this.minutesWithoutMove = 0;
        this.isAfk = false;
        Location current = player.getLocation();
        PlayerAFKEvent playerAFKEvent = new PlayerAFKEvent(player);

        this.task = instance.getServer().getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {
            @Override
            public void run() {

                if(current.getX() == atOldCheck.getX() && current.getY() == atOldCheck.getY() && current.getZ() == atOldCheck.getZ()){
                    minutesWithoutMove++;
                } else {
                    minutesWithoutMove = 0;
                    isAfk = false;
                }

                atOldCheck = current;
                if(minutesWithoutMove == 6){
                    instance.getServer().getPluginManager().callEvent(playerAFKEvent);
                    isAfk = true;
                }

            }
        }, 0L, 1200);

    }

    public boolean isAfk(){
        return this.isAfk;
    }

    public void cancel(){
        this.instance.getServer().getScheduler().cancelTask(this.task);
        this.instance.getLogger().log(Level.INFO, "AFKChecker are cancelled for player " + this.player.getDisplayName());
    }

}
