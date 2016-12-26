package net.samagames.dropper;

import net.samagames.api.SamaGamesAPI;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Vialonyx
 */

public class Dropper extends JavaPlugin {

    private World world;
    private DropperGame dropperGame;

    @Override
    public void onEnable(){

        this.world = this.getServer().getWorlds().get(0);
        this.world.setGameRuleValue("doDaylightCycle", "false");

        // -- SamaGames --
        this.dropperGame = new DropperGame("gameCode", "Dropper", "gameDesc", DropperPlayer.class);
        SamaGamesAPI.get().getGameManager().registerGame(this.dropperGame);
    }

}
