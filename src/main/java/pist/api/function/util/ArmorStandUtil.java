package main.java.pist.api.function.util;

import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArmorStandUtil {
	
	public static EntityArmorStand getArmorStand(Location loc) {
		WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
		EntityArmorStand stand = new EntityArmorStand(worldServer);
		
		stand.setLocation(loc.getX(), loc.getY(), loc.getZ(), 0, 0);
		//stand.setCustomName(" ");
		stand.setCustomNameVisible(false);
		stand.setInvisible(true);
		stand.setNoGravity(true);
		
		return stand;
	}
	
	public static EntityArmorStand sendText(Player p, Location loc, String text) {
		WorldServer worldServer = ((CraftWorld)loc.getWorld()).getHandle();
		EntityArmorStand stand = new EntityArmorStand(worldServer);
		
		stand.setLocation(loc.getX(), loc.getY(), loc.getZ(), 0, 0);
		stand.setCustomName(text);
		stand.setCustomNameVisible(true);
		stand.setInvisible(true);
		stand.setNoGravity(true);
		
		PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);
		((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
		
		return stand;
	}
	
	public static PacketPlayOutEntityEquipment getArmorStandHeadPacket(EntityArmorStand stand, ItemStack head) {
		PacketPlayOutEntityEquipment packet = new PacketPlayOutEntityEquipment(stand.getId()
				, EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(head));
		return packet;
	}
	
}
