package main.java.pist.plugins.system.manage.chatClear.executor;

import main.java.pist.manager.plugin.CommandManager;
import main.java.pist.plugins.system.manage.chatClear.ChatClearPlugin;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ChatClearExecutor extends CommandManager {

	public ChatClearExecutor() {
		super(Arrays.asList("chatclear","cc","채팅삭제","챗삭"),true);
	}

	@Override
	public void onCommand(Player p, String label, String content, String[] args) {
		if(p.isOp()) {
			if(args.length<1) {
				for(Player a : Bukkit.getOnlinePlayers()) {
					for(int i=0; i<=98; i++) {
						a.sendMessage(" ");
					}
					a.sendMessage(red+bold+" [!] "+orange+p.getName()+gray+"님이 채팅을 모두 삭제하셨습니다");
					a.sendMessage(" ");
					
					ChatClearPlugin.chatList.clear();
				}
			} else {
				int line = 0;
				try {
					line = Integer.parseInt(args[0]);
				} catch(Exception e) {
					return;
				}
				if(ChatClearPlugin.removeChat(line)) {
					for(Player a : Bukkit.getOnlinePlayers()) {
						for(int i=0; i<=100-ChatClearPlugin.chatList.size(); i++) {
							a.sendMessage(" ");
						}
						for(TextComponent b : ChatClearPlugin.chatList) {
							a.spigot().sendMessage(b);
						}
					}
				}
			}
			
		}
	}
	
	
	
}
