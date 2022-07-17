package main.java.pist.plugins.entity.mob.executor;


import main.java.pist.manager.plugin.CommandManager;
import main.java.pist.plugins.entity.mob.MobPlugin;
import main.java.pist.plugins.entity.mob.object.MobDTO;
import main.java.pist.plugins.entity.mob.object.enums.MobPersonality;
import main.java.pist.plugins.entity.mob.object.enums.MobType;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

public class MobManageCommand extends CommandManager{
	
	private static HashMap<String,String> editKey = new HashMap<String,String>();

	public MobManageCommand() {
		super(Arrays.asList("mob","몹","몬스터"));
	}

	@Override
	public void onCommand(Player p, String label, String content, String[] args) {
		if(args.length==0) {
			p.sendMessage(white+"");
			p.sendMessage(gray+"other command : /몹,몬스터");
			p.sendMessage(prefix+"/mob <create/remove> <key> "+gray+"몹을 생성 또는 삭제합니다");
			p.sendMessage(prefix+"/mob list "+gray+"몹 목록을 확인합니다");
			p.sendMessage(prefix+"/mob info <key> "+gray+"몹 정보를 확인합니다");
			p.sendMessage(prefix+"/mob spawn <key> "+gray+"몹을 소환합니다");
			p.sendMessage(white+"");
			p.sendMessage(prefix+"/mob sel <key> "+gray+"앞으로 수정할 몹을 지목합니다");
			p.sendMessage(prefix+"/mob name <name> "+gray+"몹의 이름을 설정합니다");
			p.sendMessage(prefix+"/mob entity <entity Type> "+gray+"몹의 엔티티 타입을 설정합니다");
			p.sendMessage(prefix+"/mob stat [(custom stat)] <amount> "+gray+"몹의 기본 능력치를 설정합니다");
			p.sendMessage(prefix+"/mob type [일반/대형] "+gray+"몹의 타입을 설정합니다");
			p.sendMessage(prefix+"/mob 성격 [평화/중립/적대] "+gray+"몹의 성격을 설정합니다");
			p.sendMessage(prefix+"/mob property [속성] "+gray+"몹의 속성을 설정합니다");
			p.sendMessage(prefix+"/mob spawner <add/remove/list> "+gray+"몹이 생성되는 스포너를 설정합니다");
			p.sendMessage(prefix+"/mob biome <biome key> "+gray+"몹이 자연스폰될 바이옴을 설정합니다");
			p.sendMessage(prefix+"/mob skill <add/remove/list> <skill> "+gray+"몹이 쓸 스킬을 설정합니다");
			p.sendMessage(prefix+"/mob drop <add/remove/list> <percent> "+gray+"몹이 떨굴 아이템을 설정합니다");
			p.sendMessage(white+"");
		} else {
			if(args[0].equalsIgnoreCase("create")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/mob create <key>");
				} else {
					if(MobPlugin.addMob(args[1])) {
						p.sendMessage(complete+args[1]+" Key를 가진 몹을 추가하였습니다");
					} else {
						p.sendMessage(cmdWrong+args[1]+" 같은 Key를가진 몹이 존재합니다");
					}
				}
			} else if(args[0].equalsIgnoreCase("remove")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/mob remove <key>");
				} else {
					if(MobPlugin.removeMob(args[1])) {
						p.sendMessage(complete+args[1]+" Key를 가진 몹을 삭제하였습니다");
					} else {
						p.sendMessage(cmdWrong+args[1]+" Key를 가진 몹이 존재하지 않습니다");
					}
				}
			} else if(args[0].equalsIgnoreCase("list")) {
				if(MobPlugin.mobs.size()>0) {
					p.sendMessage(prefix);
					for(MobDTO a : MobPlugin.mobs.values()) {
						p.sendMessage(gray+bold+a.getKey()+white+" - "+a.getDisplay());
					}
					p.sendMessage(prefix);
				} else {
					p.sendMessage(cmdWrong+"추가된 몹이 없습니다");
				}
			} else if(args[0].equalsIgnoreCase("info")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/mob info <key>");
				} else {
					MobDTO mob = MobPlugin.getMob(args[1]);
					if(mob!=null) {
						p.sendMessage(prefix);
						try {
							for(Field a : mob.getClass().getDeclaredFields()) {
								p.sendMessage(a.getName()+" : "+a.get(mob));
							}
						} catch(Exception e1) {
							p.sendMessage(cmdWrong+"오류 발생");
							p.sendMessage(red+e1.getCause());
						}
						p.sendMessage(prefix);
					} else {
						p.sendMessage(cmdWrong+args[1]+" Key를 가진 몹이 존재하지 않습니다");
					}
				}
			}
			else if(args[0].equalsIgnoreCase("spawn")) {
				
			} else if(args[0].equalsIgnoreCase("sel")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/mob sel <key>");
				} else {
					MobDTO target = MobPlugin.getMob(args[1]);
					if(target==null) {
						p.sendMessage(cmdWrong+"해당 키를 가진 Mob가 없습니다");
					} else {
						editKey.put(p.getName(), target.getKey());
						p.sendMessage(complete+"설정되었습니다");
					}
				}
			} else {
				MobDTO mob = getEdit(p.getName());
				if(mob==null) {
					p.sendMessage(cmdWrong+"/mob sel <key> 를 통해 먼저 편집대상을 설정하세요");
					return;
				}
				
				if(args[0].equalsIgnoreCase("name")) {
					if(args.length<2) {
						p.sendMessage(cmdWrong+"/mob name <name>");
					} else {
						mob.setDisplay(args[1]);
						p.sendMessage(complete+"설정되었습니다");
					}
				} else if(args[0].equalsIgnoreCase("entity")) {
					
				} else if(args[0].equalsIgnoreCase("stat")) {
					if(args.length<3) {
						p.sendMessage(cmdWrong+"/mob stat [health/power/speed] <amount>");
						p.sendMessage(" ");
						p.sendMessage(orange+"현재 몹 능력치");
						p.sendMessage(gray+"health : "+green+mob.getHealth());
						p.sendMessage(gray+"power : "+green+mob.getPower());
						p.sendMessage(gray+"speed : "+green+mob.getSpeed());
					} else {
						double amount = 0;
						try {
							amount = Double.parseDouble(args[2]);
						} catch(NumberFormatException e1) {
							p.sendMessage(cmdWrong+"숫자만 입력 가능합니다");
						}
						if(args[1].equalsIgnoreCase("health")) {
							mob.setHealth(amount);
						} else if(args[1].equalsIgnoreCase("power")) {
							mob.setPower(amount);
						} else if(args[1].equalsIgnoreCase("speed")) {
							mob.setSpeed(amount);
						} else {
							p.sendMessage(cmdWrong+args[1]+"은(는) 기본 능력치가 아닙니다");
							return;
						}
						p.sendMessage(complete+"기본 능력치가 설정되었습니다");
					}
				} else if(args[0].equalsIgnoreCase("type")) {
					if(args.length<2) {
						p.sendMessage(cmdWrong+"/mob type [일반/대형]");
					} else {
						try {
							mob.setType(MobType.valueOf(args[1]));
							p.sendMessage(complete+"설정되었습니다");
						} catch(Exception e1) {
							p.sendMessage(cmdWrong+"일반,대형 중 하나를 선택하세요");
						}
					}
				} else if(args[0].equalsIgnoreCase("성격")) {
					if(args.length<2) {
						p.sendMessage(cmdWrong+"/mob 성격 [평화/중립/적대]");
					} else {
						try {
							mob.setPersonality(MobPersonality.valueOf(args[1]));
							p.sendMessage(complete+"설정되었습니다");
						} catch(Exception e1) {
							p.sendMessage(cmdWrong+"평화,중립,적대 중 하나를 선택하세요");
						}
					}
				} else if(args[0].equalsIgnoreCase("property")) {
					
				} else if(args[0].equalsIgnoreCase("spawner")) {
					
				} else if(args[0].equalsIgnoreCase("biome")) {
					
				} else if(args[0].equalsIgnoreCase("kill")) {
					
				} else if(args[0].equalsIgnoreCase("drop")) {
					
				}
				
			}
		}
	}
	
	private static MobDTO getEdit(String name) {
		if(editKey.get(name)==null)
			return null;
		return MobPlugin.getMob(editKey.get(name));
	}
	
}
