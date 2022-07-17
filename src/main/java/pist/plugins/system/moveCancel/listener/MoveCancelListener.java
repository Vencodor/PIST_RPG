package main.java.pist.plugins.system.moveCancel.listener;

import main.java.pist.api.function.util.NbtUtil;
import main.java.pist.plugins.system.moveCancel.MoveCancelPlugin;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import net.minecraft.server.v1_12_R1.WorldServer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MoveCancelListener implements Listener {
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if(!p.isOp())
			return;
		
		ItemStack hand = p.getInventory().getItemInMainHand();
		if(hand!=null&&NbtUtil.getNBT(hand, "movecancel")!=null) {
			MoveCancelPlugin.add(e.getBlock());
		}
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		List<Location> nearBlocks = MoveCancelPlugin.getNearBlock(p.getLocation());
		if(nearBlocks.size()>0) {
			PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
			
			for(Location a : nearBlocks) {
				WorldServer worldServer = ((CraftWorld)a.getWorld()).getHandle();
				PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(worldServer
						, new BlockPosition(a.getX(),a.getY(),a.getZ()));
				packet.block = net.minecraft.server.v1_12_R1.Blocks.STAINED_GLASS.fromLegacyData(14);
				
				connection.sendPacket(packet);
			}
			
		}
	}
	
}
