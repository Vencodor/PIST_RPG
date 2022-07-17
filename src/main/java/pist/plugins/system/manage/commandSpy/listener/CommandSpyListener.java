package main.java.pist.plugins.system.manage.commandSpy.listener;

import main.java.pist.plugins.system.manage.commandSpy.CommandSpyPlugin;
import main.java.pist.vencoder.PluginManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandSpyListener extends PluginManager implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST) 
	public void onCommand(PlayerCommandPreprocessEvent e) {
		for(Player a : CommandSpyPlugin.enableSpy) {
			if(e.getPlayer() == a) {
				a.sendMessage(gray+" 나"+blue+bold+" >"+dgray+bold+"> "+gray+e.getMessage());
			} else {
				if(a.isOp()) {
					a.sendMessage(" "+gray+e.getPlayer().getName()+dgray+"("+red+"OP"+dgray+")"+blue+bold+" >"+dgray+bold+"> "+gray+e.getMessage());
				} else {
					a.sendMessage(" "+gray+e.getPlayer().getName()+blue+bold+" >"+dgray+bold+"> "+gray+e.getMessage());
				}
			}
		}
	}
	
}
