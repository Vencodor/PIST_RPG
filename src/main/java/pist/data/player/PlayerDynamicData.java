package main.java.pist.data.player;

import main.java.pist.data.player.object.PlayerDynamicDTO;

import java.util.UUID;

public class PlayerDynamicData {
	
	public static PlayerDynamicDTO getData(UUID uid) {
		return PlayerData.getData(uid).getDynamic();
	}
	
	
}
