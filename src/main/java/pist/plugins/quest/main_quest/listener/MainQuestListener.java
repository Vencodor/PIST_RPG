package main.java.pist.plugins.quest.main_quest.listener;

import main.java.pist.api.animation.text.TitleAnimation;
import main.java.pist.api.animation.text.TitleAnimations;
import main.java.pist.api.function.util.ArmorStandUtil;
import main.java.pist.api.function.util.Vector3D;
import main.java.pist.api.scheduler.RepeatingTask;
import main.java.pist.data.player.PlayerData;
import main.java.pist.plugins.quest.integrated.QuestIntegratedManager;
import main.java.pist.plugins.quest.integrated.object.PlayerQuestDTO;
import main.java.pist.plugins.quest.integrated.object.questDefault.QuestMessageDTO;
import main.java.pist.plugins.quest.integrated.object.quests.HaveItemQuest;
import main.java.pist.plugins.quest.main_quest.MainQuestPlugin;
import main.java.pist.plugins.quest.main_quest.inventory.MainQuestGUI;
import main.java.pist.plugins.quest.main_quest.object.MainQuestDTO;
import main.java.pist.vencoder.Main;
import main.java.pist.vencoder.PluginManager;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.Method;
import java.util.HashMap;

class Talk {
	EntityArmorStand stand = null;
	MainQuestDTO quest = null;
	public EntityArmorStand getStand() {
		return stand;
	}
	public void setStand(EntityArmorStand stand) {
		this.stand = stand;
	}
	public MainQuestDTO getQuest() {
		return quest;
	}
	public void setQuest(MainQuestDTO quest) {
		this.quest = quest;
	}
	public Talk(EntityArmorStand stand, MainQuestDTO quest) {
		super();
		this.stand = stand;
		this.quest = quest;
	}
}

public class MainQuestListener extends PluginManager implements Listener {
	
	private HashMap<Player,Talk> talk = new HashMap<Player,Talk>();
	
	@EventHandler(priority = EventPriority.LOW)
	public void onRightClickNpc(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		
		Entity rightClick = e.getRightClicked();
		PlayerQuestDTO playerQuest = PlayerData.getData(p.getUniqueId()).getQuest();
		MainQuestDTO quest = MainQuestPlugin.getQuestUseNpcTopPriorty(playerQuest, rightClick.getName());
		
		if(quest!=null) {
			if(isTalk(p)) {
				return;
			}
			if(MainQuestPlugin.isContains(playerQuest.getClearQuest(), quest)) { //이미 클리어 했다면
				TitleAnimation.run(p, red+"이미 클리어 한 퀘스트입니다", TitleAnimations.ERROR);
				return;
			}
			if(playerQuest.getProgressQuest()!=null&&playerQuest.getProgressQuest().isTalk()) {
				MainQuestDTO progressQuest = playerQuest.getProgressQuest();
				if(progressQuest.getKey().equals(quest.getKey())) {
					if(MainQuestPlugin.isQuestComplete(p, progressQuest)) { //만약 퀘스트를 클리어 한 채로 NPC에게 찾아갔다면
						QuestIntegratedManager.giveReward(p, quest.getReward());
						p.sendTitle("메인 퀘스트 클리어", "메인퀘스트를 클리어 하였습니다", 1, 20*3, 1);
						p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 2);
						
						for(Object a : quest.getQuest()) {
							if(a instanceof HaveItemQuest) {
								try {
									Method m = a.getClass().getMethod("removeItem", Player.class);
									m.invoke(a, p);
								} catch (Exception e1) {
									e1.printStackTrace();
								}
								
							}
						}
						
						if(quest.getNextQuest()!=null) {
							playerQuest.setProgressQuest(MainQuestPlugin.getQuest(quest.getNextQuest()).clone());
						} else {
							playerQuest.setProgressQuest(null);
						}
						playerQuest.getClearQuest().add(quest);
						return;
					} else if(progressQuest.isTalk()){ //이미 NPC와 말을 했었다면
						p.openInventory((new MainQuestGUI()).getInfoInv(p, progressQuest));
						return;
					}
				} else {
					return;
				}
			}
			if(!MainQuestPlugin.isClearBeforeQuest(p, quest)) { //이전 퀘스트 클리어 안함
				TitleAnimation.run(p, red+"이전 퀘스트를 먼저 클리어하세요", TitleAnimations.ERROR);
				return;
			}
			if(!QuestIntegratedManager.canProgress(p, quest.getNeed())) { //조건 미달
				TitleAnimation.run(p, red+quest.getNeed().getLevel()+"레벨 이상만 진행 가능합니다", TitleAnimations.ERROR);
				return;
			}
			
			sendTitle(p, dgray+"[ "+orange+quest.getNpc()+gray+"과의 대화 "+dgray+"]", gray+"press "+bold+"F "+gray+"to skip");
			Location spawnLoc = rightClick.getLocation().clone().subtract(0,rightClick.getHeight()-0.1,0);
			EntityArmorStand stand = ArmorStandUtil.sendText(p, spawnLoc, orange+quest.getNpc()+gray+"이(가) 당신에게 말을 겁니다..");
			EntityArmorStand icon = ArmorStandUtil.getArmorStand(spawnLoc.add(0, 2.5, 0.25));
			
			PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
			connection.sendPacket(new PacketPlayOutSpawnEntityLiving(icon));
			
			ItemStack headItem = new ItemStack(Material.BOOK_AND_QUILL);
			headItem.addUnsafeEnchantment(Enchantment.ARROW_INFINITE, 1);
			connection.sendPacket(ArmorStandUtil.getArmorStandHeadPacket(icon, headItem));
			
			talk.put(p,new Talk(stand, quest));
			
			int delay = 0;
			for(QuestMessageDTO message : quest.getMessage()) {
				new RepeatingTask(Main.getInstance(),delay+(20*3),1) {
					int count = 0;
					@Override
					public void run() {
						if(!isTalk(p)) {
							cancel();
						} else {
							if(message.getMessage().length()>=count) {							
								String who = message.getWho().replace("me", p.getName()).replace("나", p.getName());
								String content = " "+green+who+dgray+" : "+white+message.getMessage().substring(0, count)+" ";
								
								stand.setCustomName(content);
								connection.sendPacket(new PacketPlayOutEntityMetadata(stand.getId(), stand.getDataWatcher(), false));
								//rename packet
								p.playSound(p.getLocation(), Sound.BLOCK_WOODEN_TRAPDOOR_OPEN, 0.4F, 2);
								
								count++;
							} else {
								cancel();
							}
						}
					}
				};
				delay = delay + (message.getMessage().length()*4) * 1 + 20;
			}
			
			new BukkitRunnable() {
				@Override
				public void run() {
					if(isTalk(p)&&talk.get(p).getStand().isAlive()) {
						endTalk(p, quest, stand);
					}
				}
			}.runTaskLater(Main.getInstance(), delay+40+(quest.getMessage().size()*20));
			
			new RepeatingTask(Main.getInstance(),1,1) {
				Location npcLoc = rightClick.getLocation();
				@Override
				public void run() {
					if(!isTalk(p)) {
						connection.sendPacket(new PacketPlayOutEntityDestroy(stand.getId()));
						connection.sendPacket(new PacketPlayOutEntityDestroy(icon.getId()));
						cancel();
					} else {
						Vector look = p.getEyeLocation().getDirection().normalize();
						Location spawnLoc = null;
						if(Vector3D.isLookingLoc(p, rightClick.getLocation(),0.18)) {
							npcLoc.subtract(look.getX(),0,look.getZ());
							spawnLoc = new Location(npcLoc.getWorld(), npcLoc.getX(), npcLoc.getY()-stand.getHeadHeight()+0.12, npcLoc.getZ(), 0, 0);
						} else {
							Vector lookMultiply = look.multiply(4.3);
							Location loc = p.getEyeLocation().clone().add(lookMultiply.getX(),0,lookMultiply.getZ());
							spawnLoc = new Location(npcLoc.getWorld(), loc.getX(), npcLoc.getY()-stand.getHeadHeight()+0.12, loc.getZ(), 0, 0);
						}
						stand.setPosition(spawnLoc.getX(), spawnLoc.getY(), spawnLoc.getZ());
						
						connection.sendPacket(new PacketPlayOutEntityTeleport(stand));
					}
				}
			};
			
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPressF(PlayerSwapHandItemsEvent e) {
		Player p = e.getPlayer();
		if(isTalk(p)) {
			Talk t = talk.get(p);
			endTalk(p, t.getQuest(), t.getStand());
		}
	}
	
	private void endTalk(Player p, MainQuestDTO quest, EntityArmorStand stand) {
		MainQuestDTO clone = quest.clone();
		try {
			stand.killEntity();
			MainQuestPlugin.setQuest(p,clone);
			talk.remove(p);
			
//			Entity npc = MainQuestPlugin.getQuestNpc(quest);
//			if(npc!=null) {
//				PacketUtil.setGlowing(p, npc);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		p.openInventory((new MainQuestGUI()).getInfoInv(p, clone));
	}
	
	private boolean isTalk(Player p) {
		return talk.get(p)!=null;
	}
	
}
