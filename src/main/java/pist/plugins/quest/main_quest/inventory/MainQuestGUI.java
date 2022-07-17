package main.java.pist.plugins.quest.main_quest.inventory;

import main.java.pist.manager.plugin.GuiManager;
import main.java.pist.plugins.quest.integrated.object.questDefault.QuestNeedDTO;
import main.java.pist.plugins.quest.integrated.object.questDefault.QuestRewardDTO;
import main.java.pist.plugins.quest.main_quest.MainQuestPlugin;
import main.java.pist.plugins.quest.main_quest.object.MainQuestDTO;
import main.java.pist.plugins.quest.sub_quest.SubQuestPlugin;
import main.java.pist.plugins.quest.sub_quest.object.SubQuestDTO;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class MainQuestGUI extends GuiManager {
	
	public Inventory getInfoInv(Player p, MainQuestDTO quest) {
		Inventory i = Bukkit.createInventory(null, 9*3, orange+bold+"[Q] "+orange+"'"+quest.getTitle()+"' "+gray+"메인퀘스트 정보");
		if(quest!=null) {
			i.setItem(13, getQuestItem(p, quest));
		}
		
		setUI(i,5);
		
		return i;
	}
	
	public ItemStack getQuestItem(Player p, MainQuestDTO quest) {
		ArrayList<String> lore = new ArrayList<String>();
		if(!quest.isTalk()) {
			lore = getFindNpcLore(quest);
		} else {
			lore = getQuestLore(p, quest);
		}
		String display = null;
//		if(MainQuestPlugin.isQuestComplete(quest)) {
//			display = orange+bold+"[Q] "+white+quest.getTitle()+green+bold+slant+line+" 완수! "+dgray+" 메인";
//		} else {
//			
//		}
		display = orange+bold+"[Q] "+white+quest.getTitle()+dgray+" 메인";
		ItemStack item = createItem(new ItemStack(Material.BOOK_AND_QUILL)
				,display,lore);
		return item;
	}
	
	public ArrayList<String> getFindNpcLore(MainQuestDTO quest) {
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(black+"ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		lore.add(black+"ㅡ"+dgray+"➤ "+gray+"의뢰인 : "+(quest.getNpc()==null?"없음":quest.getNpc()));
		lore.add(" ");
		lore.add(black+"ㅡ-."+dgray+"▪ "+gray+"의뢰인 "+orange+quest.getNpc()+gray+"을(를) 찾아가세요");
		lore.add(" ");
		QuestRewardDTO reward = quest.getReward();
		lore.add(black+"ㅡ-."+dgray+"▣ "+gray+reward.getRewardExp()+" "+dgray+"Exp, "+gray+reward.getRewardMoney()+" "+dgray+"G");
		lore.add(" ");
		QuestNeedDTO need = quest.getNeed();
		if(need.getLevel()>0) {
			lore.add(black+"ㅡ"+dred+bold+"! "+red+"조건 : "+need.getLevel()+"레벨 이상");
		}
		if(need.getQuestKey()!=null) {
			SubQuestDTO needQuest = SubQuestPlugin.getQuest(need.getQuestKey());
			if(needQuest!=null) {
				lore.add(black+"ㅡ"+dred+bold+"! "+red+"조건 : '"+needQuest.getTitle()+"' 퀘스트 클리어");
			}
		}
		lore.add(" ");
		
		return lore;
	}
	
	public ArrayList<String> getQuestLore(Player p, MainQuestDTO quest) {
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(black+"ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		lore.add(black+"ㅡ"+dgray+"➤ "+gray+"의뢰인 : "+(quest.getNpc()==null?"없음":quest.getNpc()));
		lore.add(" ");
		for(Object a : quest.getQuest()) {
			try {
				String description = (String) a.getClass().getField("description").get(a);
				if(!(Boolean)a.getClass().getMethod("isComplete",Player.class).invoke(a,p)) { //아직 완수 안했다면
					String process = (String) a.getClass().getMethod("getProcessBar",Player.class).invoke(a,p);
					double processPer = ((double) a.getClass().getMethod("getProcess",Player.class).invoke(a,p))*100;
					
					lore.add(black+"ㅡ-."+dgray+"▪ "+description+dgray+" "+process+" "+processPer+"%");
				} else { //완수 했다면
					lore.add(black+"ㅡ-."+dgray+"▪ "
				+gray+cancelLine+ChatColor.stripColor(description)+black+"ㅡ"+green+bold+"✔");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		lore.add(" ");
		QuestRewardDTO reward = quest.getReward();
		lore.add(black+"ㅡ-."+dgray+"▣ "+gray+reward.getRewardExp()+" "+dgray+"Exp, "+gray+reward.getRewardMoney()+" "+dgray+"G");
		lore.add(" ");
		if(MainQuestPlugin.isQuestComplete(p, quest)) {
			lore.add(black+"ㅡ-."+gray+"의뢰인을 찾아가 보상을 수령하세요");
			lore.add(" ");
		}
		/*
		QuestNeedDTO need = quest.getNeed();
		if(need.getLevel()>0) {
			lore.add(black+"ㅡ"+dred+bold+"! "+red+"조건 : "+need.getLevel()+"레벨 이상");
		}
		if(need.getQuestKey()!=null) {
			SubQuestDTO needQuest = SubQuestPlugin.getQuest(need.getQuestKey());
			if(needQuest!=null) {
				lore.add(black+"ㅡ"+dred+bold+"! "+red+"조건 : '"+needQuest.getTitle()+"' 퀘스트 클리어");
			}
		}
		lore.add(" ");
		*/
		
		return lore;
	}
	
}
