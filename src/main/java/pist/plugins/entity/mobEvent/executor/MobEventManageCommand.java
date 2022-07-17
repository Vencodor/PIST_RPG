package main.java.pist.plugins.entity.mobEvent.executor;


import main.java.pist.manager.plugin.CommandManager;
import main.java.pist.plugins.customItem.CustomItemPlugin;
import main.java.pist.plugins.customItem.object.CustomItemDTO;
import main.java.pist.plugins.customItem.object.enums.CustomType;
import main.java.pist.plugins.entity.mobEvent.MobEventPlugin;
import main.java.pist.plugins.entity.mobEvent.object.MobDropDTO;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;

public class MobEventManageCommand extends CommandManager{
	
	private static HashMap<String,String> editKey = new HashMap<String,String>();

	public MobEventManageCommand() {
		super(Arrays.asList("ms","dropset","dropsetting","몹설정"),true);
	}

	@Override
	public void onCommand(Player p, String label, String content, String[] args) {
		if(args.length==0) {
			p.sendMessage(white+"");
			p.sendMessage(gray+"other command : /ms,dropset");
			p.sendMessage(prefix+"/ms <create/remove> <display> "+gray+"몹 드랍 설정을 생성 또는 삭제합니다");
			p.sendMessage(prefix+"/ms list "+gray+"추가된 몹 드랍설정을 확인합니다");
			p.sendMessage(white+"");
			p.sendMessage(prefix+"/ms sel <display> "+gray+"앞으로 수정할 몹을 지목합니다");
			p.sendMessage(prefix+"/ms exp <exp> "+gray+"몹이 떨굴 경험치를 설정합니다");
			p.sendMessage(prefix+"/ms money <money> "+gray+"몹이 떨굴 돈을 설정합니다");
			p.sendMessage(prefix+"/ms item <customItemKey> "+gray+"몹이 떨굴 아이템을 설정합니다");
			p.sendMessage(white+"");
		} else {
			if(args[0].equalsIgnoreCase("create")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/ms create <display>");
				} else {
					if(MobEventPlugin.addDrop(args[1])) {
						p.sendMessage(complete+args[1]+" Key를 가진 몹을 추가하였습니다");
					} else {
						p.sendMessage(cmdWrong+args[1]+" 같은 Key를가진 몹이 존재합니다");
					}
				}
			} else if(args[0].equalsIgnoreCase("remove")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/ms remove <display>");
				} else {
					if(MobEventPlugin.removeDrop(args[1])) {
						p.sendMessage(complete+args[1]+" Key를 가진 몹을 삭제하였습니다");
					} else {
						p.sendMessage(cmdWrong+args[1]+" Key를 가진 몹이 존재하지 않습니다");
					}
				}
			} else if(args[0].equalsIgnoreCase("list")) {
				if(MobEventPlugin.drops.size()>0) {
					p.sendMessage(prefix);
					for(MobDropDTO a : MobEventPlugin.drops) {
						p.sendMessage(gray+bold+a.getEntityDisplay()+white+" - "+a.getEntityDisplay());
					}
					p.sendMessage(prefix);
				} else {
					p.sendMessage(cmdWrong+"추가된 몹이 없습니다");
				}
			} else if(args[0].equalsIgnoreCase("sel")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/ms sel <display>");
				} else {
					MobDropDTO target = MobEventPlugin.getMobDrop(args[1]);
					if(target==null) {
						p.sendMessage(cmdWrong+"해당 키를 가진 Mob가 없습니다");
					} else {
						editKey.put(p.getName(), target.getEntityDisplay());
						p.sendMessage(complete+"설정되었습니다");
					}
				}
			} else {
				MobDropDTO mob = getEdit(p.getName());
				if(mob==null) {
					p.sendMessage(cmdWrong+"/ms sel <display> 를 통해 먼저 편집대상을 설정하세요");
					return;
				}
				
				if(args[0].equalsIgnoreCase("exp")) {
					if(args.length<2) {
						p.sendMessage(cmdWrong+"/ms exp <exp>");
					} else {
						int exp = 0;
						try {
							exp = Integer.parseInt(args[1]);
						} catch(NumberFormatException e) {
							p.sendMessage(cmdWrong+"숫자만 입력 가능합니다");
						}
						mob.setDropExp(exp);
						p.sendMessage(complete+"설정되었습니다");
					}
				} else if(args[0].equalsIgnoreCase("money")) {
					if(args.length<2) {
						p.sendMessage(cmdWrong+"/ms money <money>");
					} else {
						int money = 0;
						try {
							money = Integer.parseInt(args[1]);
						} catch(NumberFormatException e) {
							p.sendMessage(cmdWrong+"숫자만 입력 가능합니다");
						}
						mob.setDropMoney(money);
						p.sendMessage(complete+"설정되었습니다");
					}
				} else if(args[0].equalsIgnoreCase("item")) {
					if(args.length<2) {
						p.sendMessage(cmdWrong+"/ms item <CustomItemKey>");
					} else {
						CustomItemDTO ci = CustomItemPlugin.getCustom(args[1]);
						if(ci==null) {
							p.sendMessage(cmdWrong+"해당 Key를 가진 CustomItem이 없습니다");
							return;
						} else if(ci.getType() != CustomType.RANDOM) {
							p.sendMessage(cmdWrong+"CustomItem Type를 RANDOM으로 설정하세요");
							return;
						}
						
						mob.setDropItem(args[1]);
						p.sendMessage(complete+"설정되었습니다");
					}
				}
				
			}
		}
	}
	
	private static MobDropDTO getEdit(String name) {
		if(editKey.get(name)==null)
			return null;
		return MobEventPlugin.getMobDrop(editKey.get(name));
	}
	
}
