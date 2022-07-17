package main.java.pist.vencoder.server.listener;

import main.java.pist.api.function.util.DateUtil;
import main.java.pist.data.player.PlayerData;
import main.java.pist.plugins.stat.StatPlugin;
import main.java.pist.vencoder.PluginManager;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;

public class EventListener extends PluginManager implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		boolean firstJoin = PlayerData.putData(p.getUniqueId());
		if(firstJoin) {
			p.sendMessage(" ");
			p.sendMessage(gray+bold+" [ "+orange+bold+"PIST RPG"+gray+bold+" ]");
			p.sendMessage(orange+bold+" 환영합니다! "+white+"서버가 처음이시군요?");
			p.sendMessage(white+" 아직 서버에 대해 잘 모르는 당신을 위해 천천히 안내 해드릴게요.");
			p.sendMessage(dgray+" ( "+DateUtil.getHourDate(new Date())+")");
			p.sendMessage(" ");
		} else {
			PlayerData.getData(p.getUniqueId()).setName(p.getName());
		}
		
		int totalUsers = Bukkit.getOnlinePlayers().size();
		
		TextComponent text = new TextComponent(dgray+bold+"[ "+orange+bold+"+"+dgray+bold+" ] "+white+p.getName());
		text.setHoverEvent(new HoverEvent(Action.SHOW_TEXT
				, new ComponentBuilder(orange+"\n "+(totalUsers-1)+dgray+" -> "+orange+totalUsers+dgray+" ( "+green+"+"+dgray+" )  \n").create()));
		for(Player a : Bukkit.getOnlinePlayers())
			a.spigot().sendMessage(text);
		
		e.setJoinMessage(null);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		StatPlugin.removeEditInfo(p.getName());
		
		e.setQuitMessage(null);
	}
	
}
