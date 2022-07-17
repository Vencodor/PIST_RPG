package main.java.pist.plugins.collect.ore.listener;

import main.java.pist.api.function.util.ArmorStandUtil;
import main.java.pist.api.function.util.FormatUtil;
import main.java.pist.api.function.util.NbtUtil;
import main.java.pist.api.scheduler.RepeatingTask;
import main.java.pist.plugins.collect.ore.CollectOrePlugin;
import main.java.pist.plugins.collect.ore.object.CollectingOreDTO;
import main.java.pist.vencoder.Main;
import main.java.pist.vencoder.PluginManager;
import net.minecraft.server.v1_12_R1.*;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntity.PacketPlayOutEntityLook;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class CollectOreListener extends PluginManager implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClickBlock(PlayerInteractEvent e) {
		if(!(e.getAction()==Action.LEFT_CLICK_BLOCK))
			return;
		
		Block block = e.getClickedBlock();
		if(block==null||block.isEmpty())
			return;
		if(block.getType()!=Material.CONCRETE_POWDER||block.getData()!=6)
			return;
		
		Player p = e.getPlayer();
		if(p.isSneaking())
			return;
		
		e.setCancelled(true);
		
		if(CollectOrePlugin.collecting.containsKey(p.getName()))
			return;
		
		String nbtValue = NbtUtil.getNBT(p.getInventory().getItemInMainHand(), "gather");
		if(nbtValue==null||!nbtValue.equals("ore"))
			return;
		for(CollectingOreDTO a : CollectOrePlugin.collecting.values()) {
			if(block == a.getBlock()) {
				p.sendTitle(red+bold+"이미 다른사람이 채집중입니다!", gray+"", 10, 40, 5);
				return;
			}
		}
		
		Location blockLoc = block.getLocation();
		
		CollectingOreDTO collecting = new CollectingOreDTO(block, CollectOrePlugin.getRandomOre());
		CollectOrePlugin.collecting.put(p.getName(), collecting);
		
		EntityArmorStand stand = ArmorStandUtil.getArmorStand(blockLoc.add(0.5,0.06,0.75));
		PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);
		
		PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
		connection.sendPacket(packet);
		connection.sendPacket(ArmorStandUtil.getArmorStandHeadPacket(stand, new ItemStack(Material.HOPPER)));
		
		new RepeatingTask(Main.getInstance(),2,2) {
			float angle = 0;
			@Override
			public void run() {
				if(blockLoc.distance(p.getLocation())<4) {
					double leftTime = collecting.getTime()
							- (((double)System.currentTimeMillis())/1000-((double)collecting.getStart())/1000);
					if(leftTime<=0) {
						p.sendTitle(green+bold+"성공적으로 채취하였습니다!"
								, gray+"당신은 "+orange+collecting.getTime()+gray+"초 만에 광석을 채취하였습니다.", 10, 40, 5);
						
						PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(stand.getId());
						connection.sendPacket(destroyPacket);
						
						block.setType(Material.AIR);
						
						WorldServer worldServer = ((CraftWorld)blockLoc.getWorld()).getHandle();
						
						new RepeatingTask(Main.getInstance(),0,3) {
							int count = 8;
							@Override
							public void run() {
								if(count<=0)
									cancel();
								
								EntityItem item = new EntityItem(worldServer);
								item.setItemStack(CraftItemStack.asNMSCopy(collecting.getItem().getItem()));
								item.setLocation(blockLoc.getX(), blockLoc.getY()+1, blockLoc.getZ(), 0, 0);
								
								PacketPlayOutSpawnEntity itemPacket = new PacketPlayOutSpawnEntity(item, 2);
								PacketPlayOutEntityMetadata dataPacket = new PacketPlayOutEntityMetadata(item.getId(), item.getDataWatcher(), true);
								
								connection.sendPacket(itemPacket);
								connection.sendPacket(dataPacket);
								
								new BukkitRunnable() {
									@Override
									public void run() {
										PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(item.getId());
										connection.sendPacket(destroyPacket);
									}
								}.runTaskLater(Main.getInstance(), 20*3);
								
								count--;
							}
						};
						
						p.getInventory().addItem(collecting.getItem().getItem());
						
						CollectOrePlugin.collecting.remove(p.getName());
						cancel();
					} else {
						p.sendTitle(gray+bold+"광석을 캐는중입니다", FormatUtil.colorFormat(leftTime, collecting.getTime()), 0, 15, 0);
						
						angle = angle+2.2F;
						if(angle>=180)
							angle = -180;
						PacketPlayOutEntityLook headPacket 
						= new PacketPlayOutEntityLook(stand.getId(), (byte) (angle * 256.0d / 360.0d), (byte) 0, false);
						connection.sendPacket(headPacket);
					}
				} else {
					p.sendTitle(red+bold+"거리가 너무 멉니다!", gray+"광석과의 거리가 4블럭 이상 멀어지면 취소됩니다.", 10, 40, 5);
					CollectOrePlugin.collecting.remove(p.getName());
					
					PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(stand.getId());
					connection.sendPacket(destroyPacket);
					
					cancel();
				}
			}
		};
		
	}
	
}
