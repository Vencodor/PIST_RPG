package main.java.pist.plugins.cook.listener;

import main.java.pist.api.function.util.ArmorStandUtil;
import main.java.pist.api.function.util.DateUtil;
import main.java.pist.api.function.util.InventoryUtil;
import main.java.pist.api.function.util.NbtUtil;
import main.java.pist.api.scheduler.RepeatingTask;
import main.java.pist.data.player.PlayerData;
import main.java.pist.plugins.cook.CookPlugin;
import main.java.pist.plugins.cook.inventory.CookGUI;
import main.java.pist.plugins.cook.object.CookDTO;
import main.java.pist.plugins.cook.object.CookingDTO;
import main.java.pist.vencoder.Main;
import main.java.pist.vencoder.PluginManager;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CookGUI_Listener extends PluginManager implements Listener{
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getInventory().getTitle().contains("요리")) {
			e.setCancelled(true);
			
			ItemStack item = e.getCurrentItem();
			if(item==null)
				return;
			
			CookGUI gui = new CookGUI();
			Player p = (Player) e.getWhoClicked();
			Inventory inv = e.getInventory();
			
			if(NbtUtil.getNBT(item, "key")!=null) {
				CookGUI.setSelected(p, CookPlugin.getCook(NbtUtil.getNBT(item, "key")));
				p.openInventory(gui.getCookInv(p, PlayerData.getData(p).getCook(), true));
			} else if(NbtUtil.getNBT(item, "page")!=null) {
				int page = 0;
				try {
					page = Integer.parseInt(NbtUtil.getNBT(item, "page"));
				} catch(NumberFormatException e1) {
					e1.printStackTrace();
				}
				if(page<0)
					return;
				gui.setRecipePage(inv, PlayerData.getData(p).getCook(), page, CookGUI.getSelected(p));
			} else if(NbtUtil.getNBT(item, "start")!=null) {
				CookDTO cook = CookGUI.getSelected(p);
				
				if(InventoryUtil.haveItems(p.getInventory(), cook.getMaterial())) {
					InventoryUtil.removeItems(p, cook.getMaterial());
					p.closeInventory();
					
					Location iconLoc = p.getTargetBlock(null, 6).getLocation().add(0.5,0.85,0.75);
					EntityArmorStand as = ArmorStandUtil.getArmorStand(iconLoc);
					PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(as);
					
					Location textLoc = iconLoc.clone().add(0,-0.59,-0.25);
					EntityArmorStand leftTime = ArmorStandUtil.sendText(p, textLoc, orange+"도구들을 세팅중입니다..");
					
					PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
					connection.sendPacket(packet);
					ItemStack icon = new ItemStack(Material.BREAD);
					if(cook.getIcon()!=null)
						icon = cook.getIcon().getItem();
					connection.sendPacket(ArmorStandUtil.getArmorStandHeadPacket(as, new ItemStack(icon)));
					
					CookingDTO cooking = new CookingDTO(cook, as, leftTime);
					CookPlugin.playerCooking.put(p.getName(), cooking);
					
					ItemStack icon2 = icon.clone();
					new RepeatingTask(Main.getInstance(),20,20) {
						@Override
						public void run() {
							int current = (int) (cooking.getCook().getTime()
									-(System.currentTimeMillis()-cooking.getStartTime())/1000);
							if(current<=0) {
								icon2.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
								connection.sendPacket(ArmorStandUtil.getArmorStandHeadPacket(as, icon2));
								
								leftTime.setCustomName(green+"조리가 끝났습니다");
								connection.sendPacket(new PacketPlayOutEntityMetadata(leftTime.getId(), leftTime.getDataWatcher(), false));
								
								cancel();
							} else {
								leftTime.setCustomName(yellow+DateUtil.getSecondFormatDate(DateUtil.toDate(current))+gray+" 남음");
								
								connection.sendPacket(new PacketPlayOutEntityMetadata(leftTime.getId(), leftTime.getDataWatcher(), false));
							}
						}
					};
					
				}
				
			}
			
		}
	}
	
	@EventHandler
	public void onClickCookProcessingInv(InventoryClickEvent e) {
		if(e.getInventory().getTitle().contains("조리중")) {
			e.setCancelled(true);
			
			ItemStack item = e.getCurrentItem();
			if(item==null)
				return;
			
			Player p = (Player) e.getWhoClicked();
			
			if(NbtUtil.getNBT(item, "cancel")!=null) {
				CookingDTO cooking = CookPlugin.playerCooking.get(p.getName());
				p.closeInventory();
				
				if(cooking==null)
					return;
				
				p.sendMessage(" ");
				p.sendMessage(red+bold+"조리를 취소하였습니다.");
				p.sendMessage(" ");
				
				PacketPlayOutEntityDestroy packetIcon = new PacketPlayOutEntityDestroy(cooking.getIcon().getId());
				PacketPlayOutEntityDestroy packetText = new PacketPlayOutEntityDestroy(cooking.getText().getId());
				
				PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
				connection.sendPacket(packetIcon);
				connection.sendPacket(packetText);
				
				CookPlugin.playerCooking.remove(p.getName());
			}
		}
	}
	
}
