package net.samagames.dropper;

import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class DropperBoard {
	
	/*
	 * This class manages the scoreboard.
	 */
	
    private ScoreboardManager scoreboardManager;
    private Scoreboard dropperScoreboard;
    private Objective dropperObjective;
    
    private Dropper instance;
    public DropperBoard(Dropper instance){
    	this.instance = instance;
    	
    	this.scoreboardManager = this.instance.getServer().getScoreboardManager();
        this.dropperScoreboard = this.scoreboardManager.getNewScoreboard();
        this.dropperObjective = this.dropperScoreboard.registerNewObjective("dropper", "dummy");
        this.dropperObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.dropperObjective.setDisplayName("Dropper");
    	
        this.dropperObjective.getScore("Test").setScore(10);
        
    }
    
    public Scoreboard getScoreboard(){
    	return this.dropperScoreboard;
    }

}
