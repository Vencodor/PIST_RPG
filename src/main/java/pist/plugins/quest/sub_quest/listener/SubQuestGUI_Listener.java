package main.java.pist.plugins.quest.sub_quest.listener;

import main.java.pist.data.player.PlayerData;
import main.java.pist.plugins.quest.integrated.object.PlayerQuestDTO;
import main.java.pist.plugins.quest.sub_quest.SubQuestPlugin;
import main.java.pist.plugins.quest.sub_quest.inventory.SubQuestGUI;
import main.java.pist.plugins.quest.sub_quest.object.SubQuestDTO;
import main.java.pist.vencoder.PluginManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SubQuestGUI_Listener extends PluginManager implements Listener{
	
	@EventHandler
	public void onClickInfoInv(InventoryClickEvent e) {
		String title = e.getInventory().getTitle();
		if(title.contains("퀘스트")&&title.contains("정보")) {
			Player p = (Player) e.getWhoClicked();
			e.setCancelled(true);
			
			PlayerQuestDTO playerQuest = PlayerData.getData(p.getUniqueId()).getQuest();
			if(e.getSlot()==15) { //포기
				if(playerQuest.getProgressSubQuest().size()>0) {
					SubQuestDTO quest = SubQuestPlugin.getQuestUseTitle(title.split("'",3)[1]);
					
					if(SubQuestPlugin.updatePlayerQuest(p, playerQuest
							, playerQuest.getProgressSubQuest().indexOf(quest))) {
						p.openInventory((new SubQuestGUI()).getConfirmGiveUp(p, quest));
					}
					
//					for(SubQuestDTO a : playerQuest.getProgressSubQuest()) {
//						if(a.getKey().equals(quest.getKey())) {
//							SubQuestPlugin.updatePlayerQuest(p, playerQuest, playerQuest.getProgressSubQuest().indexOf(a));
//						}
//						
//					}
					
				}
			}
			
		}
	}
	
	
	@EventHandler
	public void onClickAgreeInv(InventoryClickEvent e) {
		String title = e.getInventory().getTitle();
		if(title.contains("퀘스트")&&title.contains("수락")&&title.contains("거절")) {
			Player p = (Player) e.getWhoClicked();
			e.setCancelled(true);
			
			if(e.getSlot()==20) { //수락
				p.closeInventory();
				
				PlayerQuestDTO playerQuest = PlayerData.getData(p.getUniqueId()).getQuest();
				if(playerQuest.getProgressSubQuest().size()>=3) {
					p.sendMessage(cmdWrong+"서브 퀘스트는 동시에 4개이상 진행할 수 없습니다");
					p.closeInventory();
				}
				
				String questTitle = title.split("'")[1];
				if(questTitle==null)
					return;
				SubQuestDTO quest = SubQuestPlugin.getQuestUseTitle(questTitle);
				if(quest==null)
					return;
				try {
					playerQuest.getProgressSubQuest().add(quest.clone());
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}
				
				p.sendTitle("퀘스트 수락", "퀘스트 수락함", 1, 20*3, 1);
			} else if(e.getSlot()==24) { //거절
				p.closeInventory();
			}
		}
	}
	
	@EventHandler
	public void onClickGiveUpInv(InventoryClickEvent e) {
		String title = e.getInventory().getTitle();
		if(title.contains("퀘스트 포기")) {
			Player p = (Player) e.getWhoClicked();
			e.setCancelled(true);
			 
			PlayerQuestDTO playerQuestDTO = PlayerData.getData(p.getUniqueId()).getQuest();
			SubQuestDTO targetSub = SubQuestPlugin.getQuestUseTitle(title.split("'",3)[1]);
			if(targetSub==null)
				return;
			if(e.getSlot()==0||e.getSlot()==8)
				return;
			
			if(e.getSlot()<4) { //포기
				p.closeInventory();
				
				p.sendMessage(prefix+"진행중인 서브 퀘스트를 포기하였습니다");
				for(SubQuestDTO a : playerQuestDTO.getProgressSubQuest()) {
					if(a.getKey().equals(targetSub.getKey())) {
						playerQuestDTO.getProgressSubQuest().remove(a);
					}
				}
			} else if(e.getSlot()>4) { //취소
				p.openInventory((new SubQuestGUI()).getInfoInv(p, targetSub));
			}
		}
	}
}
