package main.java.pist.plugins.collect.grass.listener;

import main.java.pist.api.function.util.ArmorStandUtil;
import main.java.pist.api.function.util.FormatUtil;
import main.java.pist.api.function.util.NbtUtil;
import main.java.pist.api.scheduler.RepeatingTask;
import main.java.pist.plugins.collect.grass.CollectGrassPlugin;
import main.java.pist.plugins.collect.grass.object.CollectingGrassDTO;
import main.java.pist.vencoder.Main;
import main.java.pist.vencoder.PluginManager;
import net.minecraft.server.v1_12_R1.*;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntity.PacketPlayOutEntityLook;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CollectGrassListener extends PluginManager implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClickBlock(PlayerInteractEvent e) {
		if(!(e.getAction()==Action.RIGHT_CLICK_BLOCK))
			return;
		
		Block block = e.getClickedBlock();
		if(block==null||block.isEmpty())
			return;
		if(block.getType()!=Material.CONCRETE_POWDER||block.getData()!=4)
			return;
		
		Player p = e.getPlayer();
		if(p.isSneaking())
			return;
		
		e.setCancelled(true);
		
		if(CollectGrassPlugin.collecting.containsKey(p.getName()))
			return;
		
		String nbtValue = NbtUtil.getNBT(p.getInventory().getItemInMainHand(), "gather");
		if(nbtValue==null||!nbtValue.equals("grass"))
			return;
		for(CollectingGrassDTO a : CollectGrassPlugin.collecting.values()) {
			if(block==a.getBlock()) {
				p.sendTitle(red+bold+"이미 다른사람이 채집중입니다!", gray+"", 10, 40, 5);
				return;
			}
		}
		
		Location blockLoc = block.getLocation();
		
		CollectingGrassDTO collecting = new CollectingGrassDTO(block, CollectGrassPlugin.getRandomOre());
		CollectGrassPlugin.collecting.put(p.getName(), collecting);
		
		EntityArmorStand stand = ArmorStandUtil.getArmorStand(blockLoc.add(0.5,0.06,0.75));
		PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(stand);
		
		PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
		connection.sendPacket(packet);
		connection.sendPacket(ArmorStandUtil.getArmorStandHeadPacket(stand, new ItemStack(Material.SHEARS)));
		
		List<PacketPlayOutBlockChange> blocks = new ArrayList<PacketPlayOutBlockChange>();
		
		Random random = new Random();
		new RepeatingTask(Main.getInstance(),2,2) {
			float angle = 0;
			@Override
			public void run() {
				if(blockLoc.distance(p.getLocation())<4) {
					double leftTime = collecting.getTime()
							- (((double)System.currentTimeMillis())/1000-((double)collecting.getStart())/1000);
					if(leftTime<=0) {
						p.sendTitle(green+bold+"성공적으로 채취하였습니다!"
								, gray+"당신은 "+orange+collecting.getTime()+gray+"초 만에 식물을 채취하였습니다.", 10, 40, 5);
						
						PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(stand.getId());
						connection.sendPacket(destroyPacket);
						
						block.setType(Material.AIR);
						
						p.getInventory().addItem(collecting.getItem().getItem());
						CollectGrassPlugin.collecting.remove(p.getName());
						
						for(PacketPlayOutBlockChange a : blocks) {
							a.block = net.minecraft.server.v1_12_R1.Blocks.AIR.getBlockData();
							connection.sendPacket(a);
						}
						
						cancel();
					} else {
						p.sendTitle(gray+bold+"식물을 채취하는중입니다", FormatUtil.colorFormat(leftTime, collecting.getTime()), 0, 15, 0);
						
						angle = angle+2.2F;
						if(angle>=180)
							angle = -180;
						PacketPlayOutEntityLook headPacket 
						= new PacketPlayOutEntityLook(stand.getId(), (byte) (angle * 256.0d / 360.0d), (byte) 0, false);
						connection.sendPacket(headPacket);
						
						if(random.nextInt(4)==0) { // 1/5
							double randomX = random.nextInt(11)-5; //0~10
							double randomZ = random.nextInt(11)-5;
							
							Location spawnLoc = blockLoc.getWorld()
									.getHighestBlockAt(blockLoc.clone().add(randomX,0,randomZ)).getLocation();
							
							WorldServer worldServer = ((CraftWorld)blockLoc.getWorld()).getHandle();
							PacketPlayOutBlockChange blockPacket = new PacketPlayOutBlockChange(worldServer
									, new BlockPosition(spawnLoc.getX(),spawnLoc.getY(),spawnLoc.getZ()));
							blockPacket.block = net.minecraft.server.v1_12_R1.Blocks.DOUBLE_PLANT.fromLegacyData(2);
							connection.sendPacket(blockPacket);
							
							blocks.add(blockPacket);
							
							new BukkitRunnable() {
								@Override
								public void run() {
									if(blockPacket.block.getMaterial() != net.minecraft.server.v1_12_R1.Material.AIR) {
										blockPacket.block = net.minecraft.server.v1_12_R1.Blocks.RED_FLOWER
												.fromLegacyData(random.nextInt(9));
										connection.sendPacket(blockPacket);
									}
								}
							}.runTaskLater(Main.getInstance(), 20*2);
						}
					}
				} else {
					p.sendTitle(red+bold+"거리가 너무 멉니다!", gray+"식물과의 거리가 4블럭 이상 멀어지면 취소됩니다.", 10, 40, 5);
					CollectGrassPlugin.collecting.remove(p.getName());
					PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(stand.getId());
					connection.sendPacket(destroyPacket);
					
					for(PacketPlayOutBlockChange a : blocks) {
						a.block = net.minecraft.server.v1_12_R1.Blocks.AIR.getBlockData();
						connection.sendPacket(a);
					}
					
					cancel();
				}
			}
		};
	}
	
}
