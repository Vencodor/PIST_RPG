package main.java.pist.plugins.system.manage.chatClear;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ChatClearPlugin {

	public static List<TextComponent> chatList = new ArrayList<TextComponent>();

	public static void addChat(TextComponent text) {
		chatList.add(text);
		if(chatList.size()>150) {
			chatList.remove(0);
		}
	}
	
	public static boolean removeChat(int line) {
		if(line>=0&&chatList.size()>line) {
			chatList.set(line, new TextComponent(ChatColor.RED+""+ChatColor.BOLD+" [!] "+ChatColor.GRAY+"관리자에 의해 삭제된 메시지입니다"));
		} else {
			return false;
		}
		return true;
	}
	
}
