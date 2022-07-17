package main.java.pist.manager.game;

import main.java.pist.data.player.PlayerData;
import main.java.pist.data.player.object.PlayerDTO;
import main.java.pist.data.player.object.PlayerInfoDTO;
import main.java.pist.manager.game.info.PlayerExp;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class PlayerManager {
	
	public static Player getOnline(String name) {
		for(Player a : Bukkit.getOnlinePlayers()) {
			if(a.getName().equals(name))
				return a;
		}
		return null;
	}
	
	public static void updateMp(Player p) {
//		PlayerDynamicDTO dynamic = PlayerDynamicData.getData(p.getUniqueId());
//		p.setLevel((int)dynamic.getMp());
//		p.setExp((float)(dynamic.getMp()/dynamic.getMaxMp()));
	}
	
	public static void updateExp(Player p) {
		PlayerDTO data = PlayerData.getData(p.getUniqueId());
		PlayerInfoDTO info = data.getInfo();
		if(info.getExp()>=PlayerExp.getExp(info.getLevel()+1)) { //레벨업
			info.setLevel(info.getLevel()+1);
			
			data.getStat().setStatPoint(data.getStat().getStatPoint()+1);
			p.sendTitle("레벨 올라감", (info.getLevel()-1)+" >> "+info.getLevel(), 0, 40, 0);
		}
		
		p.setLevel(info.getLevel());
		if(info.getExp()==0) {
			p.setExp(0);
		} else {
			float per = (float) (info.getExp()/PlayerExp.getExp(info.getLevel()+1));
			p.setExp(per<=1?per:1);
		}
	}
	
	public static void sendActionBar(Player p,String message) {
		p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
	}
	
}
