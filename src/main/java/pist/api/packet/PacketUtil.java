package main.java.pist.api.packet;

import net.minecraft.server.v1_12_R1.DataWatcher;
import net.minecraft.server.v1_12_R1.DataWatcherObject;
import net.minecraft.server.v1_12_R1.DataWatcherRegistry;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityMetadata;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PacketUtil {
	public static void setGlowing(Player p, Entity target) {
		DataWatcher data = ((CraftEntity)target).getHandle().getDataWatcher();
		data.set(new DataWatcherObject<>(0, DataWatcherRegistry.a), (byte)0x40);
		
		((CraftPlayer)p).getHandle().playerConnection
		.sendPacket(new PacketPlayOutEntityMetadata(target.getEntityId(),data,true));
	}
}
