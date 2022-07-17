package main.java.pist.plugins.cook.listener;

import main.java.pist.api.function.util.NbtUtil;
import main.java.pist.data.player.PlayerData;
import main.java.pist.plugins.cook.CookPlugin;
import main.java.pist.plugins.cook.inventory.CookGUI;
import main.java.pist.plugins.cook.object.CookDTO;
import main.java.pist.plugins.cook.object.CookDataDTO;
import main.java.pist.plugins.cook.object.CookingDTO;
import main.java.pist.vencoder.PluginManager;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_12_R1.PlayerConnection;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CookListener extends PluginManager implements Listener {
	
	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			if(!(b.getType() == Material.BLACK_GLAZED_TERRACOTTA)) //요리블럭
				return;
			e.setCancelled(true);
			
			Player p = e.getPlayer();
			CookGUI gui = new CookGUI();
			if(!CookPlugin.playerCooking.containsKey(p.getName())) {
				p.openInventory(gui.getCookInv(p, PlayerData.getData(p.getUniqueId()).getCook(), true));
			} else {
				CookingDTO cooking = CookPlugin.playerCooking.get(p.getName());
				
				if(cooking.getCook().getTime()-(System.currentTimeMillis()-cooking.getStartTime())/1000<=0) {//완료
					p.sendMessage(" ");
					p.sendMessage(green+bold+"조리를 성공적으로 마쳤습니다!");
					p.sendMessage(gray+"당신은 "+orange+cooking.getCook().getTime()+gray+"초 만에 조리를 끝냈습니다. 맛있게 드세요!");
					p.sendMessage(" ");
					
					if(cooking.getCook().getResult()!=null) {
						p.getInventory().addItem(cooking.getCook().getResult());
					} else {
						p.sendMessage(red+bold+"ERROR! 결과물이 설정되지 않았습니다");
					}
					
					PacketPlayOutEntityDestroy packetIcon = new PacketPlayOutEntityDestroy(cooking.getIcon().getId());
					PacketPlayOutEntityDestroy packetText = new PacketPlayOutEntityDestroy(cooking.getText().getId());
					
					PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
					connection.sendPacket(packetIcon);
					connection.sendPacket(packetText);
					
					CookPlugin.playerCooking.remove(p.getName());
				} else {
					p.openInventory(gui.getProgressCookInv(cooking));
				}
			}
		}
	}
	
	@EventHandler
	public void onRightClickScroll(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_AIR||e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Player p = e.getPlayer();
			ItemStack item = p.getInventory().getItemInMainHand();
			if(item==null)
				return;
			if(NbtUtil.getNBT(item, "recipe")!=null) {
				CookDTO cook = CookPlugin.getCook(NbtUtil.getNBT(item, "recipe"));
				if(cook!=null) {
					PlayerData.getData(p.getUniqueId()).getCook().getUnlock().add(new CookDataDTO(cook.getKey()));
					p.sendMessage("레시피가 추가되었습니다");
				}
			}
		}
	}
	
}