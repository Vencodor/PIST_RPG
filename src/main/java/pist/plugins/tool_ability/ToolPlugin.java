package main.java.pist.plugins.tool_ability;

import main.java.pist.api.function.util.NbtUtil;
import main.java.pist.manager.game.ability.Ability;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ToolPlugin {
	
	public static double getValue(Player p, Ability ability) {
		ItemStack item = p.getInventory().getItemInMainHand();
		if(item==null) {
			return 0;
		}
		return NbtUtil.getNBTDouble(item, ability+"");
	}
	
	public static double getArmorValue(Player p, Ability ability) {
		double var = 0;
		for(ItemStack a : p.getInventory().getArmorContents()) {
			if(a!=null) {
				var = var + NbtUtil.getNBTDouble(a, ability+"");
			}
		}
		return var;
	}
	
	/*
	public static double getValue(Player p, String key) {
		ItemStack item = p.getInventory().getItemInMainHand();
		if(item==null||!item.hasItemMeta()||item.getItemMeta().getLore()==null) {
			return 0;
		}
		List<String> lore = item.getItemMeta().getLore();
		return getValue(key, lore);
	}
	
	public static double getValue(String key,List<String> lore) {
		key = key.toLowerCase();
		for(String a : lore) {
			if(a.toLowerCase().contains(key)) {
				String val = ChatColor.stripColor(a)
						.replaceAll("[^0-9.]", "");
				try {
					return Double.parseDouble(val);
				} catch(NumberFormatException e) {
					e.printStackTrace();
					return 0;
				}
			}
		}
		return 0;
	}
	*/
	
}
