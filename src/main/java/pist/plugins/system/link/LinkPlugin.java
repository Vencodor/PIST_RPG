package main.java.pist.plugins.system.link;

import main.java.pist.vencoder.PluginManager;
import org.bukkit.entity.Player;

public class LinkPlugin extends PluginManager {

    public void linkSuccess(Player p) {
        p.sendMessage(" ");
        p.sendMessage(complete+"성공적으로 디스코드와 연동하였습니다!");
        p.sendMessage(" ");
    }

}
