package main.java.pist.plugins.entity.mobEvent.listener;

import main.java.pist.plugins.customItem.CustomItemPlugin;
import main.java.pist.plugins.entity.mobEvent.MobEventPlugin;
import main.java.pist.plugins.entity.mobEvent.object.MobDropDTO;
import main.java.pist.plugins.system.exp.ExpPlugin;
import main.java.pist.plugins.system.money.MoneyPlugin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class MobEventListener implements Listener {
	
	@EventHandler
	public void onEntityDie(EntityDeathEvent e) {
		Entity entity = e.getEntity();
		if(entity instanceof LivingEntity && !(entity instanceof Player) && ((LivingEntity) entity).getKiller() instanceof Player) {
			MobDropDTO drop = MobEventPlugin.getMobDrop(e.getEntity().getName());
			if(drop != null) {
				Player p = ((LivingEntity) entity).getKiller();
				ExpPlugin.addExp(p, drop.getDropExp());
				MoneyPlugin.addMoney(p, drop.getDropMoney());

				e.setDroppedExp(0);
				e.getDrops().clear();

				for(ItemStack a : CustomItemPlugin.getContents(drop.getDropItem())) {
					Item item = entity.getLocation().getWorld().dropItem(entity.getLocation(), a);
					item.setGlowing(true);
				}
			}
		}
	}
	
}
