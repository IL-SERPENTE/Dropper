package net.samagames.dropper;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import com.google.gson.JsonObject;
import net.samagames.api.SamaGamesAPI;
import net.samagames.api.games.Game;
import net.samagames.dropper.events.PlayerListener;
import net.samagames.dropper.events.WorldListener;
import net.samagames.dropper.level.AbstractLevel;
import net.samagames.dropper.level.LevelManager;
import net.samagames.tools.LocationUtils;
import net.samagames.tools.ProximityUtils;

/**
 * @author Vialonyx
 */

public class DropperGame extends Game<DropperPlayer> {
	
	private Dropper instance;
	private World world;
	private LevelManager levelManager;
	
	// Items 
	public final ItemStack BACK_LEVEL_HUB;

    public DropperGame(String gameCodeName, String gameName, String gameDescription, Class<DropperPlayer> gamePlayerClass, Dropper instance) {
        super(gameCodeName, gameName, gameDescription, gamePlayerClass);
        
        this.instance = instance;
        this.world = this.instance.getServer().getWorlds().get(0);
        this.world.setGameRuleValue("doDaylightCycle", "false");
        this.levelManager = new LevelManager(this);
        
        this.BACK_LEVEL_HUB = this.stackBuilder(ChatColor.DARK_AQUA + "Retour au" + ChatColor.AQUA + " Choix du niveau", null, Material.ENDER_CHEST, (byte) 0);
        
        this.instance.getServer().getPluginManager().registerEvents(new PlayerListener(this.instance), this.instance);
        this.instance.getServer().getPluginManager().registerEvents(new WorldListener(), this.instance);
        
        startProximityTasks();
        this.instance.getServer().getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {
			
			@Override
			public void run() {
				getInGamePlayers().values().forEach(DropperPlayer::updateScoreboard);
			}
			
		}, 0L, 20L);
        
    }
    
    @Override
    public void handleLogout(Player player){
    	if(this.getRegisteredGamePlayers().get(player.getUniqueId()).getCurrentlyLevel() != null){
    		this.levelManager.leaveLevel(player, true);
    	}
    }
    
    @Override
    public void startGame(){
        super.startGame();
    }
    
    /**
     * Create custom ItemStack with simple
     * @param name Item name
     * @param lore Item lore
     * @param material Item type
     * @param data Item data
     * @return the item
     */
    
    private ItemStack stackBuilder(String name, List<String> lore, Material material, byte data){ 
        org.bukkit.inventory.ItemStack tmpStack = new ItemStack(material, 1, data); 
        ItemMeta tmpStackMeta = tmpStack.getItemMeta(); 
        tmpStackMeta.setDisplayName(name); 
        tmpStackMeta.setLore(lore); 
        tmpStack.setItemMeta(tmpStackMeta); 
 
        return tmpStack; 
    } 
    
    /**
     * Get the first world of list
     * @return the world
     */
    
    public World getWorld(){
    	return this.world;
    }
    
    /**
     * Get Location of level hub from arena file
     * @return the location
     */
    
    public Location getLevelHub(){
    	JsonObject object = SamaGamesAPI.get().getGameManager().getGameProperties().getConfigs();
        return LocationUtils.str2loc(object.get("world-name").getAsString() + ", " + object.get("level-hub").getAsString());
    }
    
    /**
     * Get an instance of Dropper
     * @return an instance of Dropper
     */
    
    public Dropper getInstance(){
    	return this.instance;
    }
    
    /**
     * Get an instance of LevelManager
     * @return an instance of LevelManager
     */
    
    public LevelManager getLevelManager(){
    	return this.levelManager;
    }
    
    /**
     * Removing all effects and restore initial NightVision effect
     * @param player the player
     */
    
    public void resetPotionEffects(Player player){
    	player.getActivePotionEffects().clear();
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 2, false, false));
    }
    
    /**
     * Start all proximity tasks
     */
    
    public void startProximityTasks(){
    	
    	BukkitScheduler bukkitScheduler = this.instance.getServer().getScheduler();
    	
    	ProximityUtils.onNearbyOf(this.instance,
                this.getLevelManager().LEVEL_1.getStartSecretAs(),
                1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
                        () -> this.getLevelManager().joinLevel(player, this.getLevelManager().LEVEL_1)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.getLevelManager().LEVEL_1.getWinSecretAs(),
                2.0D, 2.0D, 2.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
                        () -> this.getLevelManager().setLevelWin(player)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.getLevelManager().LEVEL_2.getStartSecretAs(),
                1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
                        () -> this.getLevelManager().joinLevel(player, this.getLevelManager().LEVEL_2)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.getLevelManager().LEVEL_2.getWinSecretAs(),
                2.0D, 2.0D, 2.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
                        () -> this.getLevelManager().setLevelWin(player)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.getLevelManager().LEVEL_3.getStartSecretAs(),
                1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
                        () -> this.getLevelManager().joinLevel(player, this.getLevelManager().LEVEL_3)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.getLevelManager().LEVEL_3.getWinSecretAs(),
                2.0D, 2.0D, 2.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
                        () -> this.getLevelManager().setLevelWin(player)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.getLevelManager().LEVEL_4.getStartSecretAs(),
                1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
                        () -> this.getLevelManager().joinLevel(player, this.getLevelManager().LEVEL_4)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.getLevelManager().LEVEL_4.getWinSecretAs(),
                2.0D, 2.0D, 2.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
                        () -> this.getLevelManager().setLevelWin(player)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.getLevelManager().LEVEL_5.getStartSecretAs(),
                1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
                        () -> this.getLevelManager().joinLevel(player, this.getLevelManager().LEVEL_5)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.getLevelManager().LEVEL_5.getWinSecretAs(),
                2.0D, 2.0D, 2.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
                        () -> this.getLevelManager().setLevelWin(player)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.getLevelManager().LEVEL_6.getStartSecretAs(),
                1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
                        () -> this.getLevelManager().joinLevel(player, this.getLevelManager().LEVEL_6)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.getLevelManager().LEVEL_6.getWinSecretAs(),
                2.0D, 2.0D, 2.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
                        () -> this.getLevelManager().setLevelWin(player)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.getLevelManager().LEVEL_7.getStartSecretAs(),
                1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
                        () -> this.getLevelManager().joinLevel(player, this.getLevelManager().LEVEL_7)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.getLevelManager().LEVEL_7.getWinSecretAs(),
                2.0D, 2.0D, 2.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
                        () -> this.getLevelManager().setLevelWin(player)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.getLevelManager().LEVEL_8.getStartSecretAs(),
                1.0D, 1.0D, 1.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
                        () -> this.getLevelManager().joinLevel(player, this.getLevelManager().LEVEL_8)));
        
        ProximityUtils.onNearbyOf(this.instance,
                this.getLevelManager().LEVEL_8.getWinSecretAs(),
                2.0D, 2.0D, 2.0D, Player.class, player -> bukkitScheduler.runTask(this.instance,
                        () -> this.getLevelManager().setLevelWin(player)));
    	
    }

}
