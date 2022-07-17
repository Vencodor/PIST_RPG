package main.java.pist.plugins.tool_ability.weapon.effect;

import com.github.fierioziy.particlenativeapi.api.Particles_1_8;
import main.java.pist.api.function.util.Vector3D;
import main.java.pist.vencoder.Main;
import net.minecraft.server.v1_12_R1.DamageSource;
import net.minecraft.server.v1_12_R1.EntityHuman;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public abstract class EffectManager {
	
	public abstract void run(Player target, EffectSettingDTO setting);
	
	public boolean damage(Player target, LivingEntity a, double range, float damage, boolean magic) {
		if(Vector3D.isLookingLoc(target, a.getLocation(), range)) {
			if(!magic) {
				((CraftLivingEntity)a).getHandle()
				.damageEntity(DamageSource.playerAttack((EntityHuman)((CraftPlayer)target).getHandle()),damage);
			} else {
				((CraftLivingEntity)a).getHandle().damageEntity(DamageSource
						.a((net.minecraft.server.v1_12_R1.Entity)((CraftPlayer)target).getHandle()),damage);
			}
			
			Particles_1_8 particle = Main.particleAPI.getParticles_1_8();
			particle.REDSTONE().packetColored(true, a.getLocation().clone().add(0,2,0), Color.RED);
			return true;
		}
		return false;
	}
	
	public List<Entity> damageNearParticle(Player target, Location loc,List<Entity> cancelEntites, float damage, boolean magic) {
		List<Entity> nears = (List<Entity>) loc.getWorld().getNearbyEntities(loc, 0.2, 0.2, 0.2); 
		for(Entity a : nears) {
			if(a instanceof LivingEntity) {
				if(cancelEntites.contains(a))
					continue;
				
				if(!magic) {
					((CraftLivingEntity)a).getHandle()
					.damageEntity(DamageSource.playerAttack((EntityHuman)((CraftPlayer)target).getHandle()),damage);
				} else {
					((CraftLivingEntity)a).getHandle().damageEntity(DamageSource
							.a((net.minecraft.server.v1_12_R1.Entity)((CraftPlayer)target).getHandle()),damage);
				}
			}
		}
		return nears;
		
	}
	
	public void addVelocity(Entity target, Vector v) {
		target.setVelocity(target.getVelocity().add(v));
	}
	
}
