package main.java.pist.plugins.collect.grass;

import main.java.pist.api.function.util.NbtUtil;
import main.java.pist.api.object.ItemDTO;
import main.java.pist.manager.plugin.GuiManager;
import main.java.pist.plugins.collect.grass.object.CollectingGrassDTO;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Random;

public class CollectGrassPlugin {
	public static HashMap<String, CollectingGrassDTO> collecting = new HashMap<String, CollectingGrassDTO>();
	
	public static ItemDTO getRandomOre() { //Item nbt에 time달아서 주기
		GuiManager manager = new GuiManager();
		
		ItemStack item = manager.createItem(new ItemStack(Material.CHORUS_FLOWER), "장미");
		int time = 1;
		Random random = new Random();
		switch(random.nextInt(1)) {
			case 0:
				time = 5;
				break;
			case 1:
				break;
			default:
				break;
		}
		return new ItemDTO(NbtUtil.setNBT(item, "time", time+""));
	}
}
