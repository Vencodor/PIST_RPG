package main.java.pist.plugins.system.biome.executor;

import main.java.pist.manager.plugin.CommandManager;
import main.java.pist.plugins.system.biome.BiomePlugin;
import main.java.pist.plugins.system.biome.object.BiomeDTO;
import main.java.pist.plugins.system.biome.object.BiomeEffect;
import main.java.pist.plugins.system.biome.object.BiomeType;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

public class BiomeManageCommand extends CommandManager {
	
	public BiomeManageCommand() {
		super(Arrays.asList("biome","바이옴"));
	}

	private static HashMap<String,String> editKey = new HashMap<String,String>();
	
	@Override
	public void onCommand(Player p, String label, String content, String[] args) {
		if(args.length==0) {
			p.sendMessage(white+"");
			p.sendMessage(gray+"other command : /biome, 바이옴");
			p.sendMessage(prefix+"/biome <create/remove> <key> "+gray+"바이옴을 생성 또는 삭제합니다");
			p.sendMessage(prefix+"/biome list "+gray+"바이옴 목록을 확인합니다");
			p.sendMessage(prefix+"/biome info <key> "+gray+"바이옴 정보를 확인합니다");
			p.sendMessage(white+"");
			p.sendMessage(prefix+"/biome sel <key> "+gray+"앞으로 수정할 바이옴을 지목합니다");
			p.sendMessage(prefix+"/biome name <name> "+gray+"바이옴의 이름을 설정합니다");
			p.sendMessage(prefix+"/biome description <description> "+gray+"바이옴의 설명을 설정합니다");
			p.sendMessage(prefix+"/biome type <type> "+gray+"바이옴의 타입을 설정합니다");
			p.sendMessage(prefix+"/biome effect <effect> "+gray+"바이옴의 효과를 설정합니다");
			p.sendMessage(white+"");
		} else {
			if(args[0].equalsIgnoreCase("create")) {
				if(args.length<3) {
					p.sendMessage(cmdWrong+"/biome create <key> <biome>");
				} else {
					Biome biome = null;
					try {
						biome = Biome.valueOf(args[2]);
					} catch(Exception e1) {
						p.sendMessage(cmdWrong+"알맞은 바이옴 이름을 입력하세요");
						return;
					}
					if(BiomePlugin.addBiome(args[1],biome)) {
						p.sendMessage(complete+args[1]+" Key를 가진 바이옴을 추가하였습니다");
					} else {
						p.sendMessage(cmdWrong+args[1]+" 같은 Key를가진 바이옴이 존재합니다");
					}
				}
			} else if(args[0].equalsIgnoreCase("remove")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/biome remove <key>");
				} else {
					if(BiomePlugin.removeBiome(args[1])) {
						p.sendMessage(complete+args[1]+" Key를 가진 바이옴을 삭제하였습니다");
					} else {
						p.sendMessage(cmdWrong+args[1]+" Key를 가진 바이옴이 존재하지 않습니다");
					}
				}
			} else if(args[0].equalsIgnoreCase("list")) {
				if(BiomePlugin.biomes.size()>0) {
					p.sendMessage(prefix);
					for(BiomeDTO a : BiomePlugin.biomes.values()) {
						p.sendMessage(gray+bold+a.getKey()+white+" - "+a.getDisplay());
					}
					p.sendMessage(prefix);
				} else {
					p.sendMessage(cmdWrong+"추가된 바이옴이 없습니다");
				}
			} else if(args[0].equalsIgnoreCase("info")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/biome info <key>");
				} else {
					BiomeDTO biome = BiomePlugin.getBiome(args[1]);
					if(biome!=null) {
						p.sendMessage(prefix);
						try {
							for(Field a : biome.getClass().getDeclaredFields()) {
								p.sendMessage(a.getName()+" : "+a.get(biome));
							}
						} catch(Exception e1) {
							p.sendMessage(cmdWrong+"오류 발생");
							p.sendMessage(red+e1.getCause());
						}
						p.sendMessage(prefix);
					} else {
						p.sendMessage(cmdWrong+args[1]+" Key를 가진 바이옴이 존재하지 않습니다");
					}
				}
			} else if(args[0].equalsIgnoreCase("sel")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/biome sel <key>");
				} else {
					BiomeDTO target = BiomePlugin.getBiome(args[1]);
					if(target==null) {
						p.sendMessage(cmdWrong+"해당 키를 가진 biome가 없습니다");
					} else {
						editKey.put(p.getName(), target.getKey());
						p.sendMessage(complete+"설정되었습니다");
					}
				}
			} else {
				BiomeDTO biome = getEdit(p.getName());
				if(biome==null) {
					p.sendMessage(cmdWrong+"/biome sel <key> 를 통해 먼저 편집대상을 설정하세요");
					return;
				}
				
				if(args[0].equalsIgnoreCase("name")) {
					if(args.length<2) {
						p.sendMessage(cmdWrong+"/biome name <name>");
					} else {
						biome.setDisplay(args[1]);
						p.sendMessage(complete+"설정되었습니다");
					}
				} else if(args[0].equalsIgnoreCase("description")) {
					if(args.length<2) {
						p.sendMessage(cmdWrong+"/biome description <description>");
					} else {
						biome.setDescription(content.replace("biome description ", ""));
						p.sendMessage(complete+"설정되었습니다");
					}
				} else if(args[0].equalsIgnoreCase("type")) {
					if(args.length<2) {
						p.sendMessage(cmdWrong+"/fish type <마을/성/던전/무법지>");
					} else {
						BiomeType type = null;
						try {
							type = BiomeType.valueOf(args[1]);
						} catch(Exception e1) {
							p.sendMessage(cmdWrong+"알맞은 타입을 입력하세요 "+gray+"마을,성,던전,무법지");
							return;
						}
						if(type==null)
							return;
						
						biome.setType(type);
						p.sendMessage(complete+"설정되었습니다");
					}
				} else if(args[0].equalsIgnoreCase("effect")) {
					if(args.length<2) {
						p.sendMessage(cmdWrong+"/fish effect <탈수/중독/혼란>");
					} else {
						BiomeEffect effect = null;
						try {
							effect = BiomeEffect.valueOf(args[1]);
						} catch(Exception e1) {
							p.sendMessage(cmdWrong+"알맞은 효과를 입력하세요");
							return;
						}
						if(effect==null)
							return;
						
						biome.setEffect(effect);
						p.sendMessage(complete+"설정되었습니다");
					}
				}
				
			}
		}
	}
	
	private static BiomeDTO getEdit(String name) {
		if(editKey.get(name)==null)
			return null;
		return BiomePlugin.getBiome(editKey.get(name));
	}
	
}
