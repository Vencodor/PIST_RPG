package main.java.pist.plugins.tool_ability.listener;

import main.java.pist.manager.game.PlayerUpdater;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class ToolUpdater implements Listener {
	
	@EventHandler
	public void toolChange(PlayerItemHeldEvent e) {
		PlayerUpdater.updateSpeed(e.getPlayer());
	}
	
}
