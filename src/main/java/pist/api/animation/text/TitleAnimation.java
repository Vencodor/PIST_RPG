package main.java.pist.api.animation.text;

import main.java.pist.api.scheduler.RepeatingTask;
import main.java.pist.vencoder.Main;
import org.bukkit.entity.Player;

import java.util.List;

public class TitleAnimation {
	
	public static void run(Player p, String subTitle, TitleAnimations ani) {
		List<String> scene = ani.getSceneText();
		int sceneDuration = ani.getSceneDuration();
		new RepeatingTask(Main.getInstance(),sceneDuration+5,sceneDuration) {
			int index = 0;
			@Override
			public void run() {
				p.sendTitle(scene.get(index), subTitle, 0, sceneDuration+5, 5);
				if(scene.size()<=index+1) {
					if(ani.isLastSceneAnimation()) {
						p.sendTitle(scene.get(index), subTitle, 23, sceneDuration+30, 30);
					} else {
						p.sendTitle(scene.get(index), subTitle, 0, sceneDuration+20, 5);
					}
					cancel();
				}
				index++;
			}
		};
	}
	
}
