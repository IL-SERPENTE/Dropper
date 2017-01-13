package net.samagames.dropper;

import net.samagames.api.games.Game;

public class Dropper extends Game<DropperPlayer> {
	
	private DropperMain instance;
	
	 public Dropper(String gameCodeName, String gameName, String gameDescription, Class<DropperPlayer> gamePlayerClass, DropperMain instance) {
		 super(gameCodeName, gameName, gameDescription, gamePlayerClass);
	 }
	 
	 public DropperMain getMainInstance(){
		 return this.instance;
	 }
	
}
