package net.samagames.dropper;

import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.samagames.api.SamaGamesAPI;
import net.samagames.dropper.common.ProximityTaskManager;
import net.samagames.dropper.events.PlayerListener;
import net.samagames.dropper.events.WorldListener;
import net.samagames.dropper.level.LevelManager;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
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
    
    public void sendTitle(Player player, String msgTitle, String msgSubTitle, int ticks){
		IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + msgTitle + "\"}");
		IChatBaseComponent chatSubTitle = ChatSerializer.a("{\"text\": \"" + msgSubTitle + "\"}");
		PacketPlayOutTitle p1 = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
		PacketPlayOutTitle p2 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSubTitle);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(p1);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(p2);
                
		PacketPlayOutTitle pt = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, 20, ticks, 20);
		((CraftPlayer)player).getHandle().playerConnection.sendPacket(pt);
	}

    public World getWorld(){
        return this.world;
    }

    public DropperGame getDropperGame(){
        return this.dropperGame;
    }

    public LevelManager getLevelManager(){
        return this.levelManager;
    }

}