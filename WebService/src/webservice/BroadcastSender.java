/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a968692
 */
public class BroadcastSender implements Runnable {

    public BroadcastSender() {
    }

    private InetAddress getBroadcastIp() {
        try {
            Enumeration<NetworkInterface> interfaces;
            interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                try {
                    NetworkInterface networkInterface = interfaces.nextElement();
                    if (networkInterface.isLoopback()) {
                        continue;    // Don't want to broadcast to the loopback interface
                    }
                    for (InterfaceAddress interfaceAddress
                            : networkInterface.getInterfaceAddresses()) {
                        InetAddress broadcast = interfaceAddress.getBroadcast();
                        if (broadcast == null) {
                            continue;
                        } else {
                            System.out.println(broadcast.toString());
                            return broadcast;
                        }
                        // Use the address
                    }
                } catch (SocketException ex) {
                    Logger.getLogger(BroadCast.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (SocketException ex) {
            Logger.getLogger(BroadCast.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void run() {
        System.out.println("print do broads");
        InetAddress broadcastIp = getBroadcastIp();
        if (broadcastIp != null) {
            sendBroadCast(broadcastIp);

        }
    }

    private void sendBroadCast(InetAddress broadcastIp) {
        System.out.println("this" + broadcastIp.toString());
        DatagramSocket ds;
        try {
            ds = new DatagramSocket();
            String mensagem = "SD1234 " + String.valueOf(8082);
            DatagramPacket dp = new DatagramPacket(mensagem.getBytes(), mensagem.getBytes().length, broadcastIp, 6666);
            ds.send(dp);
        } catch (SocketException ex) {
            Logger.getLogger(BroadcastSender.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BroadcastSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
