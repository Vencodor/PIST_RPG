package main.java.pist.plugins.quest.main_quest;

import main.java.pist.api.animation.text.TitleAnimation;
import main.java.pist.api.animation.text.TitleAnimations;
import main.java.pist.data.player.PlayerData;
import main.java.pist.plugins.quest.integrated.object.PlayerQuestDTO;
import main.java.pist.plugins.quest.main_quest.object.MainQuestDTO;
import main.java.pist.vencoder.PluginManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainQuestPlugin extends PluginManager{
	public static HashMap<String,MainQuestDTO> quest = new HashMap<String,MainQuestDTO>();
	
	public static HashMap<String,String> editKey = new HashMap<String,String>();
	
	public static MainQuestDTO getQuest(String key) {
		return quest.get(key);
	}
	
	public static MainQuestDTO getQuestUseTitle(String title) {
		if(title==null)
			return null;
		for(MainQuestDTO a : quest.values()) {
			if(a.getTitle().equals(title))
				return a;
		}
		return null;
	}
	
	public static List<MainQuestDTO> getQuestUseNpcName(PlayerQuestDTO playerQuest, String name) {
		if(name==null)
			return null;
		List<MainQuestDTO> list = new ArrayList<MainQuestDTO>();
		for(MainQuestDTO a : quest.values()) {
			if(a.getNpc()==null)
				continue;
			if(isContains(playerQuest.getClearQuest(), a)) //이미 깬 퀘스트면
				continue;
			if(name.contains(a.getNpc()))
				list.add(a);
		}
		return list;
	}
	
	public static MainQuestDTO getQuestUseNpcTopPriorty(PlayerQuestDTO playerQuest, String name) {
		List<MainQuestDTO> list = getQuestUseNpcName(playerQuest, name);
		if(list.size()==0)
			return null;
		MainQuestDTO topPriorty = list.get(0);
		for(MainQuestDTO a : list) {
			if(topPriorty.getPriorty()<a.getPriorty()) {
				topPriorty = a;
			}
		}
		return topPriorty;
	}
	
	public static boolean setQuest(Player p, MainQuestDTO quest) {
		PlayerQuestDTO playerQuest = PlayerData.getData(p.getUniqueId()).getQuest();
		if(playerQuest.getProgressQuest()!=null&&playerQuest.getClearQuest().contains(quest)) {
			return false;
		}
		quest.setTalk(true); //대화를 했는가
		playerQuest.setProgressQuest(quest);
		return true;
	}
	
	public static void updatePlayerQuest(Player p, PlayerQuestDTO quest) {
		MainQuestDTO mainQuest = quest.getProgressQuest();
		if(isQuestComplete(p, mainQuest)) { //퀘스트 클리어
			TitleAnimation.run(p
					,ChatColor.YELLOW+"메인퀘스트"+ChatColor.GRAY+"를 클리어 하였습니다. '"
			+ChatColor.GOLD+mainQuest.getNpc()+ChatColor.GRAY+"' 에게 찾아가세요.",TitleAnimations.CLEAR);
			//p.sendTitle("메인 퀘스트 완료", "npc에게 찾아가세요", 1, 20*3, 1);
			
			//스코어보드 퀘스트 업데이트
		}
	}
	
	public static Entity getQuestNpc(MainQuestDTO quest) {
		if(quest.getNpc()==null)
			return null;
		for(World a : Bukkit.getWorlds()) {
			for(Entity b : a.getEntities()) {
				if(b instanceof LivingEntity) {
					if(b.getName().contains(quest.getNpc())) {
						return b;
					}
				}
			}
		}
		return null;
	}
	
	public static List<MainQuestDTO> getBeforeQuest(MainQuestDTO q) {
		List<MainQuestDTO> list = new ArrayList<MainQuestDTO>();
		for(MainQuestDTO a : quest.values()) {
			if(a.getNextQuest()!=null) {
				if(a.getNextQuest().equals(q.getKey())) {
					list.add(a);
				}
			}
		}
		return list;
	}
	
	public static boolean isClearBeforeQuest(Player p, MainQuestDTO q) {
		List<MainQuestDTO> clearQuests = PlayerData.getData(p.getUniqueId()).getQuest().getClearQuest();
		for(MainQuestDTO a : getBeforeQuest(q)) {
			if(!isContains(clearQuests, a))
					return false;
		}
		return true;
	}
	
	public static boolean isQuestComplete(Player p, MainQuestDTO quest) {
		for(Object a : quest.getQuest()) {
			try {
				Method m = a.getClass().getMethod("isComplete",Player.class);
				if(!(Boolean)m.invoke(a, p)) {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public static boolean isContains(List<MainQuestDTO> list, MainQuestDTO quest) {
		for(MainQuestDTO a : list) {
			if(a.getKey().equals(quest.getKey()))
				return true;
		}
		return false;
	}
	
	public static void putNewQuest(String key) {
		quest.put(key, new MainQuestDTO(key));
	}
	
	public static boolean removeQuest(String key) {
		if(getQuest(key)!=null) {
			quest.remove(key);
			return true;
		}
		return false;
	}
}
