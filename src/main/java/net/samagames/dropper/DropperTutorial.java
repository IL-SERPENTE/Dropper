package net.samagames.dropper;

import com.google.gson.JsonObject;
import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.LocationUtils;
import net.samagames.tools.tutorials.Tutorial;
import net.samagames.tools.tutorials.TutorialChapter;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;
import java.util.Arrays;
import static org.bukkit.Bukkit.getWorlds;

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
public class DropperTutorial extends Tutorial {

    /**
     * This is the Tutorial of the Dropper game.
     * @author Vialonyx.
     */

    private Dropper game;
    public DropperTutorial(Dropper game) {

        this.game = game;
        JsonObject object = SamaGamesAPI.get().getGameManager().getGameProperties().getConfigs();

        /**
         * Chapter I, Say Welcome !
         */
        this.addChapter(new TutorialChapter(LocationUtils.str2loc(getWorlds().get(0).getName() + ", " + object.get("step-1").getAsString()), "",
                Arrays.asList(Pair.of("Bienvenue sur The Dropper !", 72L),
                        Pair.of("Espérons que vous n'ayez pas le vertige !", 43L)), true));

        /**
         * Chorg.bukkit.entity.Playerapter II, How to complete a level.
         */
        this.addChapter(new TutorialChapter(LocationUtils.str2loc(getWorlds().get(0).getName() + ", " + object.get("step-2").getAsString()), "",
                Arrays.asList(Pair.of("Pour compléter un niveau", 72L),
                        Pair.of("Atterrissez dans l'eau !", 43L)), true));

        /**
         * Chapter III, Follow the right way.
         */
        this.addChapter(new TutorialChapter(LocationUtils.str2loc(getWorlds().get(0).getName() + ", " + object.get("step-2").getAsString()), "",
                Arrays.asList(Pair.of("Perdu ?", 72L),
                        Pair.of("Les citrouilles sont un bon indicateur !", 43L)), true));

        /**
         * Chapter IV, Soft landing !
         */
        this.addChapter(new TutorialChapter(LocationUtils.str2loc(getWorlds().get(0).getName() + ", " + object.get("step-2").getAsString()), "",
                Arrays.asList(Pair.of("Les toiles d'arraignées", 72L),
                        Pair.of("Vous permettent d'attérir en douceur !", 43L)), true));

        /**
         * Chapter V, You turn !
         */
        this.addChapter(new TutorialChapter(LocationUtils.str2loc(getWorlds().get(0).getName() + ", " + object.get("step-2").getAsString()), "",
                Arrays.asList(Pair.of("Maintenant...", 72L),
                        Pair.of("À toi de jouer !", 43L)), true));

    }

    @Override
    protected void onTutorialEnds(Player player, boolean interrupted){
        if(!interrupted){
            player.teleport(this.game.getSpawn());
            player.getInventory().setItem(4, Dropper.ITEM_QUIT_GAME);
            this.game.usualLevelJoin(player, this.game.getRegisteredLevels().get(0));
        }
    }

}
