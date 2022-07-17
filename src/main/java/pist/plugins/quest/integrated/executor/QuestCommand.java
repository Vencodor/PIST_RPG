package main.java.pist.plugins.quest.integrated.executor;

import main.java.pist.data.player.PlayerData;
import main.java.pist.manager.plugin.CommandManager;
import main.java.pist.plugins.quest.integrated.inventory.QuestGUI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class QuestCommand extends CommandManager {

	public QuestCommand() {
		super(Arrays.asList("q","quest","퀘","퀘스트","퀘스트"));
	}
		//퀘스트 저장하는거 만들기
	@Override
	public void onCommand(Player p, String label, String content, String[] args) {
		Inventory gui = (new QuestGUI()).getQuestInv(p, PlayerData.getData(p.getUniqueId()).getQuest());
		p.openInventory(gui);
	}
	
}
