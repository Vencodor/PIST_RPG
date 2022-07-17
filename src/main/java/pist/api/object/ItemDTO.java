package main.java.pist.api.object;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemDTO implements Cloneable {
	Material meterial = null;
	String display = null;
	List<String> lore = new ArrayList<String>();
	int amount = 0;
	short durabilty = 0;
	NBTTagCompound nbt = new NBTTagCompound();

	public Material getMeterial() {
		return meterial;
	}

	public void setMeterial(Material meterial) {
		this.meterial = meterial;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public List<String> getLore() {
		return lore;
	}

	public void setLore(List<String> lore) {
		this.lore = lore;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public short getDurabilty() {
		return durabilty;
	}

	public void setDurabilty(short durabilty) {
		this.durabilty = durabilty;
	}

	public NBTTagCompound getNbt() {
		return nbt;
	}

	public void setNbt(NBTTagCompound nbt) {
		this.nbt = nbt;
	}

	public ItemDTO(Material meterial, String display, List<String> lore, int amount, NBTTagCompound nbt) {
		super();
		this.meterial = meterial;
		this.display = display;
		this.lore = lore;
		this.amount = amount;
		this.nbt = nbt;
	}

	public ItemDTO(ItemStack item) {
		this.meterial = item.getType();
		this.amount = item.getAmount();
		this.durabilty = item.getDurability();

		ItemMeta im = item.getItemMeta();
		if (im == null)
			return;
		this.display = im.getDisplayName();
		if (im.getLore() != null)
			this.lore = im.getLore();

		net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		this.nbt = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
	}

	public ItemStack getItem() {
		ItemStack item = new ItemStack(meterial, amount);
		item.setDurability(durabilty);

		ItemMeta im = item.getItemMeta();
		im.setDisplayName(display);
		im.setLore(lore);

		net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		nmsItem.setTag(this.nbt);
		item = CraftItemStack.asBukkitCopy(nmsItem);

		return item;
	}

	public boolean equals(ItemDTO item) {
		boolean sameLore = true;
		if (item.getLore() == null && this.getLore().size() == 0) {
			sameLore = true;
		} else if (item.getLore() == null || this.getLore() == null) {
			sameLore = false;
		} else if (item.getLore().size() != this.getLore().size()) {
			sameLore = false;
		} else if (item.getLore().size() > 0) {
			for (int index = 0; index < this.getLore().size(); index++)
				if (!(item.getLore().get(index).equals(this.getLore().get(index))))
					sameLore = false;
		}
		return sameLore && this.meterial == item.getMeterial()
				&& ((this.display == null && item.getDisplay() == null) 
						|| (this.display!=null && this.display.equals(item.getDisplay())));
	}
	
	public boolean equalsWithoutLore(ItemDTO item) {
		return this.meterial == item.getMeterial()
				&& ((this.display == null && item.getDisplay() == null) || this.display.equals(item.getDisplay()));
	}
	
	@Override
	public ItemDTO clone() throws CloneNotSupportedException {
		return (ItemDTO) super.clone();
	}
}
