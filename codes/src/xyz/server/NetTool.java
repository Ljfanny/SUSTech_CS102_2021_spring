package xyz.server;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class NetTool {

    public static void sendUDPBroadCast(String ip,String msg)
    {
        try {
            DatagramSocket ds = new DatagramSocket();
            InetAddress ia=InetAddress.getByName(ip);
            DatagramPacket dp=new DatagramPacket(msg.getBytes(),0,msg.getBytes().length,ia,10086);
            ds.send(dp);
            ds.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
