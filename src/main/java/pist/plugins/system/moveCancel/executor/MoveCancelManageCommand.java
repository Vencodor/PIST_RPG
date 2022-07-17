package main.java.pist.plugins.system.moveCancel.executor;

import main.java.pist.api.function.util.NbtUtil;
import main.java.pist.manager.plugin.CommandManager;
import main.java.pist.manager.plugin.GuiManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class MoveCancelManageCommand extends CommandManager {

	public MoveCancelManageCommand() {
		super(Arrays.asList("mc","movecancel","제한","제한블럭"), true);
	}

	@Override
	public void onCommand(Player p, String label, String content, String[] args) {
		if(args.length<=0) {
			p.sendMessage(" ");
			p.sendMessage(prefix+"/mc block "+gray+"플레이어 앞을 가로막는 특수 블럭을 지급받습니다");
			p.sendMessage(" ");
		} else {
			if(args[0].equalsIgnoreCase("block")) {
				ItemStack block = (new GuiManager()).createItem(new ItemStack(Material.BARRIER));
				block = NbtUtil.setNBT(block, "movecancel", "true");
				
				p.getInventory().addItem(block);
				p.sendMessage(complete+"지급되었습니다");
			}
		}
	}
	
}
