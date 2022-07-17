package main.java.pist.vencoder.server.listener;

import main.java.pist.api.function.util.DateUtil;
import main.java.pist.manager.game.PlayerUpdater;
import main.java.pist.plugins.system.manage.chatClear.ChatClearPlugin;
import main.java.pist.vencoder.PluginManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChatListener extends PluginManager implements Listener {
	
	public static List<String> filterWords = new ArrayList<String>();
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		PlayerUpdater.updateHealth(p);
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		e.setCancelled(true);
		Player p = e.getPlayer();
		String msg = e.getMessage();
		
		for(String a : filterWords) {
			if(msg.contains(a)) {
				p.sendMessage(dred+bold+" <!> "+red+"입력하신 채팅에 욕설이 포함되어 있습니다");
				return;
			}
		}
		
		if(p.isOp())
			msg = msg.replace("&", "§");
		
		Date now = Calendar.getInstance().getTime();
		String sendTime = DateUtil.getHourDate(now);
		
		TextComponent base = new TextComponent();
		TextComponent profile = new TextComponent(" "+orange+p.getName());
		profile.setHoverEvent(new HoverEvent(Action.SHOW_TEXT
				, new ComponentBuilder(green+"\n 클릭하여 "+orange+p.getName()+green+"님의 "+aqua+bold+"정보를 확인합니다. \n").create()));
		HoverEvent textHover = new HoverEvent(Action.SHOW_TEXT
				, new ComponentBuilder(green+"\n 클릭하여 "+orange+p.getName()+green+"님의 채팅을 "+red+bold+"신고 합니다. \n"+gray+" "+sendTime+"\n").create());
		TextComponent text = null;
		
		Player to = containsOnline(msg);
		
		if(to!=null) {
			to.playSound(to.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1.55f);
			String target = to.getName();
			String[] args = msg.split(target,2);
			
			text = new TextComponent(args[0]);
			text.setHoverEvent(textHover);
			
			TextComponent text1 = new TextComponent(aqua+line+target+white);
			text1.setHoverEvent(new HoverEvent(Action.SHOW_TEXT
					, new ComponentBuilder(green+"\n 클릭하여 "+orange+target+green+"님의 "+aqua+bold+"정보를 확인합니다. \n"+gray+" * 참조된 유저입니다\n"+gray+" "+sendTime+"\n").create()));
			text.addExtra(text1);
			
			TextComponent text2 = new TextComponent(args[1]);
			text2.setHoverEvent(textHover);
			text.addExtra(text2);
		} else {
			text = new TextComponent(msg);
			text.setHoverEvent(textHover);
		}
		
		String division = dgray+" > "; //구분
		
		TextComponent opMsg = new TextComponent(base.duplicate());
		if(p.isOp()) {
			TextComponent cancel = new TextComponent(dred+bold+"·");
			cancel.setHoverEvent(new HoverEvent(Action.SHOW_TEXT
					, new ComponentBuilder(red+"\n 클릭하여 해당 채팅을 삭제합니다 \n").create()));
			cancel.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND
					, "/cc "+ChatClearPlugin.chatList.size()));
			opMsg.addExtra(cancel);
		}
		
		opMsg.addExtra(profile);
		opMsg.addExtra(division);
		opMsg.addExtra(text);
		
		base.addExtra(profile);
		base.addExtra(division);
		base.addExtra(text);
		
		ChatClearPlugin.addChat(new TextComponent(opMsg.duplicate()));
		
		for(Player a : Bukkit.getOnlinePlayers()) {
			if(a.isOp()) {
				a.spigot().sendMessage(opMsg);
			} else {
				a.spigot().sendMessage(base);
			}
		}
		
		
		System.out.println(white+p.getName()+" > "+msg);
		
	}
	
	private Player containsOnline(String name) {
		for(Player a : Bukkit.getOnlinePlayers())
			if(name.contains(a.getName()))
				return a;
		return null;
	}
	
}
