package main.java.pist.plugins.system.link.connect_client;


import main.java.pist.plugins.system.link.object.Receiver;
import main.java.pist.vencoder.Main;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ConnectClient {

    public static Socket socket = null;            //Server와 통신하기 위한 Socket
    public static BufferedReader in = null;        //Server로부터 데이터를 읽어들이기 위한 입력스트림
    public static PrintWriter out = null;            //서버로 내보내기 위한 출력 스트림

    public static void connect() throws IOException {
        try {
            socket = new Socket("59.28.143.207", 1407);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));

            System.out.println(socket.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(new Receiver(socket, in));
        thread.start();
    }

    public static boolean sendMessage(String message) {
        try {
            out.println(message);
            out.flush();
        } catch(Exception e) {
            return false;
        }

        return true;
    }

}
