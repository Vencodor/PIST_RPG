package main.java.pist.plugins.system.exp.executor;

import main.java.pist.api.function.util.FormatUtil;
import main.java.pist.data.player.PlayerData;
import main.java.pist.data.player.object.PlayerInfoDTO;
import main.java.pist.manager.game.info.PlayerExp;
import main.java.pist.manager.plugin.CommandManager;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ExpCommand extends CommandManager{

	public ExpCommand() {
		super(Arrays.asList("레벨","level","exp"));
	}

	@Override
	public void onCommand(Player p, String label, String content, String[] args) {
		PlayerInfoDTO info = PlayerData.getData(p.getUniqueId()).getInfo();
		double e = PlayerExp.getExp(info.getLevel());
		
		p.sendMessage(white+"");
		p.sendMessage(prefix+gray+"레벨 "+orange+info.getLevel());
		p.sendMessage(prefix+gray+"경험치 "+orange
				+FormatUtil.format(info.getExp()-e)+dgray+" / "+orange+FormatUtil.format(PlayerExp.getExp(info.getLevel()+1)-e));
		p.sendMessage(white+"");
	}
	
}
