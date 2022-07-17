package main.java.pist.plugins.customItem.listener;

import main.java.pist.api.function.util.NbtUtil;
import main.java.pist.manager.plugin.GuiManager;
import main.java.pist.plugins.customItem.CustomItemPlugin;
import main.java.pist.plugins.customItem.inventory.GUI_CustomItem;
import main.java.pist.plugins.customItem.object.CustomItemDTO;
import main.java.pist.plugins.customItem.object.LoreEditDTO;
import main.java.pist.plugins.customItem.object.enums.CustomType;
import main.java.pist.vencoder.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class GUI_CustomItemListener extends GuiManager implements Listener {
	
	@EventHandler
	public void onClickInv(InventoryClickEvent e) {
		if(e.getInventory().getTitle().contains("Custom")) {
			Player p = (Player)e.getWhoClicked();
			
			String title = e.getInventory().getTitle();
			CustomItemDTO custom = CustomItemPlugin.getCustom(title.split("#")[1]);
			GUI_CustomItem gui = new GUI_CustomItem();
			
			int page = 1;
			try {
				page = Integer.parseInt(title.split("#")[2].replace("Page", ""));
			} catch(NumberFormatException e1) {
				e1.printStackTrace();
				return;
			}
			
			if(custom==null) return;
			
			if(e.getClickedInventory()!=null && e.getClickedInventory().getType() == InventoryType.CHEST) {
				e.setCancelled(true);
				
				if(e.getCurrentItem()!=null&&NbtUtil.getNBT(e.getCurrentItem(), "cancel")!=null) {
					return;
				}
				
				if(e.getSlot()==48) { //이전
					if(page>1) {
						p.openInventory(gui.getCustomGUI(custom, page-1));
					}
				} else if(e.getSlot()==50) { //다음
					if(custom.getContents().size() > page*45) {
						p.openInventory(gui.getCustomGUI(custom, page+1));
					}
				} else if(e.getSlot()==53) { //설정
					p.openInventory(gui.getCustomSettingGUI(custom));
				} else if(e.getSlot()==45) { //추가
					ItemStack cursor = e.getCursor().clone();
					cursor.setAmount(1);
					
					if(custom.getContents().contains(cursor))
						return;
					custom.getContents().add(cursor);
					
					if(e.getInventory().getItem(44)==null)
						e.getInventory().addItem(cursor);
				} else if(e.getSlot()==49) { //기본 로어설정
					ItemStack cursor = e.getCursor();
					if(cursor == null||cursor.getItemMeta() == null||cursor.getItemMeta().getDisplayName() == null||cursor.getItemMeta().getLore() == null) {
						p.sendMessage(prefix+red+"아이템의 제목 또는 설명이 비어있습니다");
						return;
					} else {
						ItemMeta im = cursor.getItemMeta();
						custom.setBaseName(im.getDisplayName());
						custom.setBaseLore(im.getLore());
						
						e.getInventory().setItem(49
								, createItem(new ItemStack(Material.PAPER),custom.getBaseName()+gray+" (base lore)",custom.getBaseLore()));
					
						//제목에 $이 들어간 아이템 로어 업데이트
					}
				} else {	
					if(e.getCurrentItem()!=null&&e.getClickedInventory().getType() == InventoryType.CHEST) { //아이템 설정
						if(e.getSlot()<36)
							p.openInventory(gui.getCustomItemGUI(custom, e.getSlot()));
					}
				}
			} else {
				return;
			}
			
		}
	}
	
	@EventHandler
	public void onClickSettingInv(InventoryClickEvent e) {
		if(e.getInventory().getTitle().contains("C Setting")) {
			String title = e.getInventory().getTitle();
			CustomItemDTO custom = CustomItemPlugin.getCustom(title.split("#")[1]);
			
			e.setCancelled(true);
			if(e.getSlot()==13) {
				CustomType toType = custom.getType().next();
				
				custom.setType(toType);
				e.getInventory().setItem(13, createItem(new ItemStack(toType.getIcon()),orange+toType.toString()
				,Arrays.asList(" ",gray+toType.getDescription()," ")));
				
			}
		}
	}
	
	@EventHandler
	public void onClickItemInv(InventoryClickEvent e) {
		if(e.getInventory().getTitle().contains("C Item")) {
			Player p = (Player) e.getWhoClicked();
			
			String title = e.getInventory().getTitle();
			CustomItemDTO custom = CustomItemPlugin.getCustom(title.split("#")[1]);
			
			int index = 1;
			try {
				index = Integer.parseInt(title.split("#")[2]);
			} catch(NumberFormatException e1) {
				e1.printStackTrace();
				return;
			}
			ItemStack item = custom.getContents().get(index);
			
			e.setCancelled(true);
			if(e.getSlot()==14) { //가져오기
				p.getInventory().addItem(item);
			} else if(e.getSlot()==15) { //삭제
				custom.getContents().remove(item);
				p.closeInventory();
			} else if(e.getSlot()==13) { //로어 수정
				if(custom.getType().isEdit()) {
					if(custom.getBaseLore().size()==0||custom.getBaseName()==null) {
						p.sendMessage(prefix+red+"먼저 기본로어를 설정하세요");
					} else {
						ItemMeta im = item.getItemMeta();
						im.setDisplayName(custom.getBaseName());
						im.setLore(custom.getBaseLore());
						item.setItemMeta(im);
						p.openInventory((new GUI_CustomItem()).getCustomItemLoreGUI(custom, index));
					}
				}
			} else if(e.getSlot()==26) {
				p.openInventory((new GUI_CustomItem()).getCustomGUI(custom, 1));
			}
		}
	}
	
	@EventHandler
	public void onClickLoreInv(InventoryClickEvent e) {
		if(e.getInventory().getTitle().contains("C Lore")) {
			String title = e.getInventory().getTitle();
			CustomItemDTO custom = CustomItemPlugin.getCustom(title.split("#")[1]);
			
			int index = 1;
			try {
				index = Integer.parseInt(title.split("#")[2]);
			} catch(NumberFormatException e1) {
				e1.printStackTrace();
				return;
			}
			ItemStack item = e.getCurrentItem();
			boolean edit = false;
			
			e.setCancelled(true);
			
			Player p = (Player) e.getWhoClicked();
			if(e.getSlot()==44) {
				p.openInventory((new GUI_CustomItem()).getCustomItemGUI(custom, index));
			} else if(item.getType() == Material.NAME_TAG) {
				int count = 1;
				try {
					count = Integer.parseInt(item.getItemMeta().getDisplayName().split("#")[1]);
				} catch(Exception e1) {
					e1.printStackTrace();
					return;
				}
				CustomItemPlugin.edit.put(p.getName(), new LoreEditDTO(true, 0, count,custom,index));
				edit = true;
			} else if(item.getType() == Material.BOOK) {
				int line = 0;
				int count = 0;;
				try {
					line = Integer.parseInt(item.getItemMeta().getDisplayName().split("#")[1].split("-")[0]);
					count = Integer.parseInt(item.getItemMeta().getDisplayName().split("#")[1].split("-")[1]);
				} catch(Exception e1) {
					e1.printStackTrace();
					return;
				}
				CustomItemPlugin.edit.put(e.getWhoClicked().getName(), new LoreEditDTO(false, line, count,custom,index));
				edit = true;
			}
			if(edit) {
				p.sendMessage(" ");
				p.sendMessage(prefix+"채팅창에 변수에 넣을 문구를 입력하세요. ");
				p.sendMessage(prefix+green+"'$' "+white+"를 입력하면 취소됩니다");
				p.sendMessage(" ");
				
				p.closeInventory();
			}
		}
	}
	
	@EventHandler
	public void onCloseSetting(InventoryCloseEvent e) {
		String title = e.getInventory().getTitle();
		if(title.contains("C Setting")) {
			CustomItemDTO custom = CustomItemPlugin.getCustom(title.split("#")[1]);
			
			Player p = (Player)e.getPlayer();
			new BukkitRunnable() {
				
				@Override
				public void run() {
					p.openInventory((new GUI_CustomItem()).getCustomGUI(custom, 1));
				}
			}.runTaskLater(Main.getInstance(), 1);
			
		}
	}
	
}
