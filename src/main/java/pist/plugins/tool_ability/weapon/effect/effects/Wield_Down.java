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

public class Wield_Down extends EffectManager{
	
	@Override
	public void run(Player target, EffectSettingDTO setting) {
		if(setting == null)
			setting = Weapon.AXE.getSetting();
		
		Location loc = target.getLocation();
		Particles_1_8 particles = Main.particleAPI.getParticles_1_8();
		
		double duration = 0.13;
		
		EffectSettingDTO set = setting;
		new RepeatingTask(Main.getInstance(),0,1) {
			int count = 0;
			double t = 0;
			
			@Override
			public void run() {
				if(count>=20*duration)
					cancel();
				for(int i = 0; i<30; i++) {
					t = t + 1;
					
					double size = 0;
					for(int k=0; k<4; k++) {
						size = 1.35 + (0.15*k);
						double sizeMultiply = 0.02;
						if(k>1) {
							sizeMultiply = 0.012; //0.015
						}
						double x = 0.8*size*(3*Math.sin(sizeMultiply*t))+0.5;
						double y = size*(3*Math.cos(sizeMultiply*t));
						double z = 0;
						
						Location spawnLoc = loc.clone();
						spawnLoc.setPitch(0);
						
						Vector locVector = new Vector(x,y,z);
						locVector = VectorUtil.rotateVector(locVector, spawnLoc);
						locVector.setY(y);
						
						spawnLoc.add(locVector);
						
						Color color = Color.WHITE;
						if(set.getColor()!=null) {
							if(set.getColor().size()>k)
								color = set.getColor().get(k);
						}
						
						Object packet = particles.REDSTONE().packetColored(true, spawnLoc, color);
						particles.sendPacket(loc, 30D, packet);
					}
				}
				count++;
			}
		};
		
		double r = setting.getRange();
		for(Entity a : target.getNearbyEntities(r, r, r)) {
			if(a instanceof LivingEntity) {
				damage(target, (LivingEntity)a, 0.2, (float)setting.getDamage(), false);
			}
		}
		
	}
	
}
