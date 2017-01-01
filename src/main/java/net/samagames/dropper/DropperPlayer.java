package net.samagames.dropper;

import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.Game;
import net.samagames.api.games.GamePlayer;
import net.samagames.dropper.level.AbstractLevel;
import net.samagames.tools.scoreboards.ObjectiveSign;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;;

/**
 * @author Vialonyx
 */

public class DropperPlayer extends GamePlayer {

    private Player player;
    private DropperGame game;
    private AbstractLevel currentlyOn;
    private ObjectiveSign objective;

    public DropperPlayer(Player player) {
        super(player);
        this.player = player;
        this.game = (DropperGame) SamaGamesAPI.get().getGameManager().getGame();
        this.currentlyOn = null;
        this.objective = new ObjectiveSign("dropper", ChatColor.RED + "Dropper");
        this.objective.addReceiver(this.getOfflinePlayer());
    }

    @Override
    public void handleLogin(boolean reconnect){
        player.getInventory().clear();
        player.getInventory().addItem(this.game.BACK_LEVEL_HUB);
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 2, false, false));
        player.setGameMode(GameMode.ADVENTURE);
    }
    
    void updateScoreboard(){
    	List<DropperPlayer> players = new ArrayList<>();
    	players.addAll(this.game.getInGamePlayers().values());
    	this.objective.setLine(1, " ");
    	 this.objective.setLine(2, ChatColor.GOLD + (players.size() > 1 ? " Joueurs : " : " Joueur : ") + ChatColor.WHITE + ChatColor.BOLD + players.size());
    	this.objective.updateLines();
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
