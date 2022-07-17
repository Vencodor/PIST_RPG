package main.java.pist.plugins.tool_ability.executor;

import main.java.pist.api.function.util.NbtUtil;
import main.java.pist.manager.game.ability.Ability;
import main.java.pist.manager.plugin.CommandManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ToolManageCommand extends CommandManager {

	public ToolManageCommand() {
		super(Arrays.asList("tool","능력","ability","도구"), true);
	}

	@Override
	public void onCommand(Player p, String label, String content, String[] args) {
		if(args.length==0) {
			p.sendMessage(" ");
			p.sendMessage(prefix+"/tool <ability> <strength> "+gray+"아이템의 능력치를 설정합니다");
			p.sendMessage(prefix+"/tool ability "+gray+"능력 목록을 확인합니다");
			p.sendMessage(" ");
		} else {
			if(args[0].equalsIgnoreCase("ability")&&args.length==1) {
				p.sendMessage(" ");
				for(Ability a : Ability.values()) {
					p.sendMessage(prefix+a+gray+"("+a.getDisplay()+")");
				}
				p.sendMessage(" ");
			} else if(args.length>=1) {
				int strength = 0;
				try {
					strength = Integer.parseInt(args[2]);
				} catch(NumberFormatException e) {
					p.sendMessage(cmdWrong+"<strength> 에는 숫자만 입력 가능합니다");
					return;
				}
				
				Ability ability = null;
				try {
					ability = Ability.valueOf(args[1]);
				} catch(Exception e) {
					p.sendMessage(cmdWrong+"존재하지 않는 능력입니다. "+gray+args[1	]);
					return;
				}
				
				if(setValue(p, ability+"", strength)) {
					p.sendMessage(complete+"무기에 "+orange+ability.getDisplay()+white+"가 "+orange+strength+white+"만큼 설정되었습니다");
				} else {
					p.sendMessage(cmdWrong+"설정하는 도중 오류가 발생하였습니다");
				}
				
			}
		}
	}
	
	private boolean setValue(Player p, String tag, int strength) {
		ItemStack hand = p.getInventory().getItemInMainHand();
		if(hand==null) {
			return false;
		}
		if(strength < 0) {
			return false;
		}
		
		if(strength == 0) {
			NbtUtil.removeNBT(hand, tag);
		} else {
			p.getInventory().setItemInMainHand(NbtUtil.setNBT(hand, tag, strength+""));
		}
		return true;
	}
	
}
