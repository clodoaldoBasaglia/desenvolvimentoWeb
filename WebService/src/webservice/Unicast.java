/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a968692
 */
public class Unicast implements Runnable {

    List<Amigo> arrayDeAmigo;

    public Unicast(List<Amigo> arrayDeAmigo) {
        this.arrayDeAmigo = arrayDeAmigo;
    }

    @Override
    public void run() {
        unicast();
    }

    private void unicast() {
        try {
            int porta = 1234;
            DatagramSocket ds = new DatagramSocket(porta);
            byte[] buffer = new byte[2048];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            while (true) {
                ds.receive(packet);
                String texto = new String(buffer, 0, packet.getLength());
                texto = texto.replace("AD", "");
                //add na lista
                if (!isAmigoNaLista(packet.getAddress().toString().replace("/", ""))) {
                    arrayDeAmigo.add(new Amigo(packet.getAddress().toString().replace("/", ""), texto));
                }
                printList(arrayDeAmigo);
            }
        } catch (SocketException ex) {
            Logger.getLogger(Unicast.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Unicast.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void printList(List<Amigo> arrayDeAmigo) {
        for (Amigo amigo : arrayDeAmigo) {
            System.out.println(amigo.toString());
        }
    }

    private boolean isAmigoNaLista(String replace) {
        boolean flag = false;
        for (Amigo amigo : arrayDeAmigo) {
            if (amigo.getEnderco().equals(replace)) {
                flag = true;
            }
        }
        return flag;
    }

}
