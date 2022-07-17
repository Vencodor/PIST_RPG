package main.java.pist.plugins.system.exp.executor;

import main.java.pist.data.player.PlayerData;
import main.java.pist.data.player.object.PlayerInfoDTO;
import main.java.pist.manager.game.PlayerManager;
import main.java.pist.manager.game.info.PlayerExp;
import main.java.pist.manager.plugin.CommandManager;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ExpManageCommand extends CommandManager {

	public ExpManageCommand() {
		super(Arrays.asList("경험치관리","em","경험치"));
	}

	@Override
	public void onCommand(Player p, String label, String content, String[] args) {
		if(!p.isOp())
			return;
		if(args.length==0) {
			p.sendMessage(white+"");
			p.sendMessage(prefix+"/경험치 설정 <Player> <Amount> "+gray+"플레이어의 레벨을 설정합니다");
			p.sendMessage(prefix+"/경험치 배수 <Amount> "+gray+"경험치의 배수를 설정합니다");
			p.sendMessage(white+"");
		} else {
			if(args[0].equalsIgnoreCase("설정")) {
				if(args.length<3) {
					p.sendMessage(cmdWrong+"/레벨 설정 <Player> <Amount>");
				} else {
					Player target = PlayerManager.getOnline(args[1]);
					if(target==null) {
						p.sendMessage(cmdWrong+"플레이어가 존재하지 않습니다");
					} else {
						int level = 0;
						try {
							level = Integer.parseInt(args[2]);
						} catch(NumberFormatException e1) {
							p.sendMessage(cmdWrong+"숫자만 입력 가능합니다");
							return;
						}
						PlayerInfoDTO info = PlayerData.getData(p.getUniqueId()).getInfo();
						info.setLevel(level);
						info.setExp(PlayerExp.getExp(level));
						
						p.sendMessage(complete+"대상의 레벨이 "+orange+level+white+"으로 설정되었습니다");
					}
				}
			} else if(args[0].equalsIgnoreCase("배수")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/레벨 배수 <Amount>");
				} else {
					double multiply = 1.0;
					try {
						multiply = Double.parseDouble(args[1]);
					} catch(NumberFormatException e1) {
						p.sendMessage(cmdWrong+"숫자만 입력 가능합니다");
						return;
					}
					PlayerExp.expMutiply = multiply;
					
					p.sendMessage(complete+"경험치 배수가 "+orange+multiply+white+"으로 설정되었습니다");
				}
			}
		}
		
	}
	
}
