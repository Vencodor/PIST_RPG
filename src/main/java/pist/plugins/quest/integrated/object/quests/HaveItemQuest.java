package main.java.pist.plugins.quest.integrated.object.quests;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HaveItemQuest extends QuestManager implements Cloneable {
	
	public Material itemType = null;
	public String itemName = null;
	public int amount = 0;
	public String description = "";
	
	public HaveItemQuest(Material itemType, String itemName, int amount) {
		this.itemType = itemType;
		this.itemName = itemName;
		this.amount = amount;
		
		this.description = gray+"아이템 "+orange+itemName+gray+"을(를) "+green+amount+gray+"개 가져오기";
	}

	@Override
	public boolean isComplete(Player p) {
		return (getCurrent(p) >= amount);
	}
	
	@Override
	public String getProcessBar(Player p) {
		return "( "+getCurrent(p)+" / "+amount+" )";
	}

	@Override
	public double getProcess(Player p) {
		return (double)getCurrent(p) / (double)amount;
	}
	
	private boolean isRightItem(ItemStack item) {
		if(item == null || !item.hasItemMeta() || item.getItemMeta().getDisplayName() == null)
			return false;
		return item.getItemMeta().getDisplayName().equals(itemName);
	}
	
	public int getCurrent(Player p) {
		int current = 0;
		for(ItemStack a : p.getInventory().getContents()) {
			if(isRightItem(a))
				current = current + a.getAmount();
		}
		return current;
	}
	
	public void removeItem(Player p) {
		int count = amount;
		for(ItemStack a : p.getInventory().getContents()) {
			if(count<=0)
				return;
			if(isRightItem(a)) {
				if(count-a.getAmount()>0) {
					count = count - a.getAmount();
					a.setAmount(0);
				} else {
					a.setAmount(a.getAmount()-count);
					return;
				}
			}
				
		}
	}
	
	@Override
	public HaveItemQuest clone() throws CloneNotSupportedException{
		return (HaveItemQuest)super.clone();
	}
	
}
