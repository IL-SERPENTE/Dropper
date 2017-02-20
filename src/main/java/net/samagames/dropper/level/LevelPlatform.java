package net.samagames.dropper.level;

import org.bukkit.Location;
import org.bukkit.Material;

/**
 * @author Vialonyx
 */

public class LevelPlatform {

    private Location blockCentral;
    private boolean hiden;

    public LevelPlatform(Location blockCentral){
        this.blockCentral = blockCentral;
        this.create();
    }

    public Location get(){
        return this.blockCentral.add(0, 1, 0);
    }

    public void create(){
        this.blockCentral.getBlock().setType(Material.GLASS);
    }

    public void hide(){
        this.blockCentral.getBlock().setType(Material.AIR);
    }

    public boolean isHiden(){
        return this.hiden;
    }

}
