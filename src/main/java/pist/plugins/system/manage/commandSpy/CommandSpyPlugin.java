package main.java.pist.plugins.system.manage.commandSpy;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandSpyPlugin {
	
	public static List<Player> enableSpy = new ArrayList<Player>();
	
	public static boolean setSpy(Player p) {
		if(enableSpy.contains(p)) {
			enableSpy.remove(p);
			return false;
		} else if(!p.isOp()) {
			return false;
		} else {
			enableSpy.add(p);
		}
		return true;
	}
	
}
