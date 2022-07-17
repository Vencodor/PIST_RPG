package main.java.pist.plugins.quest.main_quest.listener;

import main.java.pist.vencoder.PluginManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MainQuestGUI_Listener extends PluginManager implements Listener{
	
	@EventHandler
	public void onClickInfoInv(InventoryClickEvent e) {
		String title = e.getInventory().getTitle();
		if(title.contains("메인퀘스트")&&title.contains("정보")) {
			//Player p = (Player) e.getWhoClicked();
			e.setCancelled(true);
			
			
			
		}
	}
	
}
