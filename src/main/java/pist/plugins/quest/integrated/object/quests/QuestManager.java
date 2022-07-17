package main.java.pist.plugins.quest.integrated.object.quests;

import main.java.pist.vencoder.PluginManager;
import org.bukkit.entity.Player;

public abstract class QuestManager extends PluginManager implements Cloneable {
	
	public abstract boolean isComplete(Player p);
	
	public abstract double getProcess(Player p);
	
	public abstract String getProcessBar(Player p);
}
