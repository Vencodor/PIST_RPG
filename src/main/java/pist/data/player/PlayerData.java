package main.java.pist.data.player;

import main.java.pist.data.player.object.PlayerDTO;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerData {
	public static HashMap<UUID,PlayerDTO> playerData = new HashMap<UUID,PlayerDTO>();
	
	public static PlayerDTO getData(UUID uid) {
		putData(uid);
		return playerData.get(uid);
	}

	public static PlayerDTO getData(String name) {
		for(PlayerDTO a : playerData.values()) {
			if(name.equals(a.getName())) {
				return a;
			}
		}
		return null;
	}
	
	public static PlayerDTO getData(Player p) {
		return getData(p.getUniqueId());
	}
	
	public static boolean putData(UUID uid) {
		if(playerData.get(uid)!=null)
			return false;
		playerData.put(uid, new PlayerDTO(uid));
		return true;
	}
	
	public static boolean saveData() {
		for(UUID a : playerData.keySet()) {
			PlayerDTO b = playerData.get(a);
			if(b==null) continue;
			try {
				//PlayerStatDAO.write(a, b.getStat());
				
			} catch(Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public static boolean loadData() {
		
		return true;
	}
	
}
