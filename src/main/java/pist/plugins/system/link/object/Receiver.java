package main.java.pist.plugins.system.link.object;

import main.java.pist.data.player.PlayerData;
import main.java.pist.data.player.object.PlayerDTO;
import main.java.pist.plugins.system.link.LinkPlugin;
import main.java.pist.plugins.system.link.connect_client.ConnectClient;
import main.java.pist.vencoder.PluginManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.UUID;

public class Receiver extends PluginManager implements Runnable{

    public Socket socket = null;
    public BufferedReader in = null;

    public Receiver(Socket socket, BufferedReader in) {
        this.socket = socket;
        this.in = in;
    }

    public void run() {
        while(true)
        { //계속 link success 데이터를 받아옴
            try {
                String data = in.readLine();
                System.out.println("Server > " + data);

                String args[] = data.split("@");

                if(args.length>1) {
                    if(args[0].equals("LINK")) {
                        if(args[1].equals("ERROR")) {
                            UUID uuid = UUID.fromString(args[3]);
                            for (Player a : Bukkit.getOnlinePlayers()) {
                                if (a.getUniqueId().equals(uuid)) {
                                    a.sendMessage(cmdWrong+args[2]);
                                    return;
                                }
                            }
                        } else if(args[1].equals("SUCCESS")) {
                            UUID uuid = UUID.fromString(args[2]);
                            for (Player a : Bukkit.getOnlinePlayers()) {
                                if (a.getUniqueId().equals(uuid)) {
                                    (new LinkPlugin()).linkSuccess(a);
                                }
                            }
                        }
                    } else if (args[0].equals("PROFILE")) {
                        String discordUserId = args[3];
                        try {
                            PlayerDTO profileData = null;
                            if (args[1].equals("UUID")) {
                                UUID uuid = UUID.fromString(args[2]);
                                profileData = PlayerData.getData(uuid);
                            } else if (args[1].equals("NAME")) {
                                profileData = PlayerData.getData(args[2]);
                            }

                            ConnectClient.sendMessage("PROFILE@SUCCESS@"+profileData.toString()+"@"+discordUserId);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ConnectClient.sendMessage("PROFILE@ERROR@프로필 정보를 수집하는 도중, 문제가 발생했습니다@"+discordUserId);
                        }
                    }
                }

            } catch (IOException e) {
                if(e instanceof SocketException) {
                    System.out.println("서버와의 연결이 끊어졌습니다");
                } else {
                    e.printStackTrace();
                }
                return;
            }
        }
    }
}
