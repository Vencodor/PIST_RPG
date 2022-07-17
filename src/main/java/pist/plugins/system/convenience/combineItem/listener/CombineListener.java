package main.java.pist.plugins.system.convenience.combineItem.listener;

import main.java.pist.api.object.ItemDTO;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CombineListener implements Listener {
	
	@EventHandler
	public void onClickInventory(InventoryClickEvent e) {
		Inventory inv = e.getClickedInventory();
		if(inv==null)
			return;
		
		if(inv.getType() == InventoryType.PLAYER && (e.getClick() == ClickType.MIDDLE)) {
			Player p = (Player) e.getWhoClicked();
			ItemStack cursor = e.getCurrentItem();
			
			if(cursor==null)
				return;
			if(!(cursor.getType()==Material.IRON_AXE||cursor.getType()==Material.IRON_PICKAXE
					||cursor.getType()==Material.IRON_HOE))
				return;
			e.setCancelled(true);
			
			ItemDTO cursorItem = new ItemDTO(cursor);
			cursorItem.setAmount(0);
			
			for(ItemStack a : p.getInventory().getContents()) {
				if(a!=null) {
					if(cursorItem.equals(new ItemDTO(a))) {
						cursorItem.setAmount(cursorItem.getAmount()+a.getAmount());
						a.setAmount(0);
					}
				}
			}
			cursor.setAmount(0);
			
			p.setItemOnCursor(cursorItem.getItem());
			p.updateInventory();
		}
		
	}
	
	@EventHandler
	public void onPickUp(EntityPickupItemEvent e) {
		if(!(e.getEntity() instanceof Player))
			return;
		Player p = (Player) e.getEntity();
		ItemStack item = e.getItem().getItemStack();
		
		if(item==null)
			return;
		if(!(item.getType()==Material.IRON_AXE||item.getType()==Material.IRON_PICKAXE
				||item.getType()==Material.IRON_HOE))
			return;
		
		ItemDTO itemDTO = new ItemDTO(item);
		for(ItemStack a : p.getInventory().getContents()) {
			if(a!=null) {
				if(itemDTO.equals(new ItemDTO(a))) {
					if(a.getAmount()+itemDTO.getAmount()<=64) {
						a.setAmount(a.getAmount()+item.getAmount());
						
						e.getItem().remove();
						e.setCancelled(true);
						return;
					}
				}
			}
		}
		
	}
	
}
