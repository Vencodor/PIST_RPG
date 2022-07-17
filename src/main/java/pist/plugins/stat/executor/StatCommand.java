package main.java.pist.plugins.stat.executor;

import main.java.pist.data.player.PlayerData;
import main.java.pist.manager.plugin.CommandManager;
import main.java.pist.plugins.stat.inventory.StatGUI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class StatCommand extends CommandManager {
	
	public StatCommand() {
		super(Arrays.asList("스텟","스탯","ㅅㅌ","stat","tmxpt"));
	}

	public void onCommand(Player p, String label, String content, String[] args) {
			StatGUI gui = new StatGUI();
			Inventory statInv = gui.getStatInv(p
					,PlayerData.getData(p.getUniqueId()).getStat(),true);
			p.openInventory(statInv);
			//InventoryAnimation.run(statInv, InventoryAnimations.SLIDE_DOWN, Material.GOLD_HOE);
	}
	
}
