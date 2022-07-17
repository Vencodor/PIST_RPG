package main.java.pist.plugins.system.biome.listener.effect;

import main.java.pist.api.animation.text.TitleAnimation;
import main.java.pist.api.animation.text.TitleAnimations;
import main.java.pist.api.scheduler.RepeatingTask;
import main.java.pist.plugins.system.biome.listener.BiomeDetectListener;
import main.java.pist.vencoder.Main;
import main.java.pist.vencoder.PluginManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

class NoAir {
	long joinTime = System.currentTimeMillis();
	int step = -1;
	
	public long getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public NoAir() {
	}
}

public class NoAirEffectListener extends PluginManager implements Listener {
	
	private static HashMap<Player, NoAir> progress = new HashMap<Player, NoAir>();
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if(e.getTo().getY()-e.getFrom().getY()!=0) {
			Player p = e.getPlayer();
			if(e.getTo().getY()>=180) {
				if(progress.get(p)==null) {
					progress.put(p, new NoAir());
				}
			} else {
				if(progress.get(p)!=null) {
					progress.remove(p);
				}
			}
		}
	}
	
	public void checkScheduler() {
		new RepeatingTask(Main.getInstance(),20*2,20*2) {
			@Override
			public void run() {
				for(Player a : progress.keySet()) {
					NoAir na = progress.get(a);
					
					int time = (int) (System.currentTimeMillis()/1000-na.getJoinTime()/1000);
					
					if(time > 10) { //5분 경과 : 탈수, 현기증
						if(na.getStep()==1) { //현기증 걸림
							na.setStep(2);
							TitleAnimation.run(a, gray+"당신은 "+dgray+"현기증"+gray+"에 걸렸습니다", TitleAnimations.WARNING);
						}
						a.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*6, 0));
						a.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20*6, 1));
					} else if(time > 5) { //30초 경과 : 고산병
						if(na.getStep()==0) { //탈수 걸림
							na.setStep(1);
							TitleAnimation.run(a, gray+"당신은 "+gray+"고산병"+gray+"에 걸렸습니다", TitleAnimations.WARNING);
						}
						a.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*6, 0));
					} else { //0분 경과 : 공기 희박
						if(na.getStep()==-1) {
							na.setStep(0);
							
							if(!BiomeDetectListener.containsDisable(a)) {
								a.sendMessage(" ");
								a.sendMessage(gray+bold+"주의! "+white+"이곳의 산소는 매우 희박합니다. 이곳에 장시간 머무르기를 피하십시오.");
								a.sendMessage(" ");
							}
						}
						
					}
				}
			}
		};
	}
	
}
