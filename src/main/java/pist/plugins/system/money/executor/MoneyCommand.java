package main.java.pist.plugins.system.money.executor;

import main.java.pist.api.function.util.FormatUtil;
import main.java.pist.data.player.PlayerData;
import main.java.pist.data.player.object.PlayerInfoDTO;
import main.java.pist.manager.plugin.CommandManager;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class MoneyCommand extends CommandManager {

	public MoneyCommand() {
		super(Arrays.asList("돈","money","골드","gold","ehs","ㄷ","ㄱㄷ","골","m"));
	}

	@Override
	public void onCommand(Player p, String label, String content, String[] args) {
		if(args.length==0) {
			PlayerInfoDTO info = PlayerData.getData(p.getUniqueId()).getInfo();
			
			p.sendMessage(white+"");
			p.sendMessage(prefix+gray+"잔액 "+orange+FormatUtil.format(info.getMoney())+yellow+bold+"G");
			p.sendMessage(white+"");
		} else {
			if(args[0].equalsIgnoreCase("순위")) {
				
			}
			
		}
	}
	
}
