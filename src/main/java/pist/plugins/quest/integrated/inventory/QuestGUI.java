package main.java.pist.plugins.quest.integrated.inventory;

import main.java.pist.manager.plugin.GuiManager;
import main.java.pist.plugins.quest.integrated.object.PlayerQuestDTO;
import main.java.pist.plugins.quest.main_quest.MainQuestPlugin;
import main.java.pist.plugins.quest.main_quest.inventory.MainQuestGUI;
import main.java.pist.plugins.quest.main_quest.object.MainQuestDTO;
import main.java.pist.plugins.quest.sub_quest.inventory.SubQuestGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class QuestGUI extends GuiManager {
	
	public Inventory getQuestInv(Player p, PlayerQuestDTO quest) {
		Inventory i = Bukkit.createInventory(null, 9*6, orange+"[Q] 퀘스트 "+dgray+slant+"메인/서브/전직");
		i.setItem(10, createItem(new ItemStack(Material.BOOK_AND_QUILL),orange+bold+"⨠ "+gray+"메인퀘스트"));
		i.setItem(13, createItem(new ItemStack(Material.BOOK),orange+bold+"⨠ "+gray+"서브퀘스트"));
		i.setItem(16, createItem(new ItemStack(Material.FEATHER),orange+bold+"⨠ "+gray+"전직퀘스트"));
		
		if(quest.getProgressQuest()==null) { //끝까지 달성한 경우
			if(quest.getClearQuest().size()>0) {
				MainQuestDTO lastQ = quest.getClearQuest().get(quest.getClearQuest().size()-1); //마지막 달성 퀘스트
				if(lastQ.getNextQuest()!=null) { //다음 퀘스트가 있다면
					quest.setProgressQuest(MainQuestPlugin.getQuest(lastQ.getNextQuest()));
				}
			}
		}
		if(quest.getProgressQuest()!=null) {
			i.setItem(28, (new MainQuestGUI()).getQuestItem(p, quest.getProgressQuest()));
		} else {
			i.setItem(28, createItem(new ItemStack(Material.BARRIER),red+" 진행중인 메인 퀘스트가 없습니다 "
					,Arrays.asList(" ",black+"ㅡ"+orange+bold+"End. "+white+"다음 메인 퀘스트가 준비되지 않았습니다"+black+"ㅡㅡㅡㅡㅡㅡ"
							,black+"ㅡㅡㅡ-."+white+"빠른 시일내로 새로운 메인퀘스트를 들고올게요!"," ")));
		}
		
		SubQuestGUI subGUI = new SubQuestGUI();
		for(int k=0; k<3; k++) {
			if(quest.getProgressSubQuest().size()>k) {
				i.setItem(30+k, subGUI.getQuestItem(p, quest.getProgressSubQuest().get(k)));
			} else {
				i.setItem(30+k, createItem(new ItemStack(Material.STRUCTURE_VOID),red+" 해당 슬롯은 비어있습니다 "
						,Arrays.asList(" ",black+"ㅡ"+yellow+bold+"Tip. "+white+"마을을 돌아다니다 보면 퀘스트를 쉽게"+black+"ㅡㅡㅡ"
								,black+"ㅡㅡㅡ-."+white+"발견할 수 있습니다."," ")));
			}
		}
		
		if(quest.getProgressJobQuest()!=null) {
			i.setItem(34, null);
		} else {
			i.setItem(34, createItem(new ItemStack(Material.STRUCTURE_VOID),red+" 진행중인 전직 퀘스트가 없습니다 "
					,Arrays.asList(" ",black+"ㅡ"+yellow+bold+"Tip. "+white+"직업을 선택하거나 직업의 차수를 높힐 수 있는"+black+"ㅡㅡㅡ"
							,black+"ㅡㅡㅡ-."+white+"퀘스트입니다."," ")));
		}
		
		setUI(i,2);
		
		return i;
	}
	
}
