package main.java.pist.plugins.system.money.executor;

import main.java.pist.data.player.PlayerData;
import main.java.pist.data.player.object.PlayerInfoDTO;
import main.java.pist.manager.game.PlayerManager;
import main.java.pist.manager.plugin.CommandManager;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class MoneyManageCommand extends CommandManager {

	public MoneyManageCommand() {
		super(Arrays.asList("돈관리","돈설정"));
	}

	@Override
	public void onCommand(Player p, String label, String content, String[] args) {
		if(args.length==0) {
			p.sendMessage(white+"");
			p.sendMessage(prefix+"/돈관리 설정 <Player> <Amount> "+gray+"플레이어의 돈을 설정합니다");
			p.sendMessage(white+"");
		} else {
			if(args[0].equalsIgnoreCase("설정")) {
				if(args.length<3) {
					p.sendMessage(cmdWrong+"/돈관리 설정 <Player> <Amount>");
				} else {
					Player target = PlayerManager.getOnline(args[1]);
					if(target==null) {
						p.sendMessage(cmdWrong+"플레이어가 존재하지 않습니다");
					} else {
						int amount = 0;
						try {
							amount = Integer.parseInt(args[2]);
						} catch(NumberFormatException e1) {
							p.sendMessage(cmdWrong+"숫자만 입력 가능합니다");
							return;
						}
						PlayerInfoDTO info = PlayerData.getData(p.getUniqueId()).getInfo();
						info.setMoney(amount);
						
						p.sendMessage(complete+"대상의 잔액이 "+orange+amount+white+"으로 설정되었습니다");
					}
				}
			}
			
		}
	}
	
}
