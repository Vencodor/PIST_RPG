package main.java.pist.plugins.cook.executor;

import main.java.pist.api.function.util.NbtUtil;
import main.java.pist.api.object.ItemDTO;
import main.java.pist.manager.plugin.CommandManager;
import main.java.pist.manager.plugin.GuiManager;
import main.java.pist.plugins.cook.CookPlugin;
import main.java.pist.plugins.cook.object.CookDTO;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;

public class CookManageCommand extends CommandManager {
	
	private static HashMap<String,String> editKey = new HashMap<String,String>();
	
	public CookManageCommand() {
		super(Arrays.asList("cook","요리","요리관리","ㅇㄹ"));
	}
	
	@Override
	public void onCommand(Player p, String label, String content, String[] args) {
		if(!p.isOp())
			return;
		if(args.length==0) {
			p.sendMessage(white+"");
			p.sendMessage(gray+"other command : /cook,요리");
			p.sendMessage(prefix+"/cook <create/remove> <key> "+gray+"요리를 생성 또는 삭제합니다");
			p.sendMessage(prefix+"/cook list "+gray+"요리 목록을 확인합니다");
			p.sendMessage(prefix+"/cook get <key> "+gray+"요리 결과물을 얻습니다");
			p.sendMessage(prefix+"/cook book <key> "+gray+"요리 추가 스크롤을 얻습니다");
			p.sendMessage(white+"");
			p.sendMessage(prefix+"/cook sel <key> "+gray+"앞으로 수정할 요리를 지목합니다");
			p.sendMessage(prefix+"/cook display <name> "+gray+"요리의 이름을 설정합니다");
			p.sendMessage(prefix+"/cook time <time> "+gray+"요리 시간을 설정합니다");
			p.sendMessage(prefix+"/cook icon "+gray+"요리의 아이콘을 설정합니다");
			p.sendMessage(prefix+"/cook tool "+gray+"요리에 필요한 도구를 설정합니다");
			p.sendMessage(prefix+"/cook result "+gray+"요리의 결과물을 설정합니다");
			p.sendMessage(prefix+"/cook material <add/remove/list> "+gray+"요리에 필요한 재료를 설정합니다");
			p.sendMessage(white+"");
		} else {
			if(args[0].equalsIgnoreCase("create")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/cook create <key>");
				} else {
					if(CookPlugin.addCook(args[1])) {
						p.sendMessage(complete+args[1]+" Key를 가진 요리을 추가하였습니다");
					} else {
						p.sendMessage(cmdWrong+args[1]+" 같은 Key를가진 요리이 존재합니다");
					}
				}
			} else if(args[0].equalsIgnoreCase("remove")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/cook remove <key>");
				} else {
					if(CookPlugin.removeCook(args[1])) {
						p.sendMessage(complete+args[1]+" Key를 가진 요리을 삭제하였습니다");
					} else {
						p.sendMessage(cmdWrong+args[1]+" Key를 가진 요리이 존재하지 않습니다");
					}
				}
			} else if(args[0].equalsIgnoreCase("list")) {
				if(CookPlugin.cook.size()>0) {
					p.sendMessage(prefix);
					for(CookDTO a : CookPlugin.cook.values()) {
						p.sendMessage(gray+bold+a.getKey()+white+" - "+a.getDisplay());
					}
					p.sendMessage(prefix);
				} else {
					p.sendMessage(cmdWrong+"추가된 요리가 없습니다");
				}
			} else if(args[0].equalsIgnoreCase("get")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/cook get <key>");
				} else {
					CookDTO cook = CookPlugin.getCook(args[1]);
					if(cook!=null) {
						p.getInventory().addItem(cook.getResult());
					} else {
						p.sendMessage(cmdWrong+args[1]+" Key를 가진 요리가 존재하지 않습니다");
					}
				}
			} else if(args[0].equalsIgnoreCase("book")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/cook book <key>");
				} else {
					CookDTO cook = CookPlugin.getCook(args[1]);
					if(cook!=null) {
						GuiManager gui = new GuiManager();
						p.getInventory().addItem(NbtUtil.setNBT(
								gui.createItem(new ItemStack(Material.PAPER),orange+cook.getDisplay()+gray+" 조합법"
										,Arrays.asList(" ",gray+"우클릭하여 조합법을 해금하세요"," "))
									, "recipe", args[1]));
					} else {
						p.sendMessage(cmdWrong+args[1]+" Key를 가진 요리가 존재하지 않습니다");
					}
				}
			}
			else if(args[0].equalsIgnoreCase("sel")) {
				if(args.length<2) {
					p.sendMessage(cmdWrong+"/cook sel <key>");
				} else {
					CookDTO target = CookPlugin.getCook(args[1]);
					if(target==null) {
						p.sendMessage(cmdWrong+"해당 키를 가진 요리가 없습니다");
					} else {
						editKey.put(p.getName(), target.getKey());
						p.sendMessage(complete+"설정되었습니다");
					}
				}
			} else {
				CookDTO cook = getEdit(p.getName());
				if(cook==null) {
					p.sendMessage(cmdWrong+"/cook sel <key> 를 통해 먼저 편집대상을 설정하세요");
					return;
				}
				
				if(args[0].equalsIgnoreCase("display")) {
					if(args.length<2) {
						p.sendMessage(cmdWrong+"/cook display <name>");
					} else {
						cook.setDisplay(args[1]);
						p.sendMessage(complete+"설정되었습니다");
					}
				} else if(args[0].equalsIgnoreCase("time")) {
					if(args.length<2) {
						p.sendMessage(cmdWrong+"/cook time <time>");
					} else {
						int amount = 0;
						try {
							amount = Integer.parseInt(args[1]);
						} catch(NumberFormatException e1) {
							p.sendMessage(cmdWrong+"숫자만 입력 가능합니다");
						}
						cook.setTime(amount);
						p.sendMessage(complete+"조리 시간이 설정되었습니다");
					}
				} else if(args[0].equalsIgnoreCase("tool")) {
					ItemStack item = p.getInventory().getItemInMainHand();
					if(item==null||item.getType()==Material.AIR) {
						p.sendMessage(cmdWrong+"아이템을 들고 명령어를 치세요");
					} else {
						cook.setTool(new ItemDTO(item));
						p.sendMessage(complete+"설정되었습니다");
					}
				} else if(args[0].equalsIgnoreCase("icon")) {
					ItemStack item = p.getInventory().getItemInMainHand();
					if(item==null||item.getType()==Material.AIR||item.getType()==Material.AIR) {
						p.sendMessage(cmdWrong+"아이템을 들고 명령어를 치세요");
					} else {
						cook.setIcon(new ItemDTO(item));
						p.sendMessage(complete+"설정되었습니다");
					}
				} else if(args[0].equalsIgnoreCase("result")) {
					ItemStack item = p.getInventory().getItemInMainHand();
					if(item==null||item.getType()==Material.AIR||item.getType()==Material.AIR) {
						p.sendMessage(cmdWrong+"아이템을 들고 명령어를 치세요");
					} else {
						cook.setResult(item);
						p.sendMessage(complete+"설정되었습니다");
					}
				} else if(args[0].equalsIgnoreCase("material")) {
					if(args.length==1) {
						p.sendMessage(cmdWrong+"/cook material [add/remove/list]");
					} else {
						if(args[1].equalsIgnoreCase("add")) {
							ItemStack item = p.getInventory().getItemInMainHand();
							if(item==null||item.getType()==Material.AIR) {
								p.sendMessage(cmdWrong+"아이템을 들고 명령어를 치세요");
							} else {
								cook.getMaterial().add(new ItemDTO(item));
								p.sendMessage(complete+"추가되었습니다");
							}
						} else if(args[1].equalsIgnoreCase("remove")) {
							if(args.length==2) {
								p.sendMessage(cmdWrong+"/cook material remove <index>");
							} else {
								int index = 0;
								try {
									index = Integer.parseInt(args[2]);
								} catch(NumberFormatException e1) {
									p.sendMessage(cmdWrong+"숫자만 입력 가능합니다");
									return;
								}
								cook.getMaterial().remove(index);
								p.sendMessage(complete+"제거되었습니다");
							}
						} else if(args[1].equalsIgnoreCase("list")) {
							if(cook.getMaterial().size()>0) {
								p.sendMessage(prefix);
								for(ItemDTO a : cook.getMaterial()) {
									p.sendMessage(orange+cook.getMaterial().indexOf(a)+gray+"번 - "+white+a.getDisplay());
								}
								p.sendMessage(prefix);
							} else {
								p.sendMessage(cmdWrong+"추가된 재료가 없습니다 "+gray+"/cook material add");
							}
						}
					}
					
				}
				
			}
		}
	}
	
	private static CookDTO getEdit(String name) {
		if(editKey.get(name)==null)
			return null;
		return CookPlugin.getCook(editKey.get(name));
	}
}
