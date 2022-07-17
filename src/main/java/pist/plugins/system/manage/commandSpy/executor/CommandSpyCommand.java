package main.java.pist.plugins.system.manage.commandSpy.executor;

import main.java.pist.manager.plugin.CommandManager;
import main.java.pist.plugins.system.manage.commandSpy.CommandSpyPlugin;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CommandSpyCommand extends CommandManager {

	public CommandSpyCommand() {
		super(Arrays.asList("spy","스파이","엿보기","커맨드","cmd","command","커맨드보기","spycommand","sc"),true);
	}

	@Override
	public void onCommand(Player p, String label, String content, String[] args) {
		if(CommandSpyPlugin.setSpy(p)) {
			p.sendMessage(dgray+bold+"["+blue+bold+" SPY "+dgray+bold+"] "+white+"명령어 보기 모드를"
		+green+" 활성화 "+white+"하였습니다.");
		} else {
			p.sendMessage(dgray+bold+"["+blue+bold+" SPY "+dgray+bold+"] "+white+"명령어 보기 모드를"
		+red+" 비활성화 "+white+"하였습니다.");
		}
	}
	
}
