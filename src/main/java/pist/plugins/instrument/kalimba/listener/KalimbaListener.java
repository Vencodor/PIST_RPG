package main.java.pist.plugins.instrument.kalimba.listener;

import com.github.fierioziy.particlenativeapi.api.Particles_1_8;
import main.java.pist.api.function.util.NbtUtil;
import main.java.pist.plugins.instrument.kalimba.KalimbaPlugin;
import main.java.pist.plugins.instrument.kalimba.KalimbaSound;
import main.java.pist.vencoder.Main;
import main.java.pist.vencoder.PluginManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class KalimbaListener extends PluginManager implements Listener {
	
	private final int slot = 8;
	
	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		if(e.getAction()!=Action.RIGHT_CLICK_AIR&&e.getAction()!=Action.RIGHT_CLICK_BLOCK)
			return;
		Player p = e.getPlayer();
		ItemStack item = p.getInventory().getItemInMainHand();
		
		if(NbtUtil.getNBT(item, "instrument")!=null&&NbtUtil.getNBT(item, "instrument").equals("칼림바")) {
			if(KalimbaPlugin.kalimba.containsKey(p.getName())) {
				KalimbaPlugin.remove(p);
				p.sendMessage(" ");
				p.sendMessage(gray+"칼림바 연주 모드가 "+red+"비활성화 "+gray+"되었습니다");
				p.sendMessage(" ");
			} else {
				PlayerInventory inv = p.getInventory();
				for(int i=0; i<=8; i++) {
					if(inv.getItem(i)!=null&&NbtUtil.getNBT(inv.getItem(i), "instrument")==null) {
						p.sendMessage(red+"연주를 하기 위해선 1~9번칸에 있는 아이템을 비워주세요.");
						return;
					}
				}
				inv.setItemInMainHand(null);
				inv.setItem(slot, item);
				
				KalimbaPlugin.add(p);
				
				p.sendMessage(" ");
				p.sendMessage(gray+"칼림바 연주 모드가 "+green+"활성화 "+gray+"되었습니다");
				p.sendMessage(gray+"9번을 제외한 1~8번키를 눌러 연주하세요.");
				p.sendMessage(" ");
				
				p.getInventory().setHeldItemSlot(slot);
			}
		}
	}
	
	@EventHandler
	public void onPressDownF(PlayerSwapHandItemsEvent e) {
		Player p = e.getPlayer();
		
		if(KalimbaPlugin.kalimba.containsKey(p.getName())) {
			if(KalimbaPlugin.kalimba.get(p.getName())) {
				KalimbaPlugin.kalimba.put(p.getName(), false);
				p.sendMessage(gray+"음높이가 "+green+"1옥타브"+gray+"로 설정되었습니다.");
			} else {
				KalimbaPlugin.kalimba.put(p.getName(), true);
				p.sendMessage(gray+"음높이가 "+orange+"2옥타브"+gray+"로 설정되었습니다.");
			}
		}
	}
	
	@EventHandler
	public void onHeldSlot(PlayerItemHeldEvent e) {
		Player p = e.getPlayer();
		
		if(KalimbaPlugin.kalimba.containsKey(p.getName())) {
			if(e.getNewSlot()==slot)
				return;
			e.setCancelled(true);
			p.getInventory().setHeldItemSlot(slot);
			
			KalimbaSound sound = null;
			if(KalimbaPlugin.kalimba.get(p.getName())) {
				sound = KalimbaPlugin.getSound(e.getNewSlot()+8);
			} else {
				sound = KalimbaPlugin.getSound(e.getNewSlot());
			}
			p.playSound(p.getLocation(), sound.getSoundName(), 10F, 1F);
			
			int count = 0;
			for(Entity a : p.getNearbyEntities(15, 15, 15)) {
				if(a instanceof Player) {
					float normalize = (float) (1-(p.getLocation().distance(a.getLocation())/15));
					((Player)a).playSound(p.getLocation(), sound.getSoundName(), 10F*normalize, 1F);
					
					count++;
				}
			}
			p.spigot().sendMessage(ChatMessageType.ACTION_BAR,
					TextComponent.fromLegacyText(sound.getDisplay()+"!"+gray+"  ( "+dgray+count+gray+"명이 듣고있습니다 )"));
			
			Location spawnLoc =  p.getLocation().clone().add(0,3,0);
			
			Particles_1_8 particles = Main.particleAPI.getParticles_1_8();
			Object particlePacket = particles.NOTE().packet(true,spawnLoc);
			particles.sendPacket(spawnLoc, 15, particlePacket);
		}
	}
	
	@EventHandler
	public void onDropItem(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		
		if(KalimbaPlugin.kalimba.containsKey(p.getName())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		if(KalimbaPlugin.kalimba.containsKey(p.getName())) {
			if(e.getClickedInventory().getType() == InventoryType.PLAYER)
				e.setCancelled(true);
		}
	}
	
}
