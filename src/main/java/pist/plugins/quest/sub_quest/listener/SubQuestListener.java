package main.java.pist.plugins.quest.sub_quest.listener;

import main.java.pist.api.animation.text.TitleAnimation;
import main.java.pist.api.animation.text.TitleAnimations;
import main.java.pist.api.function.util.ArmorStandUtil;
import main.java.pist.api.function.util.Vector3D;
import main.java.pist.api.scheduler.RepeatingTask;
import main.java.pist.data.player.PlayerData;
import main.java.pist.plugins.quest.integrated.QuestIntegratedManager;
import main.java.pist.plugins.quest.integrated.object.PlayerQuestDTO;
import main.java.pist.plugins.quest.integrated.object.questDefault.QuestMessageDTO;
import main.java.pist.plugins.quest.main_quest.MainQuestPlugin;
import main.java.pist.plugins.quest.sub_quest.SubQuestPlugin;
import main.java.pist.plugins.quest.sub_quest.inventory.SubQuestGUI;
import main.java.pist.plugins.quest.sub_quest.object.SubQuestDTO;
import main.java.pist.vencoder.Main;
import main.java.pist.vencoder.PluginManager;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;

class Talk {
	EntityArmorStand stand = null;
	SubQuestDTO quest = null;
	public EntityArmorStand getStand() {
		return stand;
	}
	public void setStand(EntityArmorStand stand) {
		this.stand = stand;
	}
	public SubQuestDTO getQuest() {
		return quest;
	}
	public void setQuest(SubQuestDTO quest) {
		this.quest = quest;
	}
	public Talk(EntityArmorStand stand, SubQuestDTO quest) {
		super();
		this.stand = stand;
		this.quest = quest;
	}
}

public class SubQuestListener extends PluginManager implements Listener {
	
	private HashMap<Player,Talk> talk = new HashMap<Player,Talk>();
	
	@EventHandler
	public void onRightClickNpc(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		
		Entity rightClick = e.getRightClicked();
		SubQuestDTO quest = SubQuestPlugin.getQuestUseNpcName(rightClick.getName());
		
		if(quest!=null) {
			PlayerQuestDTO playerQuest = PlayerData.getData(p.getUniqueId()).getQuest();
			if(isTalk(p)) {
				return;
			}
			if(MainQuestPlugin.getQuestUseNpcName(playerQuest, rightClick.getName()).size()>0) { //해당 NPC에 진행하지 않은 메인퀘가 있다면
				return;
			}
			if(SubQuestPlugin.isContains(playerQuest.getClearSubQuest(), quest)) {
				TitleAnimation.run(p, red+"이미 클리어 한 퀘스트입니다", TitleAnimations.ERROR);
				return;
			}
			if(SubQuestPlugin.isContains(playerQuest.getProgressSubQuest(), quest)) {
				TitleAnimation.run(p, red+"진행중인 퀘스트입니다", TitleAnimations.ERROR);
				return;
			}
			if(playerQuest.getProgressSubQuest().size()>=3) {
				TitleAnimation.run(p, red+"한번에 3개 이상의 퀘스트를 받을 수 없습니다", TitleAnimations.ERROR);
				return;
			}
			if(!QuestIntegratedManager.canProgress(p, quest.getNeed())) { //조건 미달
				TitleAnimation.run(p, red+quest.getNeed().getLevel()+"레벨 이상만 진행 가능합니다", TitleAnimations.ERROR);
				return;
			}
			
			sendTitle(p, dgray+"[ "+orange+quest.getNpc()+gray+"과의 대화 "+dgray+"]", gray+"press "+bold+"F "+gray+"to skip");
			Location spawnLoc = rightClick.getLocation().clone().subtract(0,rightClick.getHeight()-0.1,0);
			EntityArmorStand stand = ArmorStandUtil.sendText(p, spawnLoc, orange+quest.getNpc()+gray+"이(가) 당신에게 말을 겁니다..");
			
			talk.put(p,new Talk(stand, quest));
			
			int delay = 0;
			PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
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
						stand.killEntity();
						p.openInventory((new SubQuestGUI()).getAgreeInv(p, quest));
						talk.remove(p);
					}
				}
			}.runTaskLater(Main.getInstance(), delay+40+(quest.getMessage().size()*20));
			
			new RepeatingTask(Main.getInstance(),1,1) {
				@Override
				public void run() {
					if(!isTalk(p)) {
						connection.sendPacket(new PacketPlayOutEntityDestroy(stand.getId()));
						cancel();
					} else {
						Location npcLoc = rightClick.getLocation();
						Vector look = p.getEyeLocation().getDirection().normalize();
						if(Vector3D.isLookingLoc(p, rightClick.getLocation(),0.18)) {
							npcLoc.subtract(look.getX(),0,look.getZ());
							stand.setLocation(npcLoc.getX(), npcLoc.getY()-stand.getHeadHeight()+0.12, npcLoc.getZ(), 0, 0);
						} else {
							Vector lookMultiply = look.multiply(4.3);
							Location loc = p.getEyeLocation().clone().add(lookMultiply.getX(),0,lookMultiply.getZ());
							stand.setLocation(loc.getX(), npcLoc.getY()-stand.getHeadHeight()+0.12, loc.getZ(), 0, 0);
						}
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
			p.openInventory((new SubQuestGUI()).getAgreeInv(p, t.getQuest()));
			t.getStand().killEntity();
			talk.remove(p);
		}
	}
	
	private boolean isTalk(Player p) {
		return talk.get(p)!=null;
	}
	
}
