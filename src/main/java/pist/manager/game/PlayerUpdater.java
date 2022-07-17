package main.java.pist.manager.game;

import main.java.pist.api.function.util.FormatUtil;
import main.java.pist.api.scheduler.RepeatingTask;
import main.java.pist.data.player.PlayerData;
import main.java.pist.data.player.object.PlayerDTO;
import main.java.pist.data.player.object.PlayerDynamicDTO;
import main.java.pist.manager.game.ability.getter.PlayerAbilityData;
import main.java.pist.plugins.instrument.kalimba.KalimbaPlugin;
import main.java.pist.vencoder.Main;
import main.java.pist.vencoder.PluginManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class PlayerUpdater extends PluginManager {
	
	public void registerAll() {
		registerRegenAbility();
		registerSetAbility();
		registerInfo();
		registerSetActionBar();
	}
	
	public void registerInfo() {
		int regenTime = 2;
		new RepeatingTask(Main.getInstance(),20*regenTime,20*regenTime) {
			
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					//PlayerInfoDTO info = PlayerData.getData(p.getUniqueId()).getInfo();
					PlayerManager.updateExp(p);
				}
			}
		};
	}
	
	public void registerRegenAbility() {
		int regenTime = 3;
		new RepeatingTask(Main.getInstance(),20*regenTime,20*regenTime) {
			
			@Override
			public void run() {
				for(Player a : Bukkit.getOnlinePlayers()) {
					PlayerDTO data = PlayerData.getData(a.getUniqueId());
					PlayerDynamicDTO dynamic = data.getDynamic();
					
					double regen = PlayerAbilityData.getRegenMp(a);
					if(dynamic.getMp()+regen <= dynamic.getMaxMp()) {
						dynamic.addMp(regen);
					} else {
						dynamic.setMp(dynamic.getMaxMp());
					}
					PlayerManager.updateMp(a);
				}
			}
		};
	}
	
	public void registerSetAbility() {
		int setTime = 3;
		new RepeatingTask(Main.getInstance(),20*setTime,20*setTime) {
			
			@Override
			public void run() {
				for(Player a : Bukkit.getOnlinePlayers()) {
					PlayerDynamicDTO dynamic = PlayerData.getData(a.getUniqueId()).getDynamic();
					
					a.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(PlayerAbilityData.getMaxHealth(a));
					a.setHealthScale(20.0);
					
					double speed = PlayerAbilityData.getSpeed(a);
					a.setWalkSpeed((float) speed);
					
					double maxMp = PlayerAbilityData.getMaxMp(a);
					dynamic.setMaxMp(maxMp==0?10:maxMp);
					
					PlayerManager.updateMp(a);
				}
			}
		};
	}
	
	public void registerSetActionBar() {
		new RepeatingTask(Main.getInstance(),10,10) {
			
			@Override
			public void run() {
				for(Player a : Bukkit.getOnlinePlayers()) {
					if(KalimbaPlugin.kalimba.containsKey(a.getName()))
						continue;
					
					double health = a.getHealth();
					double maxHealth = a.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
					double healthPer = (health / maxHealth) * 100;
					
					PlayerDynamicDTO dynamic = PlayerData.getData(a.getUniqueId()).getDynamic();
					double mp = dynamic.getMp();
					double mpPer = (mp / dynamic.getMaxMp()) * 100;
					
					StringBuffer content = new StringBuffer();
					//"&c︳│││││││││││││││││││││&7││││││││││︳ &c120&4♥ &8&l〳&b︳││││││││││││││││││││││││&7│││││││︳ &b36&1";
					//막대기 30개
					
					content.append(red+"       ︳");
					boolean pink = true;
					for(double i=1; i<=30; i++) {
						if(healthPer < i*3.3) { //회색
							if(pink) {
								content.append(gray);
								pink = false;
							}
						}
						content.append("│");
					}
					content.append("︳ "+red+FormatUtil.format(health)+dred+" ♥ "+dgray+bold+"〳");
					content.append(aqua+"︳");
					
					boolean aqua = true;
					for(double i=1; i<=30; i++) {
						if(mpPer < i*3.3) { //회색
							if(aqua) {
								content.append(gray);
								aqua = false;
							}
						}
						content.append("│");
					}
					content.append("︳ "+red+FormatUtil.format(mp)+blue+" ♦");
					a.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(content.toString()));
					
				}
			}
		};
		
	}
	
	public static void updateSpeed(Player p) {
		double speed = PlayerAbilityData.getSpeed(p);
		p.setWalkSpeed((float) speed);
	}
	
	public static void updateHealth(Player p) {
		p.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(PlayerAbilityData.getMaxHealth(p));
		p.setHealthScale(20.0);
	}
	
}
