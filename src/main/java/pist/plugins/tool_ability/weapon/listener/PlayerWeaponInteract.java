package main.java.pist.plugins.tool_ability.weapon.listener;

import main.java.pist.plugins.tool_ability.weapon.Weapon;
import main.java.pist.plugins.tool_ability.weapon.effect.EffectSettingDTO;
import main.java.pist.vencoder.Main;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Method;
import java.util.Arrays;

public class PlayerWeaponInteract implements Listener {

	// private static HashMap<String,Double> charging = new
	// HashMap<String,Double>();

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (!(e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK))
			return;
		Player p = e.getPlayer();
		ItemStack item = p.getInventory().getItemInMainHand();

		if (item == null)
			return;

		net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		if (itemCompound.getString("attack") != null) {
			String attack = itemCompound.getString("attack");

			Weapon type = Weapon.get(attack);
			if (type != null) {
				if(type != Weapon.BOW) {
					if(e.getAction() == Action.LEFT_CLICK_BLOCK)
						return;
				}
				try {
					e.setCancelled(true);
					Method m = getRunMethod(type.getEffect());
					Object instance = type.getEffect().getConstructors()[0].newInstance();
					m.invoke(instance, p, null);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

	}

	@EventHandler
	public void onShoot(EntityShootBowEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			ItemStack item = e.getBow();

			net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
			NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
			if (itemCompound.getString("attack") != null && itemCompound.getString("attack").equals("활")) {
				e.setCancelled(true);
				try {
					EffectSettingDTO setting = Weapon.BOW.getSetting().clone();
					setting.setRange(setting.getRange() * (e.getForce() + 1));
					Color color = Color.WHITE;
					if (e.getForce() <= 0.3) {
						color = Color.fromRGB(221, 170, 255);
					} else if (e.getForce() <= 0.6) {
						color = Color.fromRGB(187, 85, 255);
					} else if (e.getForce() <= 0.9) {
						color = Color.fromRGB(153, 0, 255);
					} else if (e.getForce() <= 1) {
						color = Color.fromRGB(153, 0, 204);
					}
					setting.setColor(Arrays.asList(color));

					Method m = getRunMethod(Weapon.BOW.getEffect());
					Object instance = Weapon.BOW.getEffect().getConstructors()[0].newInstance();
					m.invoke(instance, p, setting);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		}
	}

	@EventHandler
	public void onRightClickBow(PlayerInteractEvent e) {
		if(e.getAction() != Action.RIGHT_CLICK_AIR&&e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		
		Player p = e.getPlayer();
		ItemStack item = p.getInventory().getItemInMainHand();
		
		net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound itemCompound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		if (itemCompound.getString("attack") != null && itemCompound.getString("attack").equals("활")) {
			ItemStack beforeItem = p.getInventory().getItem(9);
			p.getInventory().setItem(9, new ItemStack(Material.ARROW));
			new BukkitRunnable() {
				
				@Override
				public void run() {
					p.getInventory().setItem(9, beforeItem);
				}
			}.runTaskLater(Main.getInstance(), 1);
		}
	}

	private Method getRunMethod(Class<?> clazz) throws NoSuchMethodException {
		return clazz.getMethod("run", Player.class, EffectSettingDTO.class);
	}

}
