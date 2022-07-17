package main.java.pist.api.animation.inventory;

import main.java.pist.api.scheduler.RepeatingTask;
import main.java.pist.vencoder.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryAnimation {
	
	public static void run(Inventory inv, InventoryAnimations ani, Material filter) {
		if(ani==InventoryAnimations.SLIDE_UP) {
			slideUp(inv,filter);
		} else if(ani==InventoryAnimations.SLIDE_DOWN) {
			slideDown(inv,filter);
		} else {
			
		}
	}
	
	public static void slideUp(Inventory inv, Material filter) {
		Inventory clone = Bukkit.createInventory(null, inv.getSize());
		clone.setContents(inv.getContents());
		if(filter==null) {
			inv.clear();
		} else {
			for(int i=0; i<inv.getSize(); i++)
				if(inv.getItem(i)!=null&&inv.getItem(i).getType()==filter)
					inv.setItem(i, null);
		}

		int time = 3/inv.getSize(); //1.5초내로 끝나도록
		new RepeatingTask(Main.getInstance(),time,time) {
			int slot = inv.getSize()-1;
			
			@Override
			public void run() {
				if(inv==null||slot<0)
					cancel();
				ItemStack item = clone.getItem(slot);
				if(item != null && item.getType() == filter)
					inv.setItem(slot, item);
				slot = slot-1;
			}
			
		};
	}
	
	public static void slideDown(Inventory inv, Material filter) {
		Inventory clone = Bukkit.createInventory(null, inv.getSize());
		clone.setContents(inv.getContents());
		if(filter==null) {
			inv.clear();
		} else {
			for(int i=0; i<inv.getSize(); i++)
				if(inv.getItem(i)!=null&&inv.getItem(i).getType()==filter)
					inv.setItem(i, null);
		}
		
		int time = 2/inv.getSize();
		new RepeatingTask(Main.getInstance(),time,time) {
			int slot = 0;
			
			@Override
			public void run() {
				if(inv==null||slot>=inv.getSize()-1)
					cancel();
				ItemStack item = clone.getItem(slot);
				if(item != null && item.getType() == filter)
					inv.setItem(slot, item);
				slot = slot+1;
			}
			
		};
	}
}
