package main.java.pist.plugins.system.money;

import main.java.pist.data.player.PlayerData;
import main.java.pist.data.player.object.PlayerInfoDTO;
import org.bukkit.entity.Player;

public class MoneyPlugin {
	
	public static void addMoney(Player p,int amount) {
		PlayerInfoDTO info = PlayerData.getData(p.getUniqueId()).getInfo();
		info.setMoney(info.getMoney()+amount);
	}
	
}
