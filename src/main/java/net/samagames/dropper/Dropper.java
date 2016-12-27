package net.samagames.dropper;

import net.samagames.api.SamaGamesAPI;
import net.samagames.dropper.common.ProximityTaskManager;
import net.samagames.dropper.events.PlayerListener;
import net.samagames.dropper.events.WorldListener;
import net.samagames.dropper.level.LevelManager;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Vialonyx
 */

public class Dropper extends JavaPlugin {

    private World world;
    private DropperGame dropperGame;
    private LevelManager levelManager;
    private ProximityTaskManager proximityTaskManager;

    @Override
    public void onEnable(){

        this.world = this.getServer().getWorlds().get(0);
        this.world.setGameRuleValue("doDaylightCycle", "false");

        this.levelManager = new LevelManager(this);
        this.proximityTaskManager = new ProximityTaskManager(this);

        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        this.getServer().getPluginManager().registerEvents(new WorldListener(this), this);

        // -- SamaGames --
        this.dropperGame = new DropperGame("gameCode", "Dropper", "gameDesc", DropperPlayer.class);
        SamaGamesAPI.get().getGameManager().registerGame(this.dropperGame);
    }

    public DropperGame getDropperGame(){
        return this.dropperGame;
    }

    public LevelManager getLevelManager(){
        return this.levelManager;
    }

}
