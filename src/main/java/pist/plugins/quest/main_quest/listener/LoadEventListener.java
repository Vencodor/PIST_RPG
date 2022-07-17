package main.java.pist.plugins.quest.main_quest.listener;

import main.java.pist.data.player.PlayerData;
import main.java.pist.plugins.quest.integrated.object.PlayerQuestDTO;
import main.java.pist.plugins.quest.main_quest.object.MainQuestDTO;
import net.minecraft.server.v1_12_R1.DataWatcher;
import net.minecraft.server.v1_12_R1.DataWatcherObject;
import net.minecraft.server.v1_12_R1.DataWatcherRegistry;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityMetadata;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class LoadEventListener implements Listener {
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if(e.getFrom().distance(e.getTo())<0.01)
			return;
		Player p = e.getPlayer();
		PlayerQuestDTO quest = PlayerData.getData(p.getUniqueId()).getQuest();
		if(quest!=null) {
			if(quest.getProgressQuest()!=null) {
				MainQuestDTO mainQuest = quest.getProgressQuest();
				if(mainQuest.getNpc()!=null) {
					for(Entity a : p.getNearbyEntities(45, 45, 45)) {
						if(a instanceof LivingEntity) {
							if(a.getName().contains(mainQuest.getNpc())) {
								DataWatcher data = ((CraftEntity)a).getHandle().getDataWatcher();
								DataWatcherObject<Byte> glowingObj = new DataWatcherObject<>(0, DataWatcherRegistry.a);
								
								data.set(glowingObj, (byte)0x40);
								
								((CraftPlayer)p).getHandle().playerConnection
								.sendPacket(new PacketPlayOutEntityMetadata(a.getEntityId(), data, true));
								
								return;
							}
						}
					}
					
					
				}
			}
			
		}
	}
	
}
