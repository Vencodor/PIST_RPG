package main.java.pist.api.function.util;

import main.java.pist.api.object.ItemDTO;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemUtil {
	
	public static List<ItemStack> toStackList(List<ItemDTO> list) {
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		for(ItemDTO a : list) {
			stacks.add(a.getItem());
		}
		return stacks;
	}
	
}
