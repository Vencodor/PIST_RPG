package main.java.pist.plugins.quest.integrated;

import main.java.pist.data.player.PlayerData;
import main.java.pist.data.player.object.PlayerDTO;
import main.java.pist.data.player.object.PlayerInfoDTO;
import main.java.pist.plugins.quest.integrated.object.questDefault.QuestNeedDTO;
import main.java.pist.plugins.quest.integrated.object.questDefault.QuestRewardDTO;
import main.java.pist.plugins.quest.sub_quest.SubQuestPlugin;
import main.java.pist.plugins.system.money.MoneyPlugin;
import org.bukkit.entity.Player;

public class QuestIntegratedManager {
	
	public static void giveReward(Player p, QuestRewardDTO reward) {
		//give 아이템은 만들지 말것. 나중에 우편함 시스템이 제작되면 그때 give메소드 실행
		
		PlayerInfoDTO info = PlayerData.getData(p.getUniqueId()).getInfo();
		info.setExp(info.getExp()+reward.getRewardExp());
		
		MoneyPlugin.addMoney(p, reward.getRewardMoney());
	}
	
	public static boolean canProgress(Player p, QuestNeedDTO need) {
		PlayerDTO data = PlayerData.getData(p.getUniqueId());
		if(data.getInfo().getLevel() < need.getLevel())
			return false;
		
		if(need.getQuestKey()!=null) {
			if(!SubQuestPlugin.isContains(data.getQuest().getClearSubQuest(), SubQuestPlugin.getQuest(need.getQuestKey())))
				return false;
		}
		
		return true;
	}
	
}
