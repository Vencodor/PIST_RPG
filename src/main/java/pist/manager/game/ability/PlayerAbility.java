package main.java.pist.manager.game.ability;

import main.java.pist.manager.game.PlayerManager;
import main.java.pist.manager.game.ability.getter.PlayerAbilityData;
import main.java.pist.manager.game.ability.getter.PlayerDefenseAbilityData;
import net.minecraft.server.v1_12_R1.DamageSource;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;

import java.util.Random;

@SuppressWarnings("deprecation")
public class PlayerAbility implements Listener {

	private boolean handlingEvent;

	@EventHandler(priority = EventPriority.HIGH)
	public void onAttack(EntityDamageByEntityEvent e) { // 근접공격

		double damage = 0;
		DamageCause lastCause = e.getCause();

		if (e.getDamager() instanceof Player) {
			if (handlingEvent)
				return;
			
			Player damager = (Player) e.getDamager();
			
			if (lastCause == DamageCause.ENTITY_ATTACK || lastCause == DamageCause.ENTITY_SWEEP_ATTACK) { // 물리
				if(PlayerAbilityData.getMagicDamage(damager)>0) { //마법 데미지가 붙은 검으로 때릴 경우
					//EntityDamageByEntityEvent event = new EntityDamageByEntityEvent(damager, e.getEntity(), DamageCause.MAGIC, 0);
					//Bukkit.getServer().getPluginManager().callEvent(event);
					
					((CraftLivingEntity)e.getEntity()).getHandle().damageEntity(DamageSource
							.a((net.minecraft.server.v1_12_R1.Entity)((CraftPlayer)damager).getHandle()),(float) 0);
					e.setCancelled(true);
					return;
				} else {
					damage = damage + PlayerAbilityData.getAttackDamage(damager);
	
					if (getRandom(PlayerAbilityData.getCritical(damager))) {
						damage = damage * PlayerAbilityData.getCriticalDamage(damager) + damage;
						PlayerManager.sendActionBar(damager, "크리발동");
					}
				}

			} else if (lastCause == DamageCause.MAGIC || lastCause == DamageCause.THORNS) { // 마법
				damage = damage + PlayerAbilityData.getMagicDamage(damager);
				damager.sendMessage(damage+"1");
				if (getRandom(PlayerAbilityData.getCriticalMagic(damager))) {
					damage = damage * PlayerAbilityData.getCriticalMagicDamage(damager) + damage;
					PlayerManager.sendActionBar(damager, "마법 크리발동");
				}

			}
		}
		Entity entity = e.getEntity();
		if (e.getEntity() instanceof Player) {
			Player victim = (Player) entity;

			if (lastCause == DamageCause.ENTITY_ATTACK || lastCause == DamageCause.ENTITY_SWEEP_ATTACK) {
				damage = damage - PlayerDefenseAbilityData.getDefense(victim);
			} else if (e.getCause() == DamageCause.MAGIC || lastCause == DamageCause.THORNS) {
				damage = damage - PlayerDefenseAbilityData.getMagicDefense(victim);
				e.getDamager().sendMessage(damage+"2");
			}

			if (damage <= 0) {
				damage = 0;
			} else {
				double miss = PlayerAbilityData.getMiss(victim);
				if (getRandom(miss)) {
					damage = 0;
					PlayerManager.sendActionBar(victim, "데미지를 회피하였습니다");
				}

			}
			try {
				e.setDamage(DamageModifier.ARMOR, 0);
			} catch (UnsupportedOperationException e1) {

			}
		}
		e.setDamage(damage);

//			handlingEvent = true;
//			((CraftEntity)e.getEntity()).getHandle()
//			.damageEntity(DamageSource.playerAttack((EntityHuman) ((CraftPlayer)damager).getHandle()), (float)damage);
//			handlingEvent = false;
	}

	private boolean getRandom(double per) {
		Random random = new Random();
		if (random.nextInt(10000) <= per * 100) {
			return true;
		}
		return false;
	}
}
