package main.java.pist.plugins.stat.executor;

import main.java.pist.data.player.PlayerData;
import main.java.pist.manager.game.PlayerManager;
import main.java.pist.manager.plugin.CommandManager;
import main.java.pist.plugins.stat.inventory.StatGUI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class StatManageCommand extends CommandManager {

	public StatManageCommand() {
		super(Arrays.asList("스탯관리","스텟관리","statm","sm"));
	}

	@Override
	public void onCommand(Player p, String label, String content, String[] args) {
		if(args.length==0) {
			p.sendMessage(prefix+"/스텟관리 포인트 <대상> <수량> "+gray+"대상의 포인트를 설정합니다");
			p.sendMessage(prefix+"/스텟관리 확인 <대상> "+gray+"대상의 스텟을 확인합니다");
		} else {
			if(args[0].equals("포인트")) {
				if(args.length<3) {
					p.sendMessage(cmdWrong+"/스텟관리 포인트 <대상> <수량>");
				} else {
					Player target = PlayerManager.getOnline(args[1]);
					if(target==null) {
						p.sendMessage(cmdWrong+"대상이 온라인이 아닙니다");
					} else {
						int amount = 0;
						try {
							amount = Integer.parseInt(args[2]);
						} catch(NumberFormatException e1) {
							p.sendMessage(cmdWrong+"수량에는 숫자만 적어주세요");
							return;
						}
						
						PlayerData.getData(target.getUniqueId()).getStat().setStatPoint(amount);
						p.sendMessage(complete+"대상의 포인트를 "+amount+"로 설정하였습니다");
					}
				}
			} else if(args[0].equals("확인")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/스텟관리 확인 <대상>");
				} else {
					Player target = PlayerManager.getOnline(args[1]);
					if(target==null) {
						p.sendMessage(cmdWrong+"대상이 온라인이 아닙니다");
					} else {
						StatGUI gui = new StatGUI();
						Inventory statInv = gui.getStatInv(p,PlayerData.getData(target.getUniqueId()).getStat(),false);
						p.openInventory(statInv);
					}
				}
			}
		}
	}
	
}
