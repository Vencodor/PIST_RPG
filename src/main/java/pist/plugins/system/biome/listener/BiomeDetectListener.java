package main.java.pist.plugins.system.biome.listener;

import main.java.pist.data.player.PlayerDynamicData;
import main.java.pist.data.player.object.PlayerDynamicDTO;
import main.java.pist.plugins.system.biome.BiomePlugin;
import main.java.pist.plugins.system.biome.events.BiomeUpdateEvent;
import main.java.pist.plugins.system.biome.object.BiomeDTO;
import main.java.pist.vencoder.Main;
import main.java.pist.vencoder.PluginManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class BiomeDetectListener extends PluginManager implements Listener {
	
	public static List<String> disableDetect = new ArrayList<String>();
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if(e.getFrom().distance(e.getTo())>0.01) {
			Location from = e.getFrom();
			Location to = e.getTo();
			Player p = e.getPlayer();
			
			PlayerDynamicDTO dynamic = PlayerDynamicData.getData(p.getUniqueId());
			
			BiomeDTO biomeDTO = getLocationBiome(to);
			if(from.getBlock().getBiome() != to.getBlock().getBiome() 
							|| ( dynamic!=null && ((dynamic.getBiomeKey()==null&&biomeDTO!=null) //dynamic과 biomeDTO가 바뀌었을때 감지
							||(dynamic.getBiomeKey()!=null&&biomeDTO==null))
							||(dynamic.getBiomeKey()!=null&&dynamic.getBiomeKey().equals(biomeDTO.getKey())))) {
				
				BiomeUpdateEvent event = new BiomeUpdateEvent(p, getLocationBiome(from), biomeDTO);
				Bukkit.getServer().getPluginManager().callEvent(event);
				
				if(biomeDTO!=null) {
					if(!containsDisable(p)) { //감지로 인해 채팅도배가 되지 않도록
						p.sendMessage(prefix+"'"+biomeDTO.getDisplay()+"' 영역에 진입감지");
						
						new BukkitRunnable() {
							@Override
							public void run() {
								disableDetect.remove(p.getName());
							}
						}.runTaskLater(Main.getInstance(), 20*15);
					}
					disableDetect.add(p.getName());
					
					dynamic.setBiomeKey(biomeDTO.getKey());
				} else {
					dynamic.setBiomeKey(null);
				}
			}
		}
	}
	
	public static boolean containsDisable(Player p) {
		return disableDetect.contains(p.getName());
	}
	
	public BiomeDTO getLocationBiome(Location loc) {
		return BiomePlugin.getBiomeUseBiome(loc.getBlock().getBiome());
	}
	
}
