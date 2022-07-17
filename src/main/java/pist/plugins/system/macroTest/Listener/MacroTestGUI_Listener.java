package main.java.pist.plugins.system.macroTest.Listener;

import main.java.pist.plugins.system.macroTest.Inventory.MacroTestGUI;
import main.java.pist.plugins.system.macroTest.MacroTestPlugin;
import main.java.pist.vencoder.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class MacroTestGUI_Listener implements Listener {
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		
		if(MacroTestPlugin.isRunning(p)) {
			new BukkitRunnable() {
				
				@Override
				public void run() {
					p.openInventory((new MacroTestGUI()).getMacroInv());
				}
			}.runTaskLater(Main.getInstance(), 1);
		}
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		if(MacroTestPlugin.isRunning(p)) {
			MacroTestPlugin.running.remove(p.getName());
			p.closeInventory();
		}
	}
	
}
