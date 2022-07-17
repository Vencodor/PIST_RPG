package main.java.pist.plugins.quest.integrated.listener;

import main.java.pist.data.player.PlayerData;
import main.java.pist.plugins.quest.integrated.object.PlayerQuestDTO;
import main.java.pist.plugins.quest.sub_quest.inventory.SubQuestGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class QuestGUI_Listener implements Listener {
	@EventHandler
	public void onQuestInvClick(InventoryClickEvent e) {
		String title = e.getInventory().getTitle();
		if(title.contains("퀘스트")&&title.contains("메인/서브/전직")) {
			e.setCancelled(true);
			
			Player p = (Player) e.getWhoClicked();
			PlayerQuestDTO quest = PlayerData.getData(p.getUniqueId()).getQuest();
			int slot = e.getSlot();
			if(30<=slot&&slot<=32) {
				if(quest.getProgressSubQuest().size()>slot-30) {
					p.openInventory((new SubQuestGUI()).getInfoInv(p, quest.getProgressSubQuest().get(slot-30)));
				}
			}
		}
	}
}
