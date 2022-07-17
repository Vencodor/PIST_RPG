package main.java.pist.plugins.quest.main_quest.executor;

import main.java.pist.manager.plugin.CommandManager;
import main.java.pist.plugins.customItem.CustomItemPlugin;
import main.java.pist.plugins.customItem.object.CustomItemDTO;
import main.java.pist.plugins.customItem.object.enums.CustomType;
import main.java.pist.plugins.quest.integrated.object.questDefault.QuestMessageDTO;
import main.java.pist.plugins.quest.integrated.object.quests.HaveItemQuest;
import main.java.pist.plugins.quest.integrated.object.quests.MobKillQuest;
import main.java.pist.plugins.quest.main_quest.MainQuestPlugin;
import main.java.pist.plugins.quest.main_quest.object.MainQuestDTO;
import main.java.pist.plugins.quest.sub_quest.SubQuestPlugin;
import main.java.pist.plugins.quest.sub_quest.object.SubQuestDTO;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.Arrays;

public class MainQuestManageCommand extends CommandManager {

	public MainQuestManageCommand() {
		super(Arrays.asList("메인퀘스트관리","qm"));
	}

	@Override
	public void onCommand(Player p, String label, String content, String[] args) {
		if(!p.isOp()) {
			return;
		}
		
		if(args.length==0) {
			p.sendMessage(white+"");
			p.sendMessage(gray+"other command : /메인퀘스트관리");
			p.sendMessage(prefix+"/qm <create/remove> <Key> "+gray+"퀘스트를 생성 또는 삭제합니다");
			p.sendMessage(prefix+"/qm move <Player> <Key> "+gray+"플레이어의 메인퀘스트 진행도를 바꿉니다");
			p.sendMessage(prefix+"/qm list "+gray+"퀘스트 목록을 확인합니다");
			p.sendMessage(white+"");
			p.sendMessage(prefix+"/qm sel <Key> "+gray+"앞으로 수정할 퀘스트를 지목합니다");
			p.sendMessage(prefix+"/qm priority <priority> "+gray+"퀘스트의 우선순위를 설정합니다");
			p.sendMessage(prefix+"/qm title <title> "+gray+"퀘스트의 이름을 설정합니다");
			p.sendMessage(prefix+"/qm npc <npc Name> "+gray+"퀘스트와 연동된 npc를 설정합니다");
			p.sendMessage(prefix+"/qm need [quest/level] <key/amount> "+gray+"퀘스트의 진행 조건을 설정합니다");
			p.sendMessage(prefix+"/qm msg [add/remove/list] "+gray+"퀘스트의 대사를 설정합니다");
			p.sendMessage(prefix+"/qm quest [add/remove/list] "+gray+"퀘스트의 클리어 조건을 설정합니다");
			p.sendMessage(prefix+"/qm reward [item/exp/money] <container Name/amount/amount> "+gray+"퀘스트의 보상을 설정합니다");
			p.sendMessage(prefix+"/qm next <Key> "+gray+"퀘스트 다음으로 이어질 퀘스트를 설정합니다");
			p.sendMessage(white+"");
		} else {
			if(args[0].equalsIgnoreCase("create")) {
				if(args.length==1) {
					p.sendMessage(cmdWrong+"/qm create <Key>");
				} else {
					MainQuestPlugin.putNewQuest(args[1]);
					p.sendMessage(complete+orange+args[1]+white+"키를 가진 퀘스트가 생성되었습니다");
				}
			} else if(args[0].equalsIgnoreCase("remove")) {
				if(args.length==1) {
					p.sendMessage(cmdWrong+"/qm remove <Key>");
				} else {
					if(MainQuestPlugin.removeQuest(args[1])) {
						p.sendMessage(complete+orange+args[1]+white+"키를 가진 퀘스트가 제거되었습니다");
					} else {
						p.sendMessage(cmdWrong+orange+args[1]+red+"키를 가진 퀘스트가 없습니다");
					}
				}
			} else if(args[0].equalsIgnoreCase("move")) {
				if(args.length<=2) {
					p.sendMessage(cmdWrong+"/qm move <Player> <Key>");
				} else {
					//MainQuestDTO quest = MainQuestPlugin.getQuest(args[2]);
					
				}
			} else if(args[0].equalsIgnoreCase("list")) {
				if(MainQuestPlugin.quest.size()>0) {
					p.sendMessage(prefix+"");
					for(MainQuestDTO a : MainQuestPlugin.quest.values()) {
						p.sendMessage(orange+" "+a.getKey()+gray+"("+bold+a.getTitle()+gray+") - "+a.getNpc());
					}
					p.sendMessage(prefix+"");
				} else {
					p.sendMessage(cmdWrong+"추가된 퀘스트가 없습니다");
				}
			} else if(args[0].equalsIgnoreCase("sel")) {
				if(args.length==1) {
					if(MainQuestPlugin.editKey.get(p.getName())==null)
						p.sendMessage(cmdWrong+"/qm sel <Key>");
					else
						p.sendMessage(prefix+"현재 설정된 대상은 "+orange+MainQuestPlugin.editKey.get(p.getName())+white+"입니다");
				} else {
					if(MainQuestPlugin.getQuest(args[1])!=null) {
						MainQuestPlugin.editKey.put(p.getName(), args[1]);
						p.sendMessage(prefix+"퀘스트 설정 대상이 "+orange+args[1]+white+"로 변경되었습니다");
					} else {
						p.sendMessage(cmdWrong+orange+args[1]+red+"키를 가진 퀘스트가 존재하지 않습니다");
					}
				}
			} else {
				String key = MainQuestPlugin.editKey.get(p.getName());
				MainQuestDTO quest = null;
				if(key==null) {
					p.sendMessage(cmdWrong+"먼저 퀘스트 설정 대상을 지정하세요 "+gray+"/qm sel <Key>");
					return;
				}
				quest = MainQuestPlugin.getQuest(key);
				if(quest==null) {
					MainQuestPlugin.editKey.remove(p.getName());
					p.sendMessage(cmdWrong+"기존에 선택된 퀘스트를 찾을 수 없습니다");
					return;
				}
				if(args[0].equalsIgnoreCase("priority")) {
					if(args.length==1) {
						p.sendMessage(cmdWrong+"/qm priority <priority>");
					} else {
						int priority = 0;
						try {
							priority = Integer.parseInt(args[1]);
						} catch(NumberFormatException e1) {
							p.sendMessage(cmdWrong+"숫자만 입력 가능합니다");
							return;
						}
						quest.setPriorty(priority);
						p.sendMessage(complete+orange+key+white+"퀘스트의 우선순위가 "+orange+args[1]+white+"으로 설정되었습니다");
					}
				} else if(args[0].equalsIgnoreCase("title")) {
					if(args.length==1) {
						p.sendMessage(cmdWrong+"/qm title <Title>");
					} else {
						String title = content.split("title ",2)[1];
						quest.setTitle(title);
						p.sendMessage(complete+orange+key+white+"퀘스트의 타이틀이 "+orange+title+white+"으로 설정되었습니다");
					}
				} else if(args[0].equalsIgnoreCase("npc")) {
					if(args.length==1) {
						p.sendMessage(cmdWrong+"/qm npc <npc Name>");
					} else {
						quest.setNpc(args[1]);
						p.sendMessage(complete+orange+key+white+"퀘스트와 연동된 NPC가 "+orange+args[1]+white+"로 설정되었습니다");
					}
				} else if(args[0].equalsIgnoreCase("need")) {
					if(args.length==1) {
						p.sendMessage(cmdWrong+"/qm need [quest/level] <key/amount>");
					} else {
						if(args[1].equalsIgnoreCase("quest")) {
							if(args.length==2) {
								p.sendMessage(cmdWrong+"/qm need quest <key>");
							} else {
								SubQuestDTO need = SubQuestPlugin.getQuest(args[2]);
								if(need == null) {
									p.sendMessage(cmdWrong+orange+args[2]+red+"키를 가진 서브퀘스트가 존재하지 않습니다");
								} else {
									quest.getNeed().setQuestKey(args[2]);
									p.sendMessage(complete+"메인퀘스트진행에 필요한 서브퀘스트를 "+orange+args[2]+white+"로 설정하였습니다");
								}
							}
						} else if(args[1].equalsIgnoreCase("level")) {
							if(args.length==2) {
								p.sendMessage(cmdWrong+"/qm need level <amount>");
							} else {
								int amount = 0;
								try {
									amount = Integer.parseInt(args[2]);
								} catch(NumberFormatException e1) {
									p.sendMessage(cmdWrong+"숫자만 입력 가능합니다");
									return;
								}
								quest.getNeed().setLevel(amount);
								p.sendMessage(complete+"퀘스트진행에 필요한 레벨을 "+orange+amount+white+"레벨로 설정하였습니다");
							}
						}
					}
				} else if(args[0].equalsIgnoreCase("msg")) {
					if(args.length==1) {
						p.sendMessage(cmdWrong+"/qm msg [add/remove/list]");
					} else {
						if(args[1].equalsIgnoreCase("add")) {
							if(args.length<=3) {
								p.sendMessage(cmdWrong+"/qm msg add <who> <message>");
							} else {
								String message = content.split(args[2]+" ",2)[1];
								quest.getMessage().add(new QuestMessageDTO(args[2],message));
								p.sendMessage(complete+"'"+args[2]+" : "+message+"' 대사가 추가되었습니다");
							}
						} else if(args[1].equalsIgnoreCase("remove")) {
							if(args.length==2) {
								p.sendMessage(cmdWrong+"/qm msg remove <line>");
							} else {
								int line = 0;
								try {
									line = Integer.parseInt(args[2]);
								} catch(NumberFormatException e1) {
									p.sendMessage(cmdWrong+"숫자만 입력 가능합니다");
									return;
								}
								if(line >= quest.getMessage().size()) {
									p.sendMessage(cmdWrong+"해당 줄에 있는 대사가 존재하지 않습니다");
								} else {
									quest.getMessage().remove(line);
									p.sendMessage(complete+orange+line+white+"번 줄 대사가 삭제되었습니다");
								}
							}
						} else if(args[1].equalsIgnoreCase("list")) {
							if(quest.getMessage().size()>0) {
								p.sendMessage(prefix);
								for(QuestMessageDTO a : quest.getMessage()) {
									p.sendMessage(orange+quest.getMessage().indexOf(a)+gray+"번줄 - "+white+a.getWho()+" : "+a.getMessage());
								}
								p.sendMessage(prefix);
							} else {
								p.sendMessage(cmdWrong+"추가된 대사가 없습니다 "+gray+"/qm msg add");
							}
						}
					}
				} else if(args[0].equalsIgnoreCase("quest")) {
					if(args.length == 1) {
						p.sendMessage(cmdWrong+"/qm quest [add/remove/list]");
					} else {
						if(args[1].equalsIgnoreCase("add")) { //qm quest add <QuestClass> <Constursor>
							if(args.length==2) {
								p.sendMessage(cmdWrong+"/qm quest add [mob/item/location]");
							} else {
								if(args[2].equalsIgnoreCase("mob")) {
									if(args.length < 5) {
										p.sendMessage(cmdWrong+"/qm quest add mob <mob Name> <amount>");
									} else {
										int amount = 0;
										try {
											amount = Integer.parseInt(args[4]);
										} catch(NumberFormatException e1) {
											p.sendMessage(cmdWrong+"숫자만 입력 가능합니다");
											return;
										}
										quest.getQuest().add(new MobKillQuest(args[3], amount));
										p.sendMessage(complete+args[3]+"몹 "+amount+"마리 잡기 퀘스트가 추가되었습니다");
									}
								} else if(args[2].equalsIgnoreCase("item")) {
									if(args.length < 4) {
										p.sendMessage(cmdWrong+"/qm quest add item <amount>");
									} else {
										ItemStack item = p.getInventory().getItemInMainHand();
										if(item == null || !item.hasItemMeta() || item.getItemMeta().getDisplayName() == null) {
											p.sendMessage(cmdWrong+"손에 든 아이템이 없거나 이름이 적혀있지 않습니다");
											return;
										}
										
										int amount = 0;
										try {
											amount = Integer.parseInt(args[3]);
										} catch(NumberFormatException e1) {
											p.sendMessage(cmdWrong+"숫자만 입력 가능합니다");
											return;
										}
										
										quest.getQuest().add(new HaveItemQuest(item.getType(), item.getItemMeta().getDisplayName(), amount));
										p.sendMessage(complete+"해당 아이템 "+amount+"개 가져오기 퀘스트가 추가되었습니다");
									}
								} else if(args[2].equalsIgnoreCase("location")) {
									
								}
							}
						} else if(args[1].equalsIgnoreCase("remove")) {
							if(args.length==2) {
								p.sendMessage(cmdWrong+"/qm quest remove <line>");
							} else {
								int line = 0;
								try {
									line = Integer.parseInt(args[2]);
								} catch(NumberFormatException e1) {
									p.sendMessage(cmdWrong+"숫자만 입력 가능합니다");
								}
								
								if(line >= quest.getQuest().size() || line < 0) {
									p.sendMessage(cmdWrong+"해당 줄에 있는 퀘스트가 없습니다 "+gray+"/qm quest list");
								} else {
									quest.getQuest().remove(line);
									p.sendMessage(complete+"성공적으로 "+line+"번 줄에있는 퀘스트를 제거하였습니다");
								}
							}
						} else if(args[1].equalsIgnoreCase("list")) {
							if(quest.getQuest().size()>0) {
								p.sendMessage(prefix);
								for(Object a : quest.getQuest()) {
									Field field = null;
									try {
										field = a.getClass().getField("description");
										p.sendMessage(orange+quest.getQuest().indexOf(a)+gray+"번줄 - "+white+(String)field.get(a));
									} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
										e.printStackTrace();
									}
								}
								p.sendMessage(prefix);
							} else {
								p.sendMessage(cmdWrong+"추가된 퀘스트가 없습니다 "+gray+"/qm quest add");
							}
						}
					}
				} else if(args[0].equalsIgnoreCase("reward")) {
					if(args.length==1) {
						p.sendMessage(cmdWrong+"/qm reward [item/exp/money] <container Name/amount/amount>");
					} else {
						if(args[1].equalsIgnoreCase("item")) {
							if(args.length==2) {
								p.sendMessage(cmdWrong+"/qm reward item <container Name>");
							} else {
								CustomItemDTO custom = CustomItemPlugin.getCustom(args[2]);
								if(custom == null) {
									p.sendMessage(cmdWrong+"해당 이름을 가진 Container가 없습니다 "+gray+"/ci list");
								} else {
									if(custom.getType() != CustomType.QUEST_REWARD) {
										p.sendMessage(cmdWrong+"해당 Container의 타입을 "+orange+"QUEST_REWARD"+red+"로 설정하세요");
									} else {
										quest.getReward().setRewardItemContainer(args[2]);
									}
								}
							}
						} else if(args[1].equalsIgnoreCase("exp")) {
							if(args.length==2) {
								p.sendMessage(cmdWrong+"/qm reward exp <amount>");
							} else {
								int amount = 0;
								try {
									amount = Integer.parseInt(args[2]);
								} catch(NumberFormatException e1) {
									p.sendMessage(cmdWrong+"숫자만 입력 가능합니다");
									return;
								}
								quest.getReward().setRewardExp(amount);
								p.sendMessage(complete+"퀘스트 클리어 시 지급하는 경험치를 "+orange+amount+white+"로 설정하였습니다");
							}
						} else if(args[1].equalsIgnoreCase("money")) {
							if(args.length==2) {
								p.sendMessage(cmdWrong+"/qm reward money <amount>");
							} else {
								int amount = 0;
								try {
									amount = Integer.parseInt(args[2]);
								} catch(NumberFormatException e1) {
									p.sendMessage(cmdWrong+"숫자만 입력 가능합니다");
									return;
								}
								quest.getReward().setRewardMoney(amount);
								p.sendMessage(complete+"퀘스트 클리어 시 지급하는 돈을 "+orange+amount+white+"원으로 설정하였습니다");
							}
						}
					}
				} 
				else if(args[0].equalsIgnoreCase("next")) {
					if(args.length==1) {
						p.sendMessage(cmdWrong+"/qm next <key>");
					} else {
						MainQuestDTO next = MainQuestPlugin.getQuest(args[1]);
						if(next == null) {
							p.sendMessage(cmdWrong+orange+args[1]+red+"키를 가진 퀘스트가 존재하지 않습니다");
						} else {
							quest.setNextQuest(args[1]);
							p.sendMessage(complete+"다음 연계 메인퀘스트를 "+orange+args[1]+white+"로 설정하였습니다");
						}
					}
				}
				
			}
		}
		
	}
	
}
