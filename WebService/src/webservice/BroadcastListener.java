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
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author clodoaldo
 */
public class BroadcastListener implements Runnable {

    List<Amigo> arrayDeAmigo;

    public BroadcastListener(List<Amigo> arrayDeAmigo) {
        this.arrayDeAmigo = arrayDeAmigo;
    }

    @Override
    public void run() {
        InetAddress broadcastIp = getBroadcastIp();
        System.out.println("Ouvindo o broadcast no IP: " + broadcastIp.getHostAddress());
        listener();

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

    private void listener() {
        try {
            int porta = 6666;
            DatagramSocket ds = new DatagramSocket(porta);
            byte[] buffer = new byte[2048];
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
            while (true) {
                ds.receive(dp);
                String texto = new String(buffer, 0, dp.getLength());
                if (texto.contains("SD")) {
                    System.out.println(texto);
                    texto = texto.replace("SD", "");
                    String[] portas = texto.split(" ");
                    resposta(dp.getAddress(), Integer.parseInt(portas[0]), Integer.parseInt(portas[1]));
                }
            }

        } catch (SocketException ex) {
            Logger.getLogger(BroadcastListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BroadcastListener.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void resposta(InetAddress address, int parseInt, int parseInt0) {
        try {
            String mensagem = "AD" + String.valueOf(8082);
            byte[] confirmacao = mensagem.getBytes();
            DatagramSocket conf = new DatagramSocket();
            SocketAddress adressSock = new InetSocketAddress(address, parseInt);
            DatagramPacket pack = new DatagramPacket(confirmacao, confirmacao.length, adressSock);
            conf.send(pack);
            //receber na lista
            arrayDeAmigo.add(new Amigo(pack.getAddress().toString().replace("/", ""),String.valueOf(parseInt0)));
            printList(arrayDeAmigo);
        } catch (SocketException ex) {
            Logger.getLogger(BroadcastListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BroadcastListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void printList(List<Amigo> arrayDeAmigo) {
        for (Amigo amigo : arrayDeAmigo) {
            System.out.println(amigo.toString());
        }
    }

}
