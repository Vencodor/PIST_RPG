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

public class Smash_Down extends EffectManager {

	@Override
	public void run(Player target, EffectSettingDTO setting) {
		if(setting == null)
			setting = Weapon.HAMMER.getSetting();
		
		Location loc = target.getLocation();
		Particles_1_8 particles = Main.particleAPI.getParticles_1_8();
		
		EffectSettingDTO set = setting;
		double duration = 0.3;
		new RepeatingTask(Main.getInstance(),0,1) {
			int count = 0;
			double t = 0;
			
			@Override
			public void run() {
				if(count>=duration*20) {
					cancel();
				}
				
				Location spawnLoc = loc.clone();
				for(int i=0; i<32; i++) {
					t = t + 3;
					
					for(int k=1; k<=2; k++) {
						double x = (k*Math.sin(1.2*t)+5.5)*Math.sin(0.005*t-1.2);
						double y = (k*Math.sin(1.2*t)+5.5)*Math.cos(0.005*t-1.2);
						double z = k*Math.cos(1.2*t);
						
						spawnLoc = loc.clone();
						spawnLoc.setPitch(0);
						
						Vector locVector = new Vector(x,y,z);
						locVector = VectorUtil.rotateVector(locVector, spawnLoc);
						locVector.setY(y);
						
						spawnLoc.add(locVector);
						
						Color color = Color.WHITE;
						if(set.getColor()!=null) {
							if(set.getColor().size()>k-1)
								color = set.getColor().get(k-1);
						}
						
						Object packet = particles.REDSTONE()
								.packetColored(true, spawnLoc, color);
						particles.sendPacket(loc, 30D, packet);
					}
				}
				
				count++;
				
			}
			
		};
		
		double r = setting.getRange()+2;
		for(Entity a : target.getNearbyEntities(r, r, r)) {
			if(a instanceof LivingEntity) {
				damage(target, (LivingEntity)a, 0.03, (float)setting.getDamage(), false);
			}
		}
		
	}
	
	
}
