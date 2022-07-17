package main.java.pist.plugins.system.macroTest;

import main.java.pist.plugins.system.macroTest.Inventory.MacroTestGUI;
import main.java.pist.vencoder.Main;
import main.java.pist.vencoder.PluginManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class MacroTestPlugin extends PluginManager {
	
	public static HashMap<String, Long> running = new HashMap<String, Long>();
	
	public static boolean runMacroTest(Player p) {
		running.put(p.getName(), System.currentTimeMillis());
		
		MacroTestGUI gui = new MacroTestGUI();
		p.openInventory(gui.getMacroInv());
		
		new BukkitRunnable() {
			@Override
			public void run() {
				if(isRunning(p)) {
					p.kickPlayer(ChatColor.RED+"서버에서 매크로는 경고 대상입니다.");
					running.remove(p.getName());
				}
			}
		}.runTaskLater(Main.getInstance(), 20*5);
		
		return true;
	}
	
	public static boolean isRunning(Player p) {
		return running.containsKey(p.getName());
	}
	
	
}
