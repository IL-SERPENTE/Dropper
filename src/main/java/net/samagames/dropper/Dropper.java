package net.samagames.dropper;

import net.samagames.api.SamaGamesAPI;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Vialonyx
 */

public class Dropper extends JavaPlugin {

    private DropperGame dropperGame;

    @Override
    public void onEnable(){
        this.dropperGame = new DropperGame("gameCode", "Dropper", "gameDesc", DropperPlayer.class);
        SamaGamesAPI.get().getGameManager().registerGame(this.dropperGame);
    }

}
