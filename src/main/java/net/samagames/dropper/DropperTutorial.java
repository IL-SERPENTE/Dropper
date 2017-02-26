package net.samagames.dropper;

import com.google.gson.JsonObject;
import net.samagames.api.SamaGamesAPI;
import net.samagames.tools.LocationUtils;
import net.samagames.tools.tutorials.Tutorial;
import net.samagames.tools.tutorials.TutorialChapter;
import org.apache.commons.lang3.tuple.Pair;
import java.util.Arrays;
import static org.bukkit.Bukkit.getWorlds;

/**
 * @author Vialonyx
 */

public class DropperTutorial extends Tutorial {

    private final Tutorial dropperTutorial;

    public DropperTutorial(){

        this.dropperTutorial = new Tutorial();
        JsonObject object = SamaGamesAPI.get().getGameManager().getGameProperties().getConfigs();

        /**
         * Chapter I, Say Welcome !
         */
        this.dropperTutorial.addChapter(new TutorialChapter(LocationUtils.str2loc(getWorlds().get(0).getName() + ", " + object.get("step-1").getAsString()), "",
                Arrays.asList(Pair.of("Bienvenue sur The Dropper !", 72L),
                              Pair.of("Espérons que vous n'ayez pas le vertige !", 43L)), true));

        /**
         * Chapter II, How to complete a level.
         */
        this.dropperTutorial.addChapter(new TutorialChapter(LocationUtils.str2loc(getWorlds().get(0).getName() + ", " + object.get("step-2").getAsString()), "",
                Arrays.asList(Pair.of("Pour compléter un niveau", 72L),
                              Pair.of("Atterrissez dans l'eau !", 43L)), true));

        /**
         * Chapter III, Follow the right way.
         */
        this.dropperTutorial.addChapter(new TutorialChapter(LocationUtils.str2loc(getWorlds().get(0).getName() + ", " + object.get("step-2").getAsString()), "",
                Arrays.asList(Pair.of("Perdu ?", 72L),
                              Pair.of("Les citrouilles sont un bon indicateur !", 43L)), true));

        /**
         * Chapter IV, Soft landing !
         */
        this.dropperTutorial.addChapter(new TutorialChapter(LocationUtils.str2loc(getWorlds().get(0).getName() + ", " + object.get("step-2").getAsString()), "",
                Arrays.asList(Pair.of("Les toiles d'arraignées", 72L),
                              Pair.of("Vous permettent d'attérir en douceur !", 43L)), true));

        /**
         * Chapter V, You turn !
         */
        this.dropperTutorial.addChapter(new TutorialChapter(LocationUtils.str2loc(getWorlds().get(0).getName() + ", " + object.get("step-2").getAsString()), "",
                Arrays.asList(Pair.of("Maintenant...", 72L),
                              Pair.of("À toi de jouer !", 43L)), true));


    }

}
