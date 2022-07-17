package main.java.pist.plugins.fishing.executor;

import main.java.pist.api.object.ItemDTO;
import main.java.pist.manager.plugin.CommandManager;
import main.java.pist.plugins.fishing.FishingPlugin;
import main.java.pist.plugins.fishing.object.FishDTO;
import main.java.pist.plugins.fishing.object.FishPercentage;
import main.java.pist.plugins.system.biome.BiomePlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

public class FishManageCommand extends CommandManager {

	public FishManageCommand() {
		super(Arrays.asList("fish","물고기"));
	}
	
	private static HashMap<String,String> editKey = new HashMap<String,String>();
	
	@Override
	public void onCommand(Player p, String label, String content, String[] args) {
		if(args.length==0) {
			p.sendMessage(white+"");
			p.sendMessage(gray+"other command : /fish, 물고기");
			p.sendMessage(prefix+"/fish <create/remove> <key> "+gray+"물고기를 생성 또는 삭제합니다");
			p.sendMessage(prefix+"/fish list "+gray+"물고기 목록을 확인합니다");
			p.sendMessage(prefix+"/fish info <key> "+gray+"물고기 정보를 확인합니다");
			p.sendMessage(white+"");
			p.sendMessage(prefix+"/fish sel <key> "+gray+"앞으로 수정할 물고기를 지목합니다");
			p.sendMessage(prefix+"/fish biome <biomeKey> "+gray+"물고기가 등장하는 바이옴을 설정합니다");
			p.sendMessage(prefix+"/fish item "+gray+"물고기의 아이템을 설정합니다");
			p.sendMessage(prefix+"/fish percent <percentRank> "+gray+"물고기의 등장 확률을 설정합니다");
			
			p.sendMessage(white+"");
		} else {
			if(args[0].equalsIgnoreCase("create")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/fish create <key>");
				} else {
					if(FishingPlugin.addFish(args[1])) {
						p.sendMessage(complete+args[1]+" Key를 가진 물고기를 추가하였습니다");
					} else {
						p.sendMessage(cmdWrong+args[1]+" 같은 Key를가진 물고기가 존재합니다");
					}
				}
			} else if(args[0].equalsIgnoreCase("remove")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/fish remove <key>");
				} else {
					if(FishingPlugin.removeFish(args[1])) {
						p.sendMessage(complete+args[1]+" Key를 가진 물고기를 삭제하였습니다");
					} else {
						p.sendMessage(cmdWrong+args[1]+" Key를 가진 물고기가 존재하지 않습니다");
					}
				}
			} else if(args[0].equalsIgnoreCase("list")) {
				if(FishingPlugin.fish.size()>0) {
					p.sendMessage(prefix);
					for(FishDTO a : FishingPlugin.fish.values()) {
						p.sendMessage(gray+bold+a.getKey()+white+" - "+a.getPercent());
					}
					p.sendMessage(prefix);
				} else {
					p.sendMessage(cmdWrong+"추가된 물고기가 없습니다");
				}
			} else if(args[0].equalsIgnoreCase("info")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/fish info <key>");
				} else {
					FishDTO fish = FishingPlugin.getFish(args[1]);
					if(fish!=null) {
						p.sendMessage(prefix);
						try {
							for(Field a : fish.getClass().getDeclaredFields()) {
								p.sendMessage(a.getName()+" : "+a.get(fish));
							}
						} catch(Exception e1) {
							p.sendMessage(cmdWrong+"오류 발생");
							p.sendMessage(red+e1.getCause());
						}
						p.sendMessage(prefix);
					} else {
						p.sendMessage(cmdWrong+args[1]+" Key를 가진 물고기가 존재하지 않습니다");
					}
				}
			} else if(args[0].equalsIgnoreCase("sel")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/fish sel <key>");
				} else {
					FishDTO target = FishingPlugin.getFish(args[1]);
					if(target==null) {
						p.sendMessage(cmdWrong+"해당 키를 가진 fish가 없습니다");
					} else {
						editKey.put(p.getName(), target.getKey());
						p.sendMessage(complete+"설정되었습니다");
					}
				}
			} else {
				FishDTO fish = getEdit(p.getName());
				if(fish==null) {
					p.sendMessage(cmdWrong+"/fish sel <key> 를 통해 먼저 편집대상을 설정하세요");
					return;
				}
				
				if(args[0].equalsIgnoreCase("biome")) {
					if(args.length<2) {
						p.sendMessage(cmdWrong+"/fish biome <biomeKey>");
					} else {
						if(BiomePlugin.getBiome(args[1])==null) {
							p.sendMessage(cmdWrong+"알맞은 바이옴 키를 입력하세요");
							return;
						}
						fish.setSpawnBiome(args[1]);
						p.sendMessage(complete+"설정되었습니다");
					}
				} else if(args[0].equalsIgnoreCase("item")) {
					ItemStack item = p.getInventory().getItemInMainHand();
					if(item==null) {
						p.sendMessage(cmdWrong+"손에 아이템을 들고 명령어를 치세요");
					} else {
						fish.setItem(new ItemDTO(item));
						p.sendMessage(complete+"설정되었습니다");
					}	
				} else if(args[0].equalsIgnoreCase("percent")) {
					if(args.length<2) {
						p.sendMessage(cmdWrong+"/fish percent <percentRank>");
					} else {
						FishPercentage percent = null;
						try {
							percent = FishPercentage.valueOf(args[1]);
						} catch(Exception e1) {
							p.sendMessage(cmdWrong+"알맞은 확률을 입력하세요 "+gray+"LOWEST,LOW,MEDIUM,HIGH,HIGHEST");
							return;
						}
						if(percent==null)
							return;
						
						fish.setPercent(percent);
						p.sendMessage(complete+"설정되었습니다");
					}
				}
				
			}
			
		}
	}
	
	private static FishDTO getEdit(String name) {
		if(editKey.get(name)==null)
			return null;
		return FishingPlugin.getFish(editKey.get(name));
	}
	
}
