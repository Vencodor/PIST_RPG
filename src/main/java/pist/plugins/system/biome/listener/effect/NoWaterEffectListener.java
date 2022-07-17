package main.java.pist.plugins.system.biome.listener.effect;

import main.java.pist.api.animation.text.TitleAnimation;
import main.java.pist.api.animation.text.TitleAnimations;
import main.java.pist.api.scheduler.RepeatingTask;
import main.java.pist.plugins.system.biome.events.BiomeUpdateEvent;
import main.java.pist.plugins.system.biome.listener.BiomeDetectListener;
import main.java.pist.plugins.system.biome.object.BiomeEffect;
import main.java.pist.vencoder.Main;
import main.java.pist.vencoder.PluginManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

class NoWater {
	long joinTime = System.currentTimeMillis();
	int step = -1;
	boolean treat = false; //치료 여부 (물 마셨는지)
	
	public long getJoinTime() {
		return joinTime;
	}
	public void setJoinTime(long joinTime) {
		this.joinTime = joinTime;
	}
	public boolean isTreat() {
		return treat;
	}
	public void setTreat(boolean treat) {
		this.treat = treat;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public NoWater() {
	}
}

public class NoWaterEffectListener extends PluginManager implements Listener {
	
	private static HashMap<Player, NoWater> progress = new HashMap<Player, NoWater>();
	
	@EventHandler
	public void onBiomeUpdate(BiomeUpdateEvent e) {
		Player p = e.getPlayer();
			
		if(e.getTo()!=null&&e.getTo().getEffect() == BiomeEffect.탈수) {
			progress.put(p, new NoWater());
		} else {
			progress.remove(p);
		}
	}
	
	@EventHandler
	public void onDrinkWater(PlayerItemConsumeEvent e) {
		ItemStack is = e.getItem();
        if(is.getType().equals(Material.POTION)){
        	Player p = e.getPlayer();
        	
            if(progress.containsKey(p)) {
            	progress.get(p).setTreat(true);
            	
            	p.sendTitle("", "물을 마셔서 "+blue+"탈수"+white+"가 해결되었습니다!",10,20*2,30);
            	//상쾌함 버프 5분
            }
        }
	}
	
	public void checkScheduler() {
		new RepeatingTask(Main.getInstance(),20*2,20*2) {
			@Override
			public void run() {
				for(Player a : progress.keySet()) {
					NoWater nw = progress.get(a);
					if(nw.isTreat())
						continue;
					int time = (int) (System.currentTimeMillis()/1000-nw.getJoinTime()/1000);
					
					if(time > 10) { //20분 경과 : 탈수, 현기증
						if(nw.getStep()==1) { //현기증 걸림
							nw.setStep(2);
							TitleAnimation.run(a, gray+"당신은 "+dblue+"현기증"+gray+"에 걸렸습니다", TitleAnimations.WARNING);
						}
						a.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20*6, 0));
						a.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20*6, 1));
					} else if(time > 5) { //15분 경과 : 탈수
						if(nw.getStep()==0) { //탈수 걸림
							nw.setStep(1);
							TitleAnimation.run(a, gray+"당신은 "+blue+"탈수"+gray+"에 걸렸습니다", TitleAnimations.WARNING);
						}
					} else { //0분 경과 : 후덥지근
						if(nw.getStep()==-1) {
							nw.setStep(0);
							
							if(BiomeDetectListener.containsDisable(a)) {
								a.sendMessage(" ");
								a.sendMessage(blue+bold+"주의! "+white+"이곳의 기후는 매우 덥습니다. 물을 마시길 권장드립니다.");
								a.sendMessage(" ");
							}
						}
						
					}
				}
			}
		};
	}
	
}
