package main.java.pist.plugins.quest.sub_quest;

import main.java.pist.plugins.quest.integrated.QuestIntegratedManager;
import main.java.pist.plugins.quest.integrated.object.PlayerQuestDTO;
import main.java.pist.plugins.quest.integrated.object.quests.HaveItemQuest;
import main.java.pist.plugins.quest.sub_quest.object.SubQuestDTO;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class SubQuestPlugin {
	public static HashMap<String,SubQuestDTO> quest = new HashMap<String,SubQuestDTO>();
	
	public static HashMap<String,String> editKey = new HashMap<String,String>();
	
	public static SubQuestDTO getQuest(String key) {
		return quest.get(key);
	}
	
	public static SubQuestDTO getQuestUseTitle(String title) {
		if(title==null)
			return null;
		for(SubQuestDTO a : quest.values()) {
			if(a.getTitle().equals(title))
				return a;
		}
		return null;
	}
	
	public static SubQuestDTO getQuestUseNpcName(String name) {
		if(name==null)
			return null;
		for(SubQuestDTO a : quest.values()) {
			if(a.getNpc()!=null&&name.contains(a.getNpc()))
				return a;
		}
		return null;
	}
	
	public static boolean updatePlayerQuest(Player p, PlayerQuestDTO quest, int i) {
		if(isQuestComplete(p, quest.getProgressSubQuest().get(i))) { //퀘스트 클리어
			p.sendTitle("서브 퀘스트 완료", "퀘스트 완료함", 1, 20*3, 1);
			p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 2);
			
			p.closeInventory();
			for(Object a : quest.getProgressSubQuest().get(i).getQuest()) {
				if(a instanceof HaveItemQuest) {
					try {
						Method m = a.getClass().getMethod("removeItem", Player.class);
						m.invoke(a, p);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
				}
			}
			
			QuestIntegratedManager.giveReward(p, quest.getProgressSubQuest().get(i).getReward());
			
			quest.getClearSubQuest().add(quest.getProgressSubQuest().get(i));
			quest.getProgressSubQuest().remove(i);
			
			return true;
		}
		return false;
	}
	
	public static boolean isQuestComplete(Player p, SubQuestDTO quest) {
		for(Object a : quest.getQuest()) {
			try {
				Method m = a.getClass().getMethod("isComplete",Player.class);
				if(!(Boolean)m.invoke(a,p)) {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public static boolean isContains(List<SubQuestDTO> list, SubQuestDTO quest) {
		for(SubQuestDTO a : list) {
			if(a.getKey().equals(quest.getKey()))
				return true;
		}
		return false;
	}
	
	public static void putNewQuest(String key) {
		quest.put(key, new SubQuestDTO(key));
	}
	
	public static boolean removeQuest(String key) {
		if(getQuest(key)!=null) {
			quest.remove(key);
			return true;
		}
		return false;
	}
}
