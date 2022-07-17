package main.java.pist.plugins.system.macroTest.Inventory;

import main.java.pist.manager.plugin.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class MacroTestGUI extends GuiManager {
	
	public Inventory getMacroInv() {
		Inventory inv = Bukkit.createInventory(null, 9*5, red+"매크로 테스트");
		
		
		
		return inv;
	}
	
	
	
}
