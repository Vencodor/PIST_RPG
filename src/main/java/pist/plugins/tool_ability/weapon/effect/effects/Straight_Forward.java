package main.java.pist.plugins.tool_ability.weapon.effect.effects;

import com.github.fierioziy.particlenativeapi.api.Particles_1_8;
import main.java.pist.api.scheduler.RepeatingTask;
import main.java.pist.plugins.tool_ability.weapon.Weapon;
import main.java.pist.plugins.tool_ability.weapon.effect.EffectManager;
import main.java.pist.plugins.tool_ability.weapon.effect.EffectSettingDTO;
import main.java.pist.util.VectorUtil;
import main.java.pist.vencoder.Main;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Straight_Forward extends EffectManager {

	@Override
	public void run(Player target, EffectSettingDTO setting) {
		if(setting == null)
			setting = Weapon.BOW.getSetting();
		
		Location loc = target.getLocation();
		Particles_1_8 particles = Main.particleAPI.getParticles_1_8();
		
		EffectSettingDTO set = setting;
		double duration = 0.3;
		new RepeatingTask(Main.getInstance(),0,1) {
			int count = 0;
			double t = 0;
			List<Entity> damageEntites = new ArrayList<Entity>();
			Location spawnLoc = null;
			@Override
			public void run() {
				
				if(count>=duration*20) {
					cancel();
				}
				
				for(int i=0; i<10; i++) {
					t = t + 2;
					
					double x = (set.getRange()/90)*t+0.2;
					double y = target.getEyeHeight()-0.5;
					double z = 0;
					
					Vector locVector = new Vector(x, y, z);
					locVector = VectorUtil.rotateVector(locVector, loc);
					
					spawnLoc = loc.clone().add(locVector);
					
					if(!spawnLoc.getWorld().getBlockAt(spawnLoc).isEmpty()) {
						cancel();
					}
					Object packet = particles.REDSTONE()
							.packetColored(true, spawnLoc, Color.WHITE);
					particles.sendPacket(loc, 30D, packet);
					
					damageEntites.addAll(
							damageNearParticle(target, spawnLoc, damageEntites, (float)set.getDamage(), set.isMagic()));
				}
				
				if(set.getColor().get(0)!=Color.WHITE&&(count==0||count%2==0)) {
					for(int i=0; i<=15; i++) {
						double size = 0.43;
						
						double x = 0;
						double y = Math.sin(0.835*i)*size;
						double z = Math.cos(0.835*i)*size;
						
						Vector locVector = new Vector(x, y, z);
						locVector = VectorUtil.rotateVector(locVector, loc);
						
						Object packet = particles.REDSTONE()
								.packetColored(true, spawnLoc.clone().add(locVector), set.getColor().get(0));
						particles.sendPacket(loc, 30D, packet);
					}
					
				}
				count++;
			}
			
		};
		/*
		double r = setting.getRange()+2;
		for(Entity a : target.getNearbyEntities(r, r, r)) {
			if(a instanceof LivingEntity) {
				damage(target, (LivingEntity)a, 0.03, (float)setting.getDamage(), false);
			}
		}
		*/
	}
	
	
}
