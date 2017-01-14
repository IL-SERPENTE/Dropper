package net.samagames.dropper.level;

public class DropperLevel {
	
	private int levelID;
	private String levelName, levelDescription;
	
	public DropperLevel(int levelID, String levelName, String levelDescription){
		this.levelID = levelID;
		this.levelName = levelName;
		this.levelDescription = levelDescription;
	}
	
	public int getID(){
		return this.levelID;
	}
	
	public String getName(){
		return this.levelName;
	}
	
	public String getDescription(){
		return this.levelDescription;
	}

}
