package main.java.pist.vencoder;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.plugin.ParticleNativePlugin;
import main.java.pist.api.function.util.DateUtil;
import main.java.pist.data.database.dao.cook.CookDAO;
import main.java.pist.data.database.dao.customItem.CustomItemDAO;
import main.java.pist.data.database.dao.quest.MainQuestDAO;
import main.java.pist.data.database.dao.quest.SubQuestDAO;
import main.java.pist.data.player.PlayerData;
import main.java.pist.manager.game.PlayerUpdater;
import main.java.pist.manager.game.ability.PlayerAbility;
import main.java.pist.plugins.collect.grass.listener.CollectGrassListener;
import main.java.pist.plugins.collect.ore.listener.CollectOreListener;
import main.java.pist.plugins.cook.CookPlugin;
import main.java.pist.plugins.cook.executor.CookManageCommand;
import main.java.pist.plugins.cook.listener.CookGUI_Listener;
import main.java.pist.plugins.cook.listener.CookListener;
import main.java.pist.plugins.cook.object.CookDTO;
import main.java.pist.plugins.customItem.CustomItemPlugin;
import main.java.pist.plugins.customItem.executor.CustomItemCommand;
import main.java.pist.plugins.customItem.listener.GUI_CustomItemListener;
import main.java.pist.plugins.customItem.listener.LoreEditListener;
import main.java.pist.plugins.customItem.object.CustomItemDTO;
import main.java.pist.plugins.entity.mob.entities.custom.CustomEntities;
import main.java.pist.plugins.entity.mob.executor.MobManageCommand;
import main.java.pist.plugins.entity.mobEvent.executor.MobEventManageCommand;
import main.java.pist.plugins.entity.mobEvent.listener.MobEventListener;
import main.java.pist.plugins.fishing.executor.FishManageCommand;
import main.java.pist.plugins.fishing.listener.FishingListener;
import main.java.pist.plugins.instrument.kalimba.listener.KalimbaListener;
import main.java.pist.plugins.quest.integrated.executor.QuestCommand;
import main.java.pist.plugins.quest.integrated.listener.QuestGUI_Listener;
import main.java.pist.plugins.quest.integrated.listener.QuestUpdateListener;
import main.java.pist.plugins.quest.main_quest.MainQuestPlugin;
import main.java.pist.plugins.quest.main_quest.executor.MainQuestManageCommand;
import main.java.pist.plugins.quest.main_quest.listener.MainQuestGUI_Listener;
import main.java.pist.plugins.quest.main_quest.listener.MainQuestListener;
import main.java.pist.plugins.quest.main_quest.object.MainQuestDTO;
import main.java.pist.plugins.quest.sub_quest.SubQuestPlugin;
import main.java.pist.plugins.quest.sub_quest.executor.SubQuestManageCommand;
import main.java.pist.plugins.quest.sub_quest.listener.SubQuestGUI_Listener;
import main.java.pist.plugins.quest.sub_quest.listener.SubQuestListener;
import main.java.pist.plugins.quest.sub_quest.object.SubQuestDTO;
import main.java.pist.plugins.stat.executor.StatCommand;
import main.java.pist.plugins.stat.executor.StatManageCommand;
import main.java.pist.plugins.stat.listener.GUI_StatListener;
import main.java.pist.plugins.system.biome.BiomePlugin;
import main.java.pist.plugins.system.biome.executor.BiomeManageCommand;
import main.java.pist.plugins.system.biome.listener.BiomeDetectListener;
import main.java.pist.plugins.system.biome.listener.effect.NoAirEffectListener;
import main.java.pist.plugins.system.biome.listener.effect.NoWaterEffectListener;
import main.java.pist.plugins.system.convenience.combineItem.listener.CombineListener;
import main.java.pist.plugins.system.debug.executor.DebugCommand;
import main.java.pist.plugins.system.exp.executor.ExpCommand;
import main.java.pist.plugins.system.exp.executor.ExpManageCommand;
import main.java.pist.plugins.system.link.connect_client.ConnectClient;
import main.java.pist.plugins.system.link.executor.LinkCommand;
import main.java.pist.plugins.system.macroTest.Listener.MacroTestGUI_Listener;
import main.java.pist.plugins.system.manage.chatClear.executor.ChatClearExecutor;
import main.java.pist.plugins.system.manage.commandSpy.executor.CommandSpyCommand;
import main.java.pist.plugins.system.manage.commandSpy.listener.CommandSpyListener;
import main.java.pist.plugins.system.money.executor.MoneyCommand;
import main.java.pist.plugins.system.money.executor.MoneyManageCommand;
import main.java.pist.plugins.system.moveCancel.executor.MoveCancelManageCommand;
import main.java.pist.plugins.system.moveCancel.listener.MoveCancelListener;
import main.java.pist.plugins.tool_ability.executor.ToolManageCommand;
import main.java.pist.plugins.tool_ability.listener.ToolUpdater;
import main.java.pist.plugins.tool_ability.weapon.listener.PlayerWeaponInteract;
import main.java.pist.vencoder.server.executor.PistCommand;
import main.java.pist.vencoder.server.listener.ChatListener;
import main.java.pist.vencoder.server.listener.EventListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class Main extends JavaPlugin {

	public static HashMap<String, String> textureCache = new HashMap<String, String>();

	public static Main instance;
	public static ParticleNativeAPI particleAPI;

	public static Main getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		instance = this;
		
		particleAPI = ParticleNativePlugin.getAPI();
		//particleAPI = ParticleNativeCore.loadAPI(this);

		org.bukkit.plugin.PluginManager pm = getServer().getPluginManager();

		pm.registerEvents(new PistCommand(), this);

		pm.registerEvents(new GUI_StatListener(), this);
		pm.registerEvents(new GUI_CustomItemListener(), this);
		pm.registerEvents(new SubQuestGUI_Listener(), this);
		pm.registerEvents(new QuestGUI_Listener(), this);
		pm.registerEvents(new MainQuestGUI_Listener(), this);
		pm.registerEvents(new CookGUI_Listener(), this);
		pm.registerEvents(new MacroTestGUI_Listener(), this);
		
		pm.registerEvents(new KalimbaListener(), this);
		
		pm.registerEvents(new MainQuestListener(), this);
		pm.registerEvents(new SubQuestListener(), this);
		pm.registerEvents(new QuestUpdateListener(), this);
		pm.registerEvents(new PlayerWeaponInteract(), this);
		pm.registerEvents(new CookListener(), this);
		pm.registerEvents(new CombineListener(), this);
		pm.registerEvents(new CollectOreListener(), this);
		pm.registerEvents(new CollectGrassListener(), this);
		pm.registerEvents(new FishingListener(), this);
		pm.registerEvents(new CommandSpyListener(), this);
		pm.registerEvents(new MobEventListener(), this);
		pm.registerEvents(new MoveCancelListener(), this);
		
		pm.registerEvents(new EventListener(), this);
		pm.registerEvents(new ChatListener(), this);
		pm.registerEvents(new LoreEditListener(), this);
		pm.registerEvents(new ToolUpdater(), this);
		
		pm.registerEvents(new BiomeDetectListener(), this);
		pm.registerEvents(new NoWaterEffectListener(), this);
		pm.registerEvents(new NoAirEffectListener(), this);
		
		// pm.registerEvents(new ArmorDefenseListener(), this);
		// pm.registerEvents(new WeaponAttackListener(), this); 현재 쓸데가 없음
		// pm.registerEvents(new LoadEventListener(), this);

		pm.registerEvents(new PlayerAbility(), this);

		PistCommand.register(new CustomItemCommand());
		PistCommand.register(new StatCommand());
		PistCommand.register(new StatManageCommand());
		PistCommand.register(new SubQuestManageCommand());
		PistCommand.register(new MainQuestManageCommand());
		PistCommand.register(new DebugCommand());
		PistCommand.register(new MoneyCommand());
		PistCommand.register(new MoneyManageCommand());
		PistCommand.register(new ExpCommand());
		PistCommand.register(new ExpManageCommand());
		PistCommand.register(new QuestCommand());
		PistCommand.register(new MobManageCommand());
		PistCommand.register(new CookManageCommand());
		PistCommand.register(new BiomeManageCommand());
		PistCommand.register(new FishManageCommand());
		PistCommand.register(new CommandSpyCommand());
		PistCommand.register(new ChatClearExecutor());
		PistCommand.register(new MobEventManageCommand());
		PistCommand.register(new MoveCancelManageCommand());
		PistCommand.register(new ToolManageCommand());
		PistCommand.register(new LinkCommand());
		
		PlayerUpdater playerAbility = new PlayerUpdater();
		
		CustomEntities.registerEntities();
		playerAbility.registerAll();

		CustomItemPlugin.customItems = CustomItemDAO.getList();
		CookPlugin.cook = CookDAO.getList();
		MainQuestPlugin.quest = MainQuestDAO.getList();
		SubQuestPlugin.quest = SubQuestDAO.getList();
		
		try {
			File filter = new File(getDataFolder() + "/filterWords.txt");
			if(!filter.exists()) {
				File config = getDataFolder();
				if(!config.exists()) {
					config.mkdir();
				}
				filter.createNewFile();
			} else {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(new FileInputStream(filter.getPath()),"UTF8")
				);
				
				String str;
				while ((str = reader.readLine()) != null) {
					//System.out.println(str);
					ChatListener.filterWords.addAll(Arrays.asList(str.split(",")));
				}
				reader.close();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		BiomePlugin.settingEffects();
		
		for(Player a : Bukkit.getOnlinePlayers()) {
			PlayerData.putData(a.getUniqueId());
			
			if(a.isOp()) {
				a.sendMessage(" ");
				a.sendMessage(ChatColor.GRAY+""+ChatColor.BOLD+"[ "+ChatColor.GOLD+ChatColor.BOLD+"SYSTEM"
				+ChatColor.GRAY+ChatColor.BOLD+" ] "+ChatColor.WHITE+"플러그인을 정상적으로 구동하였습니다.");
				a.sendMessage(ChatColor.DARK_GRAY+" ( "+DateUtil.getHourDate(new Date())+" )");
				//a.sendMessage(ChatColor.DARK_GRAY+" ( 본 메시지는 op에게만 전송됩니다 )");
				a.sendMessage(" ");
				
			}
		}

		try {
			ConnectClient.connect();
		} catch (IOException e) {
			System.out.println("Discord Bot 서버와의 연결에 실패하였습니다");
		}
	}

	@Override
	public void onDisable() {
//		if(PlayerData.saveData()) {
//			System.out.println("데이터 저장오류");
//		}
		
		CustomItemDAO.reset();
		for (CustomItemDTO a : CustomItemPlugin.customItems)
			CustomItemDAO.write(a);
		
		CookDAO.reset();
		for(CookDTO a : CookPlugin.cook.values())
			CookDAO.write(a);
		
		MainQuestDAO.reset();
		for(MainQuestDTO a : MainQuestPlugin.quest.values())
			MainQuestDAO.write(a);
		
		SubQuestDAO.reset();
		for(SubQuestDTO a : SubQuestPlugin.quest.values())
			SubQuestDAO.write(a);
		
		CustomEntities.unregisterEntities();
	}

}
