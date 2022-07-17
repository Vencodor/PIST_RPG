package main.java.pist.plugins.system.link.executor;

import main.java.pist.api.function.Key;
import main.java.pist.api.function.util.FormatUtil;
import main.java.pist.data.player.PlayerData;
import main.java.pist.data.player.object.PlayerInfoDTO;
import main.java.pist.manager.game.info.PlayerExp;
import main.java.pist.manager.plugin.CommandManager;
import main.java.pist.plugins.system.link.connect_client.ConnectClient;
import net.md_5.bungee.api.chat.*;
import org.bukkit.entity.Player;

import javax.xml.soap.Text;
import java.util.Arrays;

public class LinkCommand extends CommandManager{

	public LinkCommand() {
		super(Arrays.asList("link","l","연동","디스코드연동","dl"));
	}

	@Override
	public void onCommand(Player p, String label, String content, String[] args) {
		if(args.length==0) {
			TextComponent text = new TextComponent(prefix+"디스코드와 연동하시겠습니까?");

			TextComponent linkText = new TextComponent(green+bold+" Link!");
			linkText.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT
					,  new ComponentBuilder(gray+"Link "+blue+"Discord"+gray+" and "+green+"Minecraft").create()));
			linkText.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/link linkAgain"));

			text.addExtra(linkText);

			p.spigot().sendMessage(text);
			p.sendMessage(gray+" 연동하면 더 많은 서비스를 이용할 수 있습니다");
		} else {
			if(args[0].equals("linkAgain")) {
				String code = Key.getSimpleRandomKey(4);

				p.sendMessage(" ");
				p.sendMessage(prefix+"디스코드에서 ';link "+orange+code+white+"' 명령어를 입력하세요");
				p.sendMessage(red+"코드의 유효기간은 3분입니다");
				p.sendMessage(" ");

				ConnectClient.sendMessage("LINK@"+p.getUniqueId()+"@"+code);
			}

		}
	}
	
}
