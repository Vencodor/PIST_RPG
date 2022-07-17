package main.java.pist.plugins.quest.integrated.object.quests;

import org.bukkit.entity.Player;

public class MobKillQuest extends QuestManager implements Cloneable {
	
	public String mobName = null;
	public int amount = 0;
	public String description = "";
	
	public int current = 0;
	
	public MobKillQuest(String mobName, int amount) {
		this.mobName = mobName;
		this.amount = amount;
		
		this.description = orange+mobName+gray+"을(를) "+green+amount+gray+"마리 잡기";
	}

	@Override
	public boolean isComplete(Player p) {
		return (current >= amount);
	}
	
	@Override
	public String getProcessBar(Player p) {
		return "( "+current+" / "+amount+" )";
	}

	@Override
	public double getProcess(Player p) {
		return (double)current / (double)amount;
	}
	
	public boolean isRightMob(String name) {
		return name.contains(mobName);
	}
	
	public void addCurrent() {
		current++;
	}
	
	@Override
	public MobKillQuest clone() throws CloneNotSupportedException{
		return (MobKillQuest)super.clone();
	}
	
}
