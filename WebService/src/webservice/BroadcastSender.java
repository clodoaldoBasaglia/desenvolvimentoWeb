/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import static com.sun.xml.internal.ws.model.RuntimeModeler.PORT;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
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
            sendBroadCast(broadcastIp.toString());
            System.out.println("this" + broadcastIp.toString());
        }
    }

    private void sendBroadCast(String endereco) {
        try {
            endereco = endereco.replace("/", "");
            InetAddress addr = InetAddress.getByName(endereco);
            
            try (DatagramSocket serverSocket = new DatagramSocket()) {
                for (int i = 0; i < 100; i++) {
                    String msg = "Sent message no " + i;
                    
                    DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),msg.getBytes().length,addr,8082);
//                    serverSocket.send(msgPacket);
                    
//                    System.out.println("Server sent packet with msg: " + msg);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BroadcastSender.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(BroadcastSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
