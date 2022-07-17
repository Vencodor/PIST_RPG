package main.java.pist.plugins.stat.listener;

import main.java.pist.data.player.PlayerData;
import main.java.pist.manager.plugin.GuiManager;
import main.java.pist.plugins.stat.StatPlugin;
import main.java.pist.plugins.stat.inventory.StatGUI;
import main.java.pist.plugins.stat.object.EditDTO;
import main.java.pist.plugins.stat.object.PlayerStatDTO;
import main.java.pist.plugins.stat.object.enums.Stat;
import main.java.pist.util.Heads;
import main.java.pist.vencoder.Main;
import main.java.pist.vencoder.PluginManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class GUI_StatListener extends PluginManager implements Listener {

	@EventHandler
	public void onCloseInv(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		String title = e.getInventory().getTitle();
		if (title.contains("Status")) {
			if (!title.contains("Info")) {

			} else if (!title.contains("Info")) {
				StatPlugin.removeEditInfo(p.getName());
			} else { // Info창에서 뒤로가기 또는 창을 껏을때
				StatGUI gui = new StatGUI();
				new BukkitRunnable() {
					@Override
					public void run() {
						p.openInventory(gui.getStatInv(p, PlayerData.getData(p.getUniqueId()).getStat(), true));
					}
				}.runTaskLater(Main.getInstance(), 1);
			}
		}
	}

	@EventHandler
	public void onClickInv(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getInventory().getTitle().contains("Status")) {
			e.setCancelled(true);
			ItemStack item = e.getCurrentItem();
			if (item == null || item.getItemMeta() == null || item.getItemMeta().getDisplayName() == null)
				return;
			else {
				String name = item.getItemMeta().getDisplayName();
				if (name.contains("창 닫기")) {
					p.closeInventory();
					p.updateInventory();
					// SaveInv.load(p);
				} else if (name.contains("뒤로가기")) {
					p.closeInventory();
				} else if (name.contains("초기화")) {
					StatGUI gui = new StatGUI();

					StatPlugin.editInfo.put(p.getName(), new EditDTO());
					gui.updateStatGui(e.getInventory(), new EditDTO(), PlayerData.getData(p.getUniqueId()).getStat());
				} else if (name.contains("저장")) {
					EditDTO edit = StatPlugin.getEdit(p.getName());
					PlayerStatDTO playerStat = PlayerData.getData(p.getUniqueId()).getStat();
					if (edit == null || playerStat == null)
						return;
					playerStat.setStatPoint(edit.getStatPoint());
					playerStat.addStat(edit.getIncreaseStat());

					// ItemStack beforeItem = item;
					GuiManager gui = new GuiManager();
					Heads icon = new Heads();
					e.getInventory().setItem(e.getSlot(), gui.createItem(icon.getHead(
							"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDMxMmNhNDYzMmRlZjVmZmFmMmViMGQ5ZDdjYzdiNTVhNTBjNGUzOTIwZDkwMzcyYWFiMTQwNzgxZjVkZmJjNCJ9fX0="),
							green + "저장되었습니다"));

					new BukkitRunnable() {
						@Override
						public void run() {
							if (e.getInventory() != null) {
								if (p.getOpenInventory() != null && p.getOpenInventory().getTitle().contains("Stat")) {
									p.closeInventory();
									p.updateInventory();
								}
							}
						}
					}.runTaskLater(Main.getInstance(), (long) (20 * 0.85));
				} else if (name.contains("클릭당 증가폭")) {
					StatGUI gui = new StatGUI();
					Heads icon = new Heads();
					int count = gui.setNextClickIncrease(p);
					if (count == 1) {
						ItemStack head = icon.getHead(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzFiYzJiY2ZiMmJkMzc1OWU2YjFlODZmYzdhNzk1ODVlMTEyN2RkMzU3ZmMyMDI4OTNmOWRlMjQxYmM5ZTUzMCJ9fX0=");
						ItemMeta im = item.getItemMeta();
						e.getInventory().setItem(e.getSlot(),
								gui.createItem(head, green + "클릭당 증가폭" + dgray + bold + " (1)", im.getLore()));
					} else if (count == 3) {
						ItemStack head = icon.getHead(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWQ0ZWFlMTM5MzM4NjBhNmRmNWU4ZTk1NTY5M2I5NWE4YzNiMTVjMzZiOGI1ODc1MzJhYzA5OTZiYzM3ZTUifX19");
						ItemMeta im = item.getItemMeta();
						e.getInventory().setItem(e.getSlot(),
								gui.createItem(head, green + "클릭당 증가폭" + dgray + bold + " (3)", im.getLore()));
					} else if (count == 5) {
						ItemStack head = icon.getHead(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmQ1N2UzYmM4OGE2NTczMGUzMWExNGUzZjQxZTAzOGE1ZWNmMDg5MWE2YzI0MzY0M2I4ZTU0NzZhZTIifX19");
						ItemMeta im = item.getItemMeta();
						e.getInventory().setItem(e.getSlot(),
								gui.createItem(head, green + "클릭당 증가폭" + dgray + bold + " (5)", im.getLore()));
					} else if (count == 10) {
						ItemStack head = icon.getHead(
								"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDlkZDM2NzU5MzA3ZGI4ZTJkOWY0YjBjMmFlZDI1NTZkYjFkZGNmM2Y2N2ZhMTljYzgyNmFjYmQ5NjVmZSJ9fX0=");
						ItemMeta im = item.getItemMeta();
						e.getInventory().setItem(e.getSlot(),
								gui.createItem(head, green + "클릭당 증가폭" + dgray + bold + " (10)", im.getLore()));
					}
				} else {
					StatGUI gui = new StatGUI();
					if (name.contains("STR")|| name.contains("DEX")||name.contains("CON")||name.contains("INT")||name.contains("WIS")) {
						Stat click = null;
						if (name.contains("STR")) {
							click = Stat.STR;
						} else if (name.contains("DEX")) {
							click = Stat.DEX;
						} else if (name.contains("CON")) {
							click = Stat.CON;
						} else if (name.contains("INT")) {
							click = Stat.INT;
						} else if (name.contains("WIS")) {
							click = Stat.WIS;
						}

						if (e.getClick() == ClickType.LEFT) { // 추가
							EditDTO edit = StatPlugin.getEdit(p.getName());
							if (edit == null)
								return;

							int point = edit.getStatPoint();
							int increaseCount = edit.getIncreaseCount();
							if (point - increaseCount >= 0) {
								edit.setStatPoint(point - increaseCount);
								edit.getIncreaseStat().addStat(click.toString(), increaseCount);
								gui.updateStatGui(e.getInventory(), edit, PlayerData.getData(p.getUniqueId()).getStat());
							}
						} else if (e.getClick() == ClickType.RIGHT) { // 정보
							p.openInventory(gui.getStatInfoInv(click));
						}

					}
				}
				
			}
		}
	}

}
