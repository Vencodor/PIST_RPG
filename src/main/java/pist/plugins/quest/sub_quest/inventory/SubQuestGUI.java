package main.java.pist.plugins.quest.sub_quest.inventory;

import main.java.pist.manager.plugin.GuiManager;
import main.java.pist.plugins.quest.integrated.object.questDefault.QuestNeedDTO;
import main.java.pist.plugins.quest.integrated.object.questDefault.QuestRewardDTO;
import main.java.pist.plugins.quest.sub_quest.SubQuestPlugin;
import main.java.pist.plugins.quest.sub_quest.object.SubQuestDTO;
import main.java.pist.util.HeadEnum;
import main.java.pist.util.Heads;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class SubQuestGUI extends GuiManager {
	
	public Inventory getInfoInv(Player p, SubQuestDTO quest) {
		Inventory i = Bukkit.createInventory(null, 9*3, orange+bold+"[Q] "+orange+"'"+quest.getTitle()+"' "+gray+"퀘스트 정보");
		if(quest!=null) {
			i.setItem(11, getQuestItem(p, quest));
		}
		Heads icon = new Heads();
		i.setItem(15, createItem(icon.getHead(HeadEnum.RED_X.getValue()),red+" 포기 "
				,Arrays.asList(" ",black+"ㅡ"+red+"현재 퀘스트를 포기합니다."+black+"ㅡ", " ")));
		
		setUI(i, 5);
		
		return i;
	}
	
	public Inventory getAgreeInv(Player p, SubQuestDTO quest) {
		Inventory i = Bukkit.createInventory(null, 9*4, orange+bold+"[Q] "+orange+"'"+quest.getTitle()+"' "+gray+"퀘스트 "+green+"수락"+dgray+"/"+red+"거절");
		i.setItem(13, getQuestItem(p, quest));
		Heads icon = new Heads();
		i.setItem(20, createItem(icon.getHead(HeadEnum.GRREN_CHECK.getValue()),green+" 수락 "));
		i.setItem(24, createItem(icon.getHead(HeadEnum.RED_X.getValue()),red+" 거절 "));
		
		setUI(i, 4);
		
		return i;
	}
	
	public Inventory getConfirmGiveUp(Player p, SubQuestDTO quest) {
		Inventory i = Bukkit.createInventory(null, 9*1, orange+bold+"[Q] "+orange+"'"+quest.getTitle()+"' "+red+"퀘스트 포기");
		i.setItem(4, getQuestItem(p, quest));
		ItemStack giveUp = createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)14),red+" 퀘스트를 포기합니다 ");
		ItemStack cancel = createItem(new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)13),green+" 퀘스트를 포기하지 않습니다 ");
		
		for(int k=1; k<4; k++)
			i.setItem(k, giveUp);
		for(int k=5; k<8; k++)
			i.setItem(k, cancel);
		
		setUI(i, 6);
		
		return i;
	}
	
	public ItemStack getQuestItem(Player p, SubQuestDTO quest) {
		ItemStack item = createItem(new ItemStack(Material.BOOK_AND_QUILL)
				,orange+bold+"[Q] "+white+quest.getTitle()+dgray+" 서브",getLore(p, quest));
		return item;
	}
	
	public ArrayList<String> getLore(Player p, SubQuestDTO quest) {
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
	
}
