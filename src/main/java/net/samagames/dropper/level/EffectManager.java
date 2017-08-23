package net.samagames.dropper.level;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/*
 * This file is part of Dropper.
 *
 * Dropper is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dropper is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Dropper.  If not, see <http://www.gnu.org/licenses/>.
 */
public class EffectManager {

    /**
     * The effect manager is used to manages effects gived to player on levels.
     * @author Vialonyx
     */

    /**
     * Remove all actives effects and add the default night vision effect.
     * @param player The player on which want to restore effects.
     */

    public void restoreDefaultEffects(Player player){
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 2));
    }

    public void addEffectsForLevel(Player player, DropperLevel level){

        if(level.getID() == 19){
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 999999, 4));
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 2));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
        } else if (level.getID() == 15){
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 999999, 4));
        }else if (level.getID() == 23){
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 4));
        } else if(level.getID() == 24) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 999999, 4));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 4));
        } else if(level.getID() == 25) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 3));
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999, 7));
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 999999, 0));
        } else if(level.getID() == 26 || level.getID() == 7){
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 99999, 4));
        } else if(level.getID() == 27){
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 1));
        }

    }

}
