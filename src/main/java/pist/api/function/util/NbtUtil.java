package main.java.pist.api.function.util;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagString;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NbtUtil {
	public static ItemStack setNBT(ItemStack item, String key, String data) {
		net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		
		itemCompound.set(key, new NBTTagString(data));
		nmsItem.setTag(itemCompound);
		
		return CraftItemStack.asBukkitCopy(nmsItem);
	}
	
	public static ItemStack removeNBT(ItemStack item, String key) {
		net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		
		itemCompound.remove(key);
		nmsItem.setTag(itemCompound);
		
		return CraftItemStack.asBukkitCopy(nmsItem);
	}
	
	public static String getNBT(ItemStack item, String key) {
		net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		
		String result = itemCompound.getString(key);
		if(result.length()==0)
			return null;
		return result;
	}
	
	public static double getNBTDouble(ItemStack item, String key) {
		net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		
		String result = itemCompound.getString(key);
		if(result.length()==0)
			return 0;
		
		double resultDouble = 0;
		try {
			resultDouble = Double.parseDouble(result);
		} catch(NumberFormatException e) {
			return 0;
		}
		
		return resultDouble;
	}
	
}
