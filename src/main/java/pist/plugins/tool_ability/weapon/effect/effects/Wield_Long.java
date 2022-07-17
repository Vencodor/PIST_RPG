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
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;

public class Wield_Long extends EffectManager {

	@Override
	public void run(Player target, EffectSettingDTO setting) {
		if(setting == null)
			setting = Weapon.LONG_SWORD.getSetting();
		
		Location loc = target.getLocation();
		Particles_1_8 particles = Main.particleAPI.getParticles_1_8();
		
		Vector direction = target.getEyeLocation().getDirection().normalize();
		
		Random ran = new Random();
		double duration = 0.13;
		
		EffectSettingDTO set = setting;
		new RepeatingTask(Main.getInstance(),0,1) {
			int count = 0;
			double t = 0;
			
			@Override
			public void run() {
				if(count>=20*duration)
					cancel();
				for(double i = 0; i<30; i++) {
					t = t + 2;
					
					double x = Math.abs(-2*Math.sin(0.018*t)+Math.sin(1.8*t))+(set.getRange()/2);
					double y = 0.1*Math.cos(0.018*t)+0.5;
					double z = 3*Math.cos(0.018*t);
					
					Location spawnLoc = loc.clone();
					spawnLoc.setPitch(0);
					
					Vector locVector = new Vector(x,y,z);
					locVector = VectorUtil.rotateVector(locVector, spawnLoc);
					locVector.setY(y);
					
					spawnLoc.add(locVector);
					
					Color color = Color.WHITE;
					if(set.getColor()!=null)
						color = set.getColor().get(ran.nextInt(set.getColor().size()));
					
					Object packet = particles.REDSTONE().packetColored(true, spawnLoc, color);
					particles.sendPacket(loc, 30D, packet);
				}
				count++;
			}
		};
		
		addVelocity(target, direction.multiply(-0.25));
		
		double r = setting.getRange();
		for(Entity a : target.getNearbyEntities(r, r, r)) {
			if(a instanceof LivingEntity) {
				damage(target, (LivingEntity)a, 0.2, (float)setting.getDamage(), false);
			}
		}
		
	}
	
}
