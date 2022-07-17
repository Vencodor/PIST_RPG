package main.java.pist.plugins.system.exp;

import main.java.pist.data.player.PlayerData;
import main.java.pist.data.player.object.PlayerInfoDTO;
import main.java.pist.manager.game.PlayerManager;
import org.bukkit.entity.Player;

public class ExpPlugin {
	public static void addExp(Player p,int amount) {
		PlayerInfoDTO info = PlayerData.getData(p.getUniqueId()).getInfo();
		info.setExp(info.getExp()+amount);
		
		PlayerManager.updateExp(p);
	}
}
