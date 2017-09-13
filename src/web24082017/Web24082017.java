/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web24082017;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Suporte
 */
public class Web24082017 extends Thread {

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8082);
            while (true) {
                Socket socket = ss.accept();
                new Thread(new Servidor(socket)).run();
            }
        } catch (IOException ex) {
            System.out.println("aaaa");
            Logger.getLogger(Web24082017.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
