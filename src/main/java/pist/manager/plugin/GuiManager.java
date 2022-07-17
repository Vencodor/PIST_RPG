package main.java.pist.manager.plugin;

import main.java.pist.util.Heads;
import main.java.pist.vencoder.PluginManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiManager extends PluginManager{
	
	public ItemStack createItem(ItemStack item, String name, List<String> lore) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	
	public ItemStack createItem(ItemStack item, String name) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		im.setLore(new ArrayList<String>());
		item.setItemMeta(im);
		return item;
	}
	
	public ItemStack createItem(ItemStack item) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(" ");
		im.setLore(new ArrayList<String>());
		item.setItemMeta(im);
		return item;
	}
	
	public ItemStack getPlayerInfoItem(Player p) {
		Heads icon = new Heads();
		ItemStack head = icon.getHead(icon.getHeadValue(p.getName()));
		
		List<String> infoLore = new ArrayList<String>();
		infoLore.add(" ");
		infoLore.add(white+"레벨 "+orange+bold+"1");
		infoLore.add(" ");
		ItemStack info = createItem(head,orange+bold+p.getName(),infoLore);
		
		return info;
	}
	
	public void setUI(Inventory i, int dura) {
		int slot = 0;
		slot = i.getSize()-9;
		i.setItem(slot, getUI(dura));
	}
	
	public ItemStack getUI(int dura) {
		ItemStack item = createItem(new ItemStack(Material.GOLD_HOE));
		item.setDurability((short)dura);
		
		setUnbreak(item);
		
		return item;
	}
	
	public void setUnbreak(ItemStack item) {
		ItemMeta im = item.getItemMeta();
		im.setUnbreakable(true);
		item.setItemMeta(im);
	}
	
}
