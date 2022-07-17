package main.java.pist.api.function.util;

import main.java.pist.api.object.ItemDTO;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InventoryUtil {

	public static boolean haveItem(Inventory i, ItemDTO itemDTO) {
		ItemDTO item = null;
		try {
			item = itemDTO.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		ItemStack[] contents = contentClone(i);
		for (ItemStack b : contents) {
			if (b == null)
				continue;
			if (item.equalsWithoutLore(new ItemDTO(b))) {
				int removeAmount = b.getAmount() - item.getAmount();
				b.setAmount(removeAmount >= 0 ? removeAmount : 0);
				if (removeAmount >= 0) {
					return true;
				} else {
					item.setAmount(removeAmount * -1);
				}
			} else {
				
			}
		}
		return false;
	}

	public static boolean haveItems(Inventory i, List<ItemDTO> items) {
		List<ItemDTO> list = new ArrayList<ItemDTO>(items);

		ItemStack[] contents = contentClone(i);
		for (Iterator<ItemDTO> itr = list.iterator(); itr.hasNext();) {
			ItemDTO a = itr.next();
			for (ItemStack b : contents) {
				if (b == null)
					continue;
				if (a.equals(new ItemDTO(b))) {
					int removeAmount = b.getAmount() - a.getAmount();
					b.setAmount(removeAmount >= 0 ? removeAmount : 0);
					if (removeAmount >= 0) {
						itr.remove();
						if(list.size()==0)
							return true;
					} else {
						a.setAmount(removeAmount * -1);
					}
				} else {

				}
			}
		}
		return list.size() == 0;
	}

	public static boolean removeItems(Player p, List<ItemDTO> items) {
		List<ItemDTO> list = new ArrayList<ItemDTO>(items);

		ItemStack[] contents = p.getInventory().getContents();
		for (Iterator<ItemDTO> itr = list.iterator(); itr.hasNext();) {
			ItemDTO a = itr.next();
			for (ItemStack b : contents) {
				if (b == null)
					continue;
				if (a.equals(new ItemDTO(b))) {
					int removeAmount = b.getAmount() - a.getAmount();
					b.setAmount(removeAmount >= 0 ? removeAmount : 0);
					if (removeAmount >= 0) {
						itr.remove();
						if(list.size()==0)
							return true;
					} else {
						a.setAmount(removeAmount * -1);
					}
				} else {

				}
			}
		}
		// p.getInventory().setContents(contents);

		return true;
	}

	private static ItemStack[] contentClone(Inventory inv) {
		List<ItemStack> list = new ArrayList<ItemStack>();
		for (ItemStack a : inv.getContents())
			if (a != null)
				list.add(a.clone());
		return list.toArray(new ItemStack[list.size()]);
	}

}
