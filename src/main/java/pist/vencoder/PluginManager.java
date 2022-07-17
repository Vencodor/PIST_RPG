package main.java.pist.vencoder;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class PluginManager {
	
	public String bold = ChatColor.BOLD + "";
	public String cancelLine = ChatColor.STRIKETHROUGH + "";
	public String line = ChatColor.UNDERLINE + "";
	public String slant = ChatColor.ITALIC + "";
	
	public String white = ChatColor.WHITE + "";
	public String black = ChatColor.BLACK + "";
	public String red = ChatColor.RED + "";
	public String dred = ChatColor.DARK_RED + "";
	public String orange = ChatColor.GOLD + "";
	public String yellow = ChatColor.YELLOW + "";
	public String green = ChatColor.GREEN + "";
	public String dgreen = ChatColor.DARK_GREEN + "";
	public String aqua = ChatColor.AQUA + "";
	public String blue = ChatColor.BLUE + "";
	public String dblue = ChatColor.DARK_BLUE + "";
	public String gray = ChatColor.GRAY + "";
	public String dgray = ChatColor.DARK_GRAY + "";
	public String purple = ChatColor.LIGHT_PURPLE + "";
	public String dpurple = ChatColor.DARK_PURPLE + "";
	
	public String prefix = gray+bold+"[ "+aqua+bold+"PIST"+gray+bold+" ] "+white;
	public String cmdWrong = gray+bold+"[ "+dred+bold+"Wrong"+gray+bold+" ] "+red;
	public String permission = gray+bold+"[ "+red+bold+"Permission"+gray+bold+" ] "+red;
	public String complete = gray+bold+"[ "+green+bold+"Complete"+gray+bold+" ] "+white;
	
	public void say(String msg) {
		for(Player a : Bukkit.getOnlinePlayers())
			a.sendMessage(msg);
	}
	
	public void say(TextComponent msg) {
		for(Player a : Bukkit.getOnlinePlayers())
			a.spigot().sendMessage(msg);
	}
	
	public void sendTitle(Player p, String msg, String subMsg) {
		p.sendTitle(msg, subMsg, 1, 20*2, 1);
	}
	
}
