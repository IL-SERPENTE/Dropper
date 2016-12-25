package net.samagames.dropper;

import net.samagames.api.games.Game;

/**
 * @author Vialonyx
 */

public class DropperGame extends Game<DropperPlayer> {

    public DropperGame(String gameCodeName, String gameName, String gameDescription, Class<DropperPlayer> gamePlayerClass) {
        super(gameCodeName, gameName, gameDescription, gamePlayerClass);
    }

    @Override
    public void startGame(){
        super.startGame();
    }

}
