package main.java.pist.plugins.customItem.executor;

import main.java.pist.manager.plugin.CommandManager;
import main.java.pist.plugins.customItem.CustomItemPlugin;
import main.java.pist.plugins.customItem.inventory.GUI_CustomItem;
import main.java.pist.plugins.customItem.object.CustomItemDTO;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class CustomItemCommand extends CommandManager {

	public CustomItemCommand() {
		super(Arrays.asList("커스텀","아이템","커스텀아이템","custom","item","customitem","ci"));
	}

	public void onCommand(Player p, String label, String content, String[] args) {
		if(args.length==0) {
			p.sendMessage(white+" ");
			p.sendMessage(prefix+"/custom list "+gray+"");
			p.sendMessage(prefix+"/custom open "+gray+"<Key>");
			p.sendMessage(prefix+"/custom create "+gray+"<Key>");
			p.sendMessage(prefix+"/custom remove "+gray+"<Key>");
			p.sendMessage(white+" ");
		} else {
			if(args[0].equalsIgnoreCase("list")) {
				if(CustomItemPlugin.customItems.size()>0) {
					p.sendMessage(prefix);
					for(CustomItemDTO a : CustomItemPlugin.customItems)
						p.sendMessage(orange+" "+a.getKey()+gray+"("+bold+a.getType().toString()+gray+")"
					+" - "+yellow+a.getContents().size()+gray+" Items");
					p.sendMessage(prefix);
				} else {
					p.sendMessage(cmdWrong+"추가된 커스텀아이템이 없습니다");
				}
				
			} else if(args.length==1) {
				p.sendMessage(cmdWrong+red+"Key를 입력하세요");
			} else {
				CustomItemDTO custom = CustomItemPlugin.getCustom(args[1]);
				if(custom==null&&!args[0].equalsIgnoreCase("create")&&!args[0].equalsIgnoreCase("list")) {
					p.sendMessage(cmdWrong+orange+args[1]+red+" Key를 가진 CustomContents가 없습니다.");
				} else {
					if(args[0].equalsIgnoreCase("open")) {
						p.openInventory((new GUI_CustomItem()).getCustomGUI(custom, 1));
					} else if(args[0].equalsIgnoreCase("create")) {
						CustomItemPlugin.addCustom(args[1]);
						p.sendMessage(complete+orange+args[1]+white+" Key 커스텀 아이템을 생성하였습니다");
					} else if(args[0].equalsIgnoreCase("remove")) {
						CustomItemPlugin.removeCustom(args[1]);
						p.sendMessage(complete+orange+args[1]+white+" Key 커스텀 아이템을 제거하였습니다");
					} else {
						
					}
					
				}
			}
			
		}
	}
	
}
