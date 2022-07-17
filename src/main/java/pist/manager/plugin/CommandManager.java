package main.java.pist.manager.plugin;

import main.java.pist.vencoder.PluginManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandManager extends PluginManager{
	public List<String> commands = new ArrayList<String>();
	public boolean opCmd = false;
	
	public CommandManager(List<String> commands) {
		this.commands = commands;
	}
	
	public CommandManager(List<String> commands, boolean opCmd) {
		this.commands = commands;
		this.opCmd = opCmd;
	}
	
	public List<String> getCommands() {
		return commands;
	}
	
	public boolean isOpCmd() {
		return opCmd;
	}
	
	public abstract void onCommand(Player p,String label,String content,String[] args);
}
