package main.java.pist.plugins.customItem;

import main.java.pist.plugins.customItem.inventory.GUI_CustomItem;
import main.java.pist.plugins.customItem.object.CustomItemDTO;
import main.java.pist.plugins.customItem.object.LoreEditDTO;
import main.java.pist.plugins.customItem.object.enums.CustomType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CustomItemPlugin {
	public static ArrayList<CustomItemDTO> customItems = new ArrayList<CustomItemDTO>();
	public static HashMap<String,LoreEditDTO> edit = new HashMap<String,LoreEditDTO>();
	
	public static CustomItemDTO getCustom(String name) {
		for(CustomItemDTO a : customItems)
			if(a.getKey().equals(name))
				return a;
		return null;
	}
	
	public static void addCustom(String name) {
		customItems.add(new CustomItemDTO(name));
	}
	
	public static void removeCustom(String name) {
		for(CustomItemDTO a : customItems)
			if(a.getKey().equals(name))
				customItems.remove(a);
	}

	public static List<ItemStack> getContents(String name) {
		CustomItemDTO custom = getCustom(name);
		if(custom == null)
			return null;
		return custom.getContents();
	}
	
	public static ItemStack getRandomCustomItem(String name) {
		CustomItemDTO custom = getCustom(name);
		
		if(custom==null||custom.getType()!=CustomType.RANDOM)
			return null;
		
		GUI_CustomItem gui = new GUI_CustomItem();
		Inventory inv = gui.getCustomGUI(custom, 0);
		List<ItemStack> list = new ArrayList<ItemStack>();
		
		for(int k = 0; k<45; k++) {
			ItemStack a = inv.getItem(k);
			if(a==null||k%8==0)
				continue;
			
			if(k/8==0) { //low
				for(int j = 0; j<12; j++) //12 * 5, 70%
					list.add(a);
			} else if(k/8==1) { //medium
				for(int j = 0; j<5; j++) //5 * 5, 20%
					list.add(a);
			} else { //high
				for(int j = 0; j<1; j++) //1 * 5, 5%
					list.add(a);
			}
			
		}
		
		Random r = new Random();
		return list.get(r.nextInt(list.size()));
	}
	
}
