package main.java.pist.plugins.customItem.listener;

import main.java.pist.manager.plugin.GuiManager;
import main.java.pist.plugins.customItem.CustomItemPlugin;
import main.java.pist.plugins.customItem.inventory.GUI_CustomItem;
import main.java.pist.plugins.customItem.object.LoreEditDTO;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class LoreEditListener extends GuiManager implements Listener {
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		LoreEditDTO edit = CustomItemPlugin.edit.get(p.getName());
		if(edit!=null) {
			e.setCancelled(true);
			
			String msg = e.getMessage();
			ItemStack item = edit.getCustom().getContents().get(edit.getIndex());
			if(edit.isName()) { //이름 수정하는경우
				String name = item.getItemMeta().getDisplayName();
				StringBuilder builder = new StringBuilder();
				int count = 0;
				for(char a : name.toCharArray()) {
					if(a == '$') {
						if(count == edit.getCount()) {
							builder.append(msg);
						} else {
							builder.append("$");
							count++;
						}
					} else {
						builder.append(a);
					}
				}
				ItemMeta im = item.getItemMeta();
				im.setDisplayName(builder.toString());
				item.setItemMeta(im);
			} else { //설명 수정하는경우
				List<String> lore = item.getItemMeta().getLore();
				String content = lore.get(edit.getLine());
				StringBuilder builder = new StringBuilder();
				int count = 0;
				for(char a : content.toCharArray()) {
					if(a == '$') {
						if(count == edit.getCount()) {
							builder.append(msg);
						} else {
							builder.append("$");
							count++;
						}
					} else {
						builder.append(a);
					}
				}
				lore.set(edit.getLine(), builder.toString());
				
				ItemMeta im = item.getItemMeta();
				im.setLore(lore);
				item.setItemMeta(im);
			}
			//CustomItemDTO custom = CustomItemPlugin.getCustom(edit.getCustom().getKey());
			edit.getCustom().getContents().set(edit.getIndex(), item);
			p.openInventory((new GUI_CustomItem()).getCustomItemLoreGUI(edit.getCustom(),edit.getIndex()));
			CustomItemPlugin.edit.remove(p.getName());
		}
	}
	
}
