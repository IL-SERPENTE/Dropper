package net.samagames.dropper.playmode;

public enum PlayMode {
	
	UNSET(0),
	CHALLENGE(1),
	ENTERTAINMENT(2);
	
	private int value;
	PlayMode(int value){
		this.value = value;
	}

}
