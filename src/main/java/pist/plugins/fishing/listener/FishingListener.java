package main.java.pist.plugins.fishing.listener;

import main.java.pist.api.function.util.ArmorStandUtil;
import main.java.pist.api.scheduler.RepeatingTask;
import main.java.pist.plugins.fishing.FishingPlugin;
import main.java.pist.plugins.fishing.object.FishDTO;
import main.java.pist.vencoder.Main;
import main.java.pist.vencoder.PluginManager;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;

public class FishingListener extends PluginManager implements Listener{
	
	@EventHandler
	public void onFishing(PlayerFishEvent e) { //찌를 던질때,회수할때,물고기 걸렸을때, 물고기 잡았을때 e.getState()로 확인가능
		Player p = e.getPlayer();
		
		if(e.getState() == State.CAUGHT_FISH) {
			e.getCaught().remove();
			
			FishDTO fish = FishingPlugin.getFishRandom();
			ItemStack fishItem = fish.getItem().getItem();
			p.getInventory().addItem(fishItem);
			
			p.sendMessage(prefix+yellow+fish.getKey()+white+" 을(를) 낚았습니다!");
			
			Location loc = e.getHook().getLocation();
			EntityArmorStand icon = ArmorStandUtil.getArmorStand(loc.add(0,0.5,0));
			//icon.setFlag(6, true); //glowing
			
			PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
			
			connection.sendPacket(new PacketPlayOutSpawnEntityLiving(icon));
			connection.sendPacket(ArmorStandUtil.getArmorStandHeadPacket(icon, fishItem));
			connection.sendPacket(new PacketPlayOutEntityEffect(icon.getId(), new MobEffect(MobEffectList.fromId(24), 1000, 20, true, true)));
			
			new RepeatingTask(Main.getInstance(),2,2) {
				final double addY = 0.13;
				int count = 0;
				@Override
				public void run() {
					Location asLoc = new Location(loc.getWorld(), icon.locX, icon.locY, icon.locZ);
					if(count>=20) {
						connection.sendPacket(new PacketPlayOutEntityDestroy(icon.getId()));
						cancel();
					}
					icon.setPosition(asLoc.getX(), asLoc.getY()+addY, asLoc.getZ());
					connection.sendPacket(new PacketPlayOutEntityTeleport(icon));
					count++;
				}
			};
			
		}
		
		//p.sendMessage(prefix+orange+"debug");
	}
	
}