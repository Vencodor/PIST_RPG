package main.java.pist.plugins.quest.integrated.listener;

import main.java.pist.data.player.PlayerData;
import main.java.pist.plugins.quest.integrated.object.PlayerQuestDTO;
import main.java.pist.plugins.quest.integrated.object.quests.MobKillQuest;
import main.java.pist.plugins.quest.main_quest.MainQuestPlugin;
import main.java.pist.plugins.quest.sub_quest.SubQuestPlugin;
import main.java.pist.plugins.quest.sub_quest.object.SubQuestDTO;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;

import java.lang.reflect.Method;

public class QuestUpdateListener implements Listener{
	
	@EventHandler
	public void onPickUp(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			PlayerQuestDTO quest = PlayerData.getData(p.getUniqueId()).getQuest();
			
			if(quest.getProgressQuest()!=null) {
				MainQuestPlugin.updatePlayerQuest(p, quest);
			}
			for(int i=0; i<quest.getProgressSubQuest().size(); i++) {
				SubQuestPlugin.updatePlayerQuest(p, quest, 0);
			}
			
		}
	}
	
	@EventHandler
	public void onKill(EntityDeathEvent e) {
		if(e.getEntity().getKiller() != null) {
			Player p = e.getEntity().getKiller();
			PlayerQuestDTO quest = PlayerData.getData(p.getUniqueId()).getQuest();
			
			if(quest.getProgressQuest()!=null) {
				for(Object a : quest.getProgressQuest().getQuest()) {
					if(a instanceof MobKillQuest) {
						try {
							Method isRight = a.getClass().getMethod("isRightMob", String.class);
							if((boolean)isRight.invoke(a, e.getEntity().getName())) {
								Method m = a.getClass().getMethod("addCurrent");
								m.invoke(a);
							}
							MainQuestPlugin.updatePlayerQuest(p, quest);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
			
			for(SubQuestDTO progress : quest.getProgressSubQuest()) {
				if(progress.getKey()!=null) {
					for(Object a : progress.getQuest()) {
						if(a instanceof MobKillQuest) {
							try {
								Method isRight = a.getClass().getMethod("isRightMob", String.class);
								if((boolean)isRight.invoke(a, e.getEntity().getName())) {
									Method m = a.getClass().getMethod("addCurrent");
									m.invoke(a);
								}
								SubQuestPlugin.updatePlayerQuest(p, quest, quest.getProgressSubQuest().indexOf(progress));
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
			
		}
	}
	
}
