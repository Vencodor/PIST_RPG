package main.java.pist.vencoder.server.executor;

import main.java.pist.vencoder.Main;
import main.java.pist.vencoder.PluginManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class PistCommand extends PluginManager implements Listener {
	
	public static HashMap<String,Class<?>> cmds = new HashMap<String,Class<?>>();
	
	public static List<String> bukkitCmds = Arrays.asList("op","deop","reload","pl");
	
	private static List<String> disablePlayer = new ArrayList<String>();
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayercommand(PlayerCommandPreprocessEvent e)	{
		Player p = e.getPlayer();
		String cmd = e.getMessage().split(" ")[0].replace("/", "");
		
		if(disablePlayer.contains(p.getName()))
			return;
		
		if(isContainsCmd(cmd)) {
			e.setCancelled(true);
			String[] args = e.getMessage().split(" ");
			ArrayList<String> argList = new ArrayList<String>(Arrays.asList(args));
			argList.remove(0);
			Class<?> commandClass = cmds.get(cmd);
			try {
				String className = commandClass.getSimpleName();
				Object instance = commandClass.getDeclaredConstructor().newInstance();
				
				if(className.toLowerCase().contains("manage")||commandClass.getField("opCmd").getBoolean(instance)) {
					if(!p.isOp()) {
						p.sendMessage(permission+"권한이 부족합니다! "+dgray+className);
						return;
					}
				}
				Method method = getMethod(commandClass,"onCommand");
				Object[] param = { p,cmd,e.getMessage().replace("/", ""),argList.toArray(new String[argList.size()])};
				method.invoke(instance, param);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else if(Bukkit.getPluginCommand(cmd)!=null||bukkitCmds.contains(cmd)) {
			
		} else if(!p.isOp()){
			e.setCancelled(true);
			String[] args = e.getMessage().split(" ");
			
			if(cmd.contains("!pass")) {
				disablePlayer.add(p.getName());
				
				p.chat("/"+args[1]);
				
				new BukkitRunnable() {
					@Override
					public void run() {
						disablePlayer.remove(p.getName());
					}
				}.runTaskLater(Main.getInstance(), 10);
				
				return;
			}
			
			String match = matchCommand(cmd);
			if(match!=null) {
				p.sendMessage(red+bold+" <!>"+gray+" 혹시 '"+orange+"/"+match+gray+"' 명령어를 찾으셨나요? ");
				
				TextComponent runCmd = new TextComponent(dgray+" [ '"+orange+"/"+cmd+dgray+"' 명령어를 계속 실행합니다 ]");
				runCmd.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/!pass "+cmd));
				p.spigot().sendMessage(runCmd);
			} else {
				p.sendMessage(red+bold+" <!>"+gray+" 명령어 목록을 확인하려면 '"+orange+"/help"+gray+"' 을(를) 입력하세요");
			}
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void register(Object clazz) {
		try {
			Field field = clazz.getClass().getField("commands");
			
			List<String> commands = (List<String>)field.get(clazz);
			for(String a : commands) {
				cmds.put(a, clazz.getClass());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	private boolean isContainsCmd(String cmd) {
		for(String a : cmds.keySet()) {
			if(cmd.equalsIgnoreCase(a))
				return true;
		}
		return false;
	}
	
	private String matchCommand(String str) {
		String matchString = null;
		int matchCount = 0;
		
		Random r = new Random();
		for(String a : cmds.keySet()) {
			try {
				Object instance = cmds.get(a).getDeclaredConstructor().newInstance();
				if(cmds.get(a).getField("opCmd").getBoolean(instance))
					continue;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			int count = 0;
			for(int i=0; i<str.length(); i++) {
				count = count + countChar(a, str.charAt(i));
			}
			if(matchCount<count) {
				matchString = a;
				matchCount = count;
			} else if(matchCount==count) {
				if(r.nextBoolean()) {
					matchString = a;
					matchCount = count;
				}
			}
		}
		
		return matchString;
	}
	
	private int countChar(String str, char ch) {
		return (int) str.chars()
		.filter(c -> c == ch)
		.count();
	}
	
	private Method getMethod(Class<?> clazz, String name) {
		for(Method a : clazz.getMethods()) {
			if(a.getName().equals(name))
				return a;
		}
		return null;
	}

}
